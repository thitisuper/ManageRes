package kasembhundit.kutthareeya.thiti.manageres;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReceiveActivity extends AppCompatActivity {
    private String refString, dateString, timeString;
    private String[] id_foodStrings, specialStrings,
            toppingStrings, itemStrings, productNameStrings,
            productPriceStrings, titleStrings, unitPriceStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

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


            for (int i = 0; i < lengthAInt; i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_foodStrings[i] = jsonObject.getString("id_Food");
                specialStrings[i] = jsonObject.getString("Special");
                toppingStrings[i] = jsonObject.getString("Topping");
                itemStrings[i] = jsonObject.getString("Item");
                productNameStrings[i] = myFindNameAndPrice(0, id_foodStrings[i]);//ไปค้นหาชื่อของอาหารโยนเลข 0 ไป โดยไอดีของอาหาร
                productPriceStrings[i] = myFindNameAndPrice(1, id_foodStrings[i]);//ไปค้นหาราคาของอาหารโยนเลข 1 ไป โดยไอดีของอาหาร
                titleStrings[i] = productNameStrings[i] +
                        ", " +
                        strings[Integer.parseInt(specialStrings[i])] +
                        ", " +
                        toppingStrings[i];
                unitPriceStrings[i] = Integer.toString((Integer.parseInt(productPriceStrings[i])) +
                        (addSpecial(specialStrings[i])) +
                        (addTopping(toppingStrings[i])));

                Log.d(tag, "id_food [" + i + "] ==> " + id_foodStrings[i]);
                Log.d(tag, "productName [" + i + "]" + productNameStrings[i]);

            }   // for

            //Create ListView
            ListView listView1 = (ListView) findViewById(R.id.livReceive);
            ReceiveAdapter receiveAdapter = new ReceiveAdapter(ReceiveActivity.this,
                    titleStrings, itemStrings, unitPriceStrings);
            listView1.setAdapter(receiveAdapter);

            //Show Total
            int totalAInt = 0;
            for (int i = 0; i < itemStrings.length; i += 1) {

                int subTotalAInt = (Integer.parseInt(itemStrings[i])) * (Integer.parseInt(unitPriceStrings[i]));
                totalAInt = totalAInt + subTotalAInt;

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

        refTextView.setText(refString);
        dateTextView.setText("วันที่: " + dateString);
        timeTextView.setText("เวลา: " + timeString);


    }
}   //Main Class
