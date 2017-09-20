package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class StatusActivity extends AppCompatActivity {
    private String[] loginStrings;
    private String[] ref_String, id_foodStrings, specialStrings,
            toppingStrings, itemStrings, productNameStrings,
            productPriceStrings, specialAndStrings, unitPriceStrings;
    private ImageView backImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        loginStrings = getIntent().getStringArrayExtra("Login");
        Log.d("20SepV3", "login ID ==> " + loginStrings[0]);

        //Create ListView
        createListView();

        //Back Controller
        backController();

    }   //Main Method

    private void backController() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createListView() {
        String tag = "20SepV3";

        try {
            MyConstant myConstant = new MyConstant();
            GetOrderWhereIDUser getOrderWhereIDUser = new GetOrderWhereIDUser(StatusActivity.this);
            getOrderWhereIDUser.execute(loginStrings[0], myConstant.getUrlGetOrderWhereUser());
            String result = getOrderWhereIDUser.get();
            String[] strings = new String[]{"ธรรมดา", "พิเศษ"};

            //จองหน่วยความจำ
            JSONArray jsonArray = new JSONArray(result);
            int lengthAInt = jsonArray.length();
            ref_String = new String[lengthAInt];
            id_foodStrings = new String[lengthAInt];
            specialStrings = new String[lengthAInt];
            toppingStrings = new String[lengthAInt];
            itemStrings = new String[lengthAInt];
            productNameStrings = new String[lengthAInt];
            productPriceStrings = new String[lengthAInt];
            specialAndStrings = new String[lengthAInt];
            unitPriceStrings = new String[lengthAInt];

            for (int i = 0; i < lengthAInt; i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ref_String[i] = jsonObject.getString("id_Ref");
                id_foodStrings[i] = jsonObject.getString("id_Food");
                specialStrings[i] = jsonObject.getString("Special");
                toppingStrings[i] = jsonObject.getString("Topping");
                itemStrings[i] = jsonObject.getString("Item");
                productNameStrings[i] = myFindNameAndPrice(0, id_foodStrings[i]);//ไปค้นหาชื่อของอาหารโยนเลข 0 ไป โดยไอดีของอาหาร
                productPriceStrings[i] = myFindNameAndPrice(1, id_foodStrings[i]);//ไปค้นหาราคาของอาหารโยนเลข 1 ไป โดยไอดีของอาหาร
                specialAndStrings[i] = strings[Integer.parseInt(specialStrings[i])];
                unitPriceStrings[i] = Integer.toString((Integer.parseInt(productPriceStrings[i])) +
                        (addSpecial(specialStrings[i])) +
                        (addTopping(toppingStrings[i])));
            }   //for

            //Create ListView
            ListView listView = (ListView) findViewById(R.id.livOrderRef);
            StatusUserAdapter statusUserAdapter = new StatusUserAdapter(StatusActivity.this, ref_String, productNameStrings,
                    productPriceStrings, specialAndStrings, toppingStrings, itemStrings);
            listView.setAdapter(statusUserAdapter);

            Log.d("20SepV3", "getOrder Result ==> " + result);

        } catch (Exception e) {
            Log.d(tag, "e to CreateListview StatusActivity ==> " + e.toString());
        }
    }

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

    private String myFindNameAndPrice(int i, String id_foodString) {
        String tag = "20SepV3";
        MyConstant myConstant = new MyConstant();
        String myResultString = null;

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(StatusActivity.this);
            getFoodWhere.execute("id", id_foodString, myConstant.getUrlGetProductWhereID());//ไม่ว่าจะเป็น 0 หรือ 1 จะได้ค่ามาทั้งบรรทัด
            String resultString = getFoodWhere.get();
            Log.d(tag, "reslutString ==> " + resultString);

            JSONArray jsonArray = new JSONArray(resultString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            switch (i) {
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
}   //Main Class
