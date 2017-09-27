package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MerchantMainActivity extends AppCompatActivity {
    MyConstant myConstant = new MyConstant();
    String[] id_ref, id_userStrings, nameUserStrings, surnameUserStrings,
            id_foodStrings, foodNameStrings, priceFoodStrings, specialStrings,
            itemStrings, toppingStrings, nameAndSurnameString, unitPriceStrings,
            specialAndStrings, id_orderStrings, priceStrings, timeStrings;
    ImageView manageImageView, refreshImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);

        //Initial View
        initialView();

        //Create ListView
        createListView();

    }   // Main Method

    private void initialView() {

        refreshImageView = (ImageView) findViewById(R.id.imvRefresh);
        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MerchantMainActivity.this, MerchantMainActivity.class);
                startActivity(intent);
            }
        });

        manageImageView = (ImageView) findViewById(R.id.imvManage);
        manageImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantMainActivity.this, MerchantActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createListView() {
        String tag = "9SepV1";

        try {
            final MyConstant myConstant = new MyConstant();
            GetAllData getAllData = new GetAllData(MerchantMainActivity.this);
            getAllData.execute(myConstant.getUrlGetOrderWhereIdRefAndStatus());
            String result = getAllData.get();
            Log.d(tag, "result ==> " + result);
            String[] stringsSpecial = new String[]{"ธรรมดา", "พิเศษ"};

            //จองหน่วยความจำเขามาแปลงเป็นตัวหนังสือ
            JSONArray jsonArray = new JSONArray(result);
            int lengthAnInt = jsonArray.length();
            id_orderStrings = new String[lengthAnInt];
            id_ref = new String[lengthAnInt];
            id_userStrings = new String[lengthAnInt];
            nameUserStrings = new String[lengthAnInt];
            surnameUserStrings = new String[lengthAnInt];
            id_foodStrings = new String[lengthAnInt];
            foodNameStrings = new String[lengthAnInt];
            priceFoodStrings = new String[lengthAnInt];
            unitPriceStrings = new String[lengthAnInt];
            specialStrings = new String[lengthAnInt];
            itemStrings = new String[lengthAnInt];
            toppingStrings = new String[lengthAnInt];
            nameAndSurnameString = new String[lengthAnInt];
            specialAndStrings = new String[lengthAnInt];
            priceStrings = new String[lengthAnInt];
            timeStrings = new String[lengthAnInt];

            for (int i = 0; i < lengthAnInt; i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_orderStrings[i] = jsonObject.getString("id");
                id_userStrings[i] = jsonObject.getString("id_User");
                id_foodStrings[i] = jsonObject.getString("id_Food");
                id_ref[i] = jsonObject.getString("id_Ref");
                specialStrings[i] = jsonObject.getString("Special");
                itemStrings[i] = jsonObject.getString("Item");
                toppingStrings[i] = jsonObject.getString("Topping");
                priceStrings[i] = jsonObject.getString("PriceOrder");
                timeStrings[i] = jsonObject.getString("TimeReceive");
                nameUserStrings[i] = myFindNameAndSurUser(0, id_userStrings[i]);
                surnameUserStrings[i] = myFindNameAndSurUser(1, id_userStrings[i]);
                foodNameStrings[i] = myFindNameFoodAndPrice(0, id_foodStrings[i]);
                priceFoodStrings[i] = myFindNameFoodAndPrice(1, id_foodStrings[i]);

                specialAndStrings[i] = stringsSpecial[Integer.parseInt(specialStrings[i])];

                unitPriceStrings[i] = Integer.toString((Integer.parseInt(priceFoodStrings[i])) +
                        (addSpecial(specialStrings[i])) +
                        (addTopping(toppingStrings[i])));


                nameAndSurnameString[i] = nameUserStrings[i] + "  " + surnameUserStrings[i];

                Log.d(tag, "id User ==> " + id_userStrings[i]);
                Log.d(tag, "User Name ==> " + nameUserStrings[i]);
                Log.d(tag, "SurName ==> " + surnameUserStrings[i]);
                Log.d(tag, "id Food ==> " + id_foodStrings[i]);
                Log.d(tag, "Price ==> " + priceFoodStrings[i]);
            }

            ListView listView = (ListView) findViewById(R.id.livOrderMerchant);
            MerchantOrderAdapter merchantOrderAdapter = new MerchantOrderAdapter(MerchantMainActivity.this,
                    id_ref, nameAndSurnameString, foodNameStrings, priceStrings, specialAndStrings, toppingStrings, itemStrings, timeStrings);
            listView.setAdapter(merchantOrderAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MerchantMainActivity.this);
                    builder.setCancelable(false);
                    builder.setIcon(R.mipmap.ic_launcher);
                    builder.setTitle("ยืนยันการทำอาหาร");
                    builder.setMessage("คุณต้องการยืนยันรายการอาหารนี้เสร็จเรียบร้อยแล้ว");
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PostStatusOrderMer postStatusOrderMer = new PostStatusOrderMer(MerchantMainActivity.this);
                            postStatusOrderMer.execute(id_orderStrings[position], myConstant.getUrlPostStatusOrderMer());
                            dialog.dismiss();
                            finish();
                            Intent intent = new Intent(MerchantMainActivity.this, MerchantMainActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.show();
                }
            });

        } catch (Exception e) {
            Log.d(tag, "e CreateListView MerchantMainActivity ==>" + e.toString());
        }

    }

    private int addTopping(String toppingString) {
        int resultToppingAnInt = 0;
        if (!toppingString.equals("ไม่มี")) {
            resultToppingAnInt = 10;
        }
        return resultToppingAnInt;
    }

    private int addSpecial(String specialString) {

        int resultAnInt = 0;
        int specialAnInt = Integer.parseInt(specialString);

        if (specialAnInt == 1) {
            resultAnInt = 10;
        }

        return resultAnInt;
    }

    private String myFindNameFoodAndPrice(int index, String id_foodStrings) {
        String tag = "14SepV2";
        String myResultFoodString = null;

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(MerchantMainActivity.this);
            getFoodWhere.execute("id", id_foodStrings, myConstant.getUrlGetProductWhereID());//ไม่ว่าจะเป็น 0 หรือ 1 จะได้ค่ามาทั้งบรรทัด
            String resultFoodString = getFoodWhere.get();
            Log.d(tag, "result ==> " + resultFoodString);

            JSONArray jsonArray = new JSONArray(resultFoodString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            switch (index) {
                case 0:
                    myResultFoodString = jsonObject.getString("ProductName");
                    break;
                case 1:
                    myResultFoodString = jsonObject.getString("ProductPrice");
                    break;
            }
            return myResultFoodString;

        } catch (Exception e) {
            Log.d(tag, "e myFindFood == > " + e.toString());
            return null;
        }
    }

    private String myFindNameAndSurUser(int index, String id_userStrings) {
        String tag = "14SepV1";
        String myResultString = null;

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(MerchantMainActivity.this);
            getFoodWhere.execute("id", id_userStrings, myConstant.getUrlGetUserWhereID());//ไม่ว่าจะเป็น 0 หรือ 1 จะได้ค่ามาทั้งบรรทัด
            String resultString = getFoodWhere.get();
            Log.d(tag, "result ==> " + resultString);

            JSONArray jsonArray = new JSONArray(resultString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            switch (index) {
                case 0:
                    myResultString = jsonObject.getString("Name");
                    break;
                case 1:
                    myResultString = jsonObject.getString("Surname");
                    break;
            }
            return myResultString;

        } catch (Exception e) {
            Log.d(tag, "e myFindName ==> " + e.toString());
            return null;
        }
    }


}   // Main Class
