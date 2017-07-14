package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

public class CheckOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);

        //Add Order
        addOrder();

        //Save Order To Server
        saveOrderToServer();

        //Create ListView
        createListView();


    }   //Main Method

    private void createListView() {
        ListView listView = (ListView) findViewById(R.id.livOrder);
        String tag = "14JulyV2";
        int totalPrice = 0;

        try {

            final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            final String[] idStrings = new String[cursor.getCount()];
            String[] id_FoodStrings = new String[cursor.getCount()];
            String[] specialStrings = new String[cursor.getCount()];
            String[] toppingStrings = new String[cursor.getCount()];
            String[] itemStrings = new String[cursor.getCount()];
            String[] nameFoodStrings = new String[cursor.getCount()];
            String[] showSpecialStrings = new String[cursor.getCount()];
            String[] factorPriceStrings = new String[cursor.getCount()];
            int[] priceInts = new int[cursor.getCount()];
            String[] showPriceStrings = new String[cursor.getCount()];//จองหน่วยความจำ

            String[] strings = new String[]{"ธรรมดา", "พิเศษ"};

            for (int i = 0; i < cursor.getCount(); i += 1) {

                idStrings[i] = cursor.getString(0);
                id_FoodStrings[i] = cursor.getString(1);
                specialStrings[i] = cursor.getString(2);
                toppingStrings[i] = cursor.getString(3);
                itemStrings[i] = cursor.getString(4);

                nameFoodStrings[i] = findNameFood(id_FoodStrings[i], true);
                Log.d(tag, "nameFood[ " + i + "] ==> " + nameFoodStrings);

                factorPriceStrings[i] = findNameFood(id_FoodStrings[i], false);
                priceInts[i] = Integer.parseInt(factorPriceStrings[i]) * Integer.parseInt(itemStrings[i]);
                totalPrice = totalPrice + priceInts[i];
                showPriceStrings[i] = Integer.toString(priceInts[i]);

                showSpecialStrings[i] = strings[Integer.parseInt(specialStrings[i])];

                cursor.moveToNext();
            }   //for

            TextView textView = (TextView) findViewById(R.id.txtShowTotalPrice);
            textView.setText("Total = " + Integer.toString(totalPrice) + " บาท");

            OrderAdapter orderAdapter = new OrderAdapter(CheckOrderActivity.this,
                    nameFoodStrings,
                    showSpecialStrings,
                    itemStrings,
                    showPriceStrings,
                    toppingStrings);
            listView.setAdapter(orderAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d("14JulyV2", "You Click ID ==> " + idStrings[position]);
                    deleteOrder(idStrings[position], sqLiteDatabase);

                }
            });

            cursor.close();

        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());
        }


    }   //Create ListView

    private void deleteOrder(final String idString, final SQLiteDatabase sqLiteDatabase) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOrderActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You want to Delete this Order!");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqLiteDatabase.delete("orderTABLE",
                        "id" + "=" + Integer.parseInt(idString), null);
                createListView();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private String findNameFood(String idFood, boolean status) {

        String tag = "14JulyV2", strResult = null;
        MyConstant myConstant = new MyConstant();
        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(CheckOrderActivity.this);
            getFoodWhere.execute("id", idFood, myConstant.getUrlGetProductWhereID());
            String strJSON = getFoodWhere.get();

            JSONArray jsonArray = new JSONArray(strJSON);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            if (status) {
                strResult = jsonObject.getString("ProductName");
            } else {
                strResult = jsonObject.getString("ProductPrice");
            }

            return strResult;

        } catch (Exception e) {
            Log.d(tag, "e findName ==> " + e.toString());
            return null;
        }
    }

    private void saveOrderToServer() {
        ImageView imageView = (ImageView) findViewById(R.id.imvOrder);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadValueToServer();

            }   //OnClick
        });
    }

    private void uploadValueToServer() {

        String tag = "14JulyV3";
        Random random = new Random();
        int intRandom = random.nextInt(1000);
        String[] loginStrings = getIntent().getStringArrayExtra("Login");
        String id_ref = loginStrings[1] + Integer.toString(intRandom);
        Log.d(tag, "id_user ==> " + loginStrings[0]);
        Log.d(tag, "id_ref ==> " + id_ref);

        try {

            //Read SQLite
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            String[] id_Food = new String[cursor.getCount()];
            String[] Special = new String[cursor.getCount()];
            String[] Topping = new String[cursor.getCount()];
            String[] Item = new String[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i += 1) {

                id_Food[i] = cursor.getString(1);
                Special[i] = cursor.getString(2);
                Topping[i] = cursor.getString(3);
                Item[i] = cursor.getString(4);

                Log.d(tag, "id_Food[" + i + "] ==> " + id_Food[i]);

                cursor.moveToNext();

            }   //for

        } catch (Exception e) {
            Log.d(tag, "e upload ==> " + e.toString());
        }

    }   //Upload Value To Server

    private void addOrder() {
        ImageView imageView = (ImageView) findViewById(R.id.imvAddOrder);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}   //Main Class
