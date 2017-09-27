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

public class EditFoodActivity extends AppCompatActivity {

    MyConstant myConstant = new MyConstant();
    private ImageView backImageView, refreshImageView;
    String[] id_productStrings, productNameStrings, productPriceStrings,
            productImage, categoryStrings;
    ListView listView;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        //Initial View
        initialView();

        //back Controller
        backController();

        //Refresh Controller
        refreshController();

        //Create ListView
        createListView();


    }   //Main Method

    private void refreshController() {
        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EditFoodActivity.this, EditFoodActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createListView() {
        String tag = "23SepV1";

        try {

            GetAllData getAllData = new GetAllData(EditFoodActivity.this);
            getAllData.execute(myConstant.getUrlGetAllProduct());
            String result = getAllData.get();
            Log.d(tag, "result EditFood ==> " + result);

            //จองหน่วยความจำ
            JSONArray jsonArray = new JSONArray(result);
            int lengthAnInt = jsonArray.length();
            id_productStrings = new String[lengthAnInt];
            productNameStrings = new String[lengthAnInt];
            productPriceStrings = new String[lengthAnInt];
            productImage = new String[lengthAnInt];
            categoryStrings = new String[lengthAnInt];

            for (int i = 0; i < lengthAnInt; i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_productStrings[i] = jsonObject.getString("id");
                productNameStrings[i] = jsonObject.getString("ProductName");
                productPriceStrings[i] = jsonObject.getString("ProductPrice");
                productImage[i] = jsonObject.getString("ProductImage");
                categoryStrings[i] = jsonObject.getString("Category");

                Log.d(tag, "id ==> " + id_productStrings[i]);
                Log.d(tag, "product name ==> " + productNameStrings[i]);
                Log.d(tag, "product Price ==> " + productPriceStrings[i]);
                Log.d(tag, "product Image ==> " + productImage[i]);
            }   //for

            EditFoodAdapter editFoodAdapter = new EditFoodAdapter(EditFoodActivity.this,
                    productImage, productNameStrings);
            listView.setAdapter(editFoodAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(EditFoodActivity.this, EditFoodManageActivity.class);
                    intent.putExtra("idFood", id_productStrings[position]);
                    intent.putExtra("nameFood", productNameStrings[position]);
                    intent.putExtra("priceFood", productPriceStrings[position]);
                    intent.putExtra("imageFood", productImage[position]);
                    intent.putExtra("categoryFood", categoryStrings[position]);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.d(tag, "e to CreateListView ==> " + e.toString());
        }
    }

    private void backController() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialView() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        refreshImageView = (ImageView) findViewById(R.id.imvRefresh);
        listView = (ListView) findViewById(R.id.livEdtFood);
    }
}   //Main Class
