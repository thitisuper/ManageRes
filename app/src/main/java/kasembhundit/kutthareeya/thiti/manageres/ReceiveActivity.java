package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiveActivity extends AppCompatActivity {
    private String refString, dateString, timeString;
    private String[] id_foodStrings, specialStrings,
            toppingStrings, itemStrings, productNameStrings,
            productPriceStrings, titleStrings, unitPriceStrings,
            promotionStrings, priceTotalStrings;
    float number1, number2, number3;
    ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        backImageView = (ImageView) findViewById(R.id.imvBack);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Get String From SQLite
        getStringFromSQLite();

        //Create Listview
        createListview();

    }   //Main Method

    private void createListview() {
        ListView listView = (ListView) findViewById(R.id.livReceive);
        String tag = "1SepV1";

        try {

            MyConstant myConstant = new MyConstant();
            GetOrderWhereIdRef getOrderWhereIdRef = new GetOrderWhereIdRef(ReceiveActivity.this);
            getOrderWhereIdRef.execute(refString, myConstant.getUrlGetOrderWhereIdRef());
            String result = getOrderWhereIdRef.get();
            Log.d(tag, "result ==> " + result);
            String[] strings = new String[]{"ธรรมดา", "พิเศษ"};

            //จองหน่วยความจำ Json Array
            JSONArray jsonArray = new JSONArray(result);
            int lengthAInt = jsonArray.length();
            id_foodStrings = new String[lengthAInt];
            specialStrings = new String[lengthAInt];
            toppingStrings = new String[lengthAInt];
            itemStrings = new String[lengthAInt];
            productNameStrings = new String[lengthAInt];
            productPriceStrings = new String[lengthAInt];
            titleStrings = new String[lengthAInt];
            unitPriceStrings = new String[lengthAInt];
            promotionStrings = new String[lengthAInt];
            priceTotalStrings = new String[lengthAInt];


            for (int i = 0; i < lengthAInt; i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_foodStrings[i] = jsonObject.getString("id_Food");
                specialStrings[i] = jsonObject.getString("Special");
                toppingStrings[i] = jsonObject.getString("Topping");
                itemStrings[i] = jsonObject.getString("Item");
                productNameStrings[i] = myFindNameAndPrice(0, id_foodStrings[i]);//ไปค้นหาชื่อของอาหารโยนเลข 0 ไป โดยไอดีของอาหาร
                productPriceStrings[i] = myFindNameAndPrice(1, id_foodStrings[i]);//ไปค้นหาราคาของอาหารโยนเลข 1 ไป โดยไอดีของอาหาร
                promotionStrings[i] = myFindNameAndPrice(2, id_foodStrings[i]);//ไปค้นหาโปรโมชั่นของอาหารโยนเลข 1 ไป โดยไอดีของอาหาร
                titleStrings[i] = productNameStrings[i] +
                        ", " +
                        strings[Integer.parseInt(specialStrings[i])] +
                        ", " +
                        toppingStrings[i];
                unitPriceStrings[i] = Integer.toString((Integer.parseInt(productPriceStrings[i])) +
                        (addSpecial(specialStrings[i])) +
                        (addTopping(toppingStrings[i])));

            }   // for

            //Create ListView
            ListView listView1 = (ListView) findViewById(R.id.livReceive);
            ReceiveAdapter receiveAdapter = new ReceiveAdapter(ReceiveActivity.this,
                    titleStrings, itemStrings, unitPriceStrings, promotionStrings);
            listView1.setAdapter(receiveAdapter);

            //Show Total
            int totalAInt = 0;
            for (int i = 0; i < itemStrings.length; i += 1) {
                priceTotalStrings[i] = Integer.toString(((Integer.parseInt(productPriceStrings[i])) +
                        (addSpecial(specialStrings[i])) +
                        (addTopping(toppingStrings[i]))) *
                        Integer.parseInt(itemStrings[i]));
                number1 = Integer.parseInt(promotionStrings[i]);
                number1 /= 100;
                number2 = Integer.parseInt(priceTotalStrings[i]) * number1;
                number3 = Integer.parseInt(priceTotalStrings[i]) - number2;
                totalAInt = (int) (totalAInt + number3);

                Log.d("26SepV1", "Unit Price ==> " + unitPriceStrings[i]);
                Log.d("26SepV1", "Total Price ==> " + priceTotalStrings[i]);
                Log.d("26SepV1", "Number1 ==> " + number1);
                Log.d("26SepV1", "Number2 ==> " + number2);
                Log.d("26SepV1", "Number3 ==> " + number3);
                Log.d("26SepV1", "============================= ");

            }   // for

            TextView textView = (TextView) findViewById(R.id.txtTotal);
            textView.setText("ราคารวม: " + Integer.toString(totalAInt) + " บาท");


        } catch (Exception e) {
            Log.d(tag, "e createListView ==> " + e.toString());
        }


    }   //Create ListView

    private int addTopping(String toppingString) {

        int resultAInt = 0;

        if (!toppingString.equals("ไม่มี")) {
            resultAInt = 10;
        }
        return resultAInt;
    }

    private int addSpecial(String specialString) {

        int resultAInt = 0;
        int specialAInt = Integer.parseInt(specialString);

        if (specialAInt == 1) {
            resultAInt = 10;
        }

        return resultAInt;
    }

    private String myFindNameAndPrice(int index, String id_foodString) {

        String tag = "1SepV2";
        MyConstant myConstant = new MyConstant();
        String myResultString = null;

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(ReceiveActivity.this);
            getFoodWhere.execute("id", id_foodString, myConstant.getUrlGetProductWhereID());//ไม่ว่าจะเป็น 0 หรือ 1 จะได้ค่ามาทั้งบรรทัด
            String resultString = getFoodWhere.get();
            Log.d(tag, "reslutString ==> " + resultString);

            JSONArray jsonArray = new JSONArray(resultString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            switch (index) {
                case 0:
                    myResultString = jsonObject.getString("ProductName");
                    break;
                case 1:
                    myResultString = jsonObject.getString("ProductPrice");
                    break;
                case 2:
                    myResultString = jsonObject.getString("promotion");
                    break;
            }

            return myResultString;

        } catch (Exception e) {
            Log.d(tag, "e myFind ==> " + e.toString());
            return null;
        }

    }

    private void getStringFromSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM receiveTABLE ORDER BY id DESC", null);
        cursor.moveToFirst();
        refString = cursor.getString(1);
        dateString = cursor.getString(2);
        timeString = cursor.getString(3);

        TextView refTextView = (TextView) findViewById(R.id.txtRef);
        TextView dateTextView = (TextView) findViewById(R.id.txtDate);
        TextView timeTextView = (TextView) findViewById(R.id.txtTime);
        TextView timeCurTextView = (TextView) findViewById(R.id.txtCurrentTime);

        refTextView.setText(refString);
        dateTextView.setText("วันที่: " + dateString);
        timeTextView.setText("เวลารับ: " + timeString);

        final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentDateTimeString = dateFormat.format(new Date());
        timeCurTextView.setText("เวลาสั่ง: " + currentDateTimeString);


    }
}   //Main Class
