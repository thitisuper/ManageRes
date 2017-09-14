package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MerchantMainActivity extends AppCompatActivity {
    String[] id_user, id_ref, id_food, special, topping, item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_main);

        //Create ListView
        createListView();

    }   // Main Method

    private void createListView() {
        ListView listView = (ListView) findViewById(R.id.livOrderMerchant);
        String tag = "9SepV1";

        try {
            MyConstant myConstant = new MyConstant();
            GetAllData getAllData = new GetAllData(MerchantMainActivity.this);
            getAllData.execute(myConstant.getUrlGetOrderWhereIdRefAndStatus());
            String result = getAllData.get();
            Log.d(tag, "result ==> " + result);

            JSONArray jsonArray = new JSONArray(result);
            id_ref = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_ref[i] = jsonObject.getString("id_Ref");
            }
            listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, id_ref));

        } catch (Exception e) {
            Log.d(tag, "e CreateListView MerchantMainActivity ==>" + e.toString());
        }

    }
}   // Main Class
