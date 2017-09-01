package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
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

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backImageView, showOrderImageView;
    private TextView titleTextView, nameTextView;
    private ListView listView;
    private int favoriteAnInt, groupAnInt;
    private String[] loginStrings;
    private String tag = "10JuneV2";
    private String strJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Initial VIew
        initialVIew();

        //Get Value And Set Up
        getValueAndSetUp();

        //Controller
        controller();

        //Create ListView
        createListView();

    }   //Main Method

    private void createListView() {

        try {

            MyConstant myConstant = new MyConstant();
            GetFoodWhere getFoodWhere = new GetFoodWhere(ServiceActivity.this);
            String strTitle = null;

            //For Favorite == 1
            if (favoriteAnInt == 1) {
                //Get Favorite
                getFoodWhere.execute("Favorite", "1",
                        myConstant.getUrlGetFoodFavorite());
                strTitle = "Favorite";
                strJSON = getFoodWhere.get();
            } else if (groupAnInt == 0) {
                //อาหารจารเดี่ยว
                getFoodWhere.execute("Group", "0",
                        myConstant.getUrlGetFoodGroup());
                strTitle = "อาหารจานเดียว";
                strJSON = getFoodWhere.get();
            } else if (groupAnInt == 1) {
                //กับข้าว
                getFoodWhere.execute("Group", "1",
                        myConstant.getUrlGetFoodGroup());
                strTitle = "กับข้าว";
                strJSON = getFoodWhere.get();
            } else if (groupAnInt == 2) {
                //ประเภทเส้น
                getFoodWhere.execute("Group", "2",
                        myConstant.getUrlGetFoodGroup());
                strTitle = "ประเภทเส้น";
                strJSON = getFoodWhere.get();
            } else {
                //For All Food
                GetAllData getAllData = new GetAllData(ServiceActivity.this);
                getAllData.execute(myConstant.getUrlGetAllProduct());
                strTitle = "อาหารทั้งหมด";
                strJSON = getAllData.get();
            }

            //Show Text
            titleTextView.setText(strTitle);
            nameTextView.setText(loginStrings[1] + " " + loginStrings[2]);

            Log.d(tag, "strJSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);

            final String[] productNameStrings = new String[jsonArray.length()];
            final String[] productPriceStrings = new String[jsonArray.length()];
            final String[] iconFood = new String[jsonArray.length()];
            final String[] idFoodStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                productNameStrings[i] = jsonObject.getString("ProductName");
                productPriceStrings[i] = "ราคา " + jsonObject.getString("ProductPrice") + " บาท";
                iconFood[i] = jsonObject.getString("ProductImage");
                idFoodStrings[i] = jsonObject.getString("id");
            }   // for

            NewAdapter newAdapter = new NewAdapter(ServiceActivity.this, productNameStrings,
                    productPriceStrings, iconFood);
            listView.setAdapter(newAdapter);

            //Intent to OrderActivity
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ServiceActivity.this, OrderActivity.class);
                    intent.putExtra("Login", loginStrings);
                    intent.putExtra("idFood", idFoodStrings[position]);
                    intent.putExtra("NameFood", productNameStrings[position]);
                    intent.putExtra("PriceFood", productPriceStrings[position]);
                    intent.putExtra("iconFood", iconFood[position]);
                    startActivity(intent);

                }
            });


        } catch (Exception e) {
            Log.d(tag, "e createListView ==> " + e.toString());
        }

    }

    private void getValueAndSetUp() {
        favoriteAnInt = getIntent().getIntExtra("Favorite", 0);
        groupAnInt = getIntent().getIntExtra("Group", 0);
        loginStrings = getIntent().getStringArrayExtra("Login");
        Log.d(tag, "Fovorite ==> " + favoriteAnInt);
        Log.d(tag, "Group ==> " + groupAnInt);
        Log.d(tag, "loginStringLength ==> " + loginStrings.length);
    }

    private void controller() {
        backImageView.setOnClickListener(ServiceActivity.this);
        showOrderImageView.setOnClickListener(this);
    }

    private void initialVIew() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        showOrderImageView = (ImageView) findViewById(R.id.imvShowOrder);
        titleTextView = (TextView) findViewById(R.id.txtTitle);
        nameTextView = (TextView) findViewById(R.id.txtName);
        listView = (ListView) findViewById(R.id.livFood);
    }

    @Override
    public void onClick(View v) {

        //For Back
        if (v == backImageView) {
            finish();
        }

        //For Show
        if (v == showOrderImageView) {

        }

    }
}   //Main Class
