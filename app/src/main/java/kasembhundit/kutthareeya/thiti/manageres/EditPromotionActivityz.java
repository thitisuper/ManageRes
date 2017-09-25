package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditPromotionActivityz extends AppCompatActivity {

    private ImageView backImageView, iconImageView;
    private EditText promotionEditText;
    private TextView nameFoodTextView;
    private Button saveButton;
    ListView listView;
    String[] id_productStrings, productNameStrings, productImageStrings,
            productPromotionStrings, productPriceStrings;

    MyConstant myConstant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion_activityz);

        //Initial View
        initialView();

        //Back Controller
        backController();

        //Create ListView
        createListView();

    }
    private void createListView() {
        String tag = "23SepV3";

        try {
            myConstant = new MyConstant();
            GetAllData getAllData = new GetAllData(EditPromotionActivityz.this);
            getAllData.execute(myConstant.getUrlGetAllProduct());
            String result = getAllData.get();

            //จองหน่วยความจำ
            JSONArray jsonArray = new JSONArray(result);
            int lengthAnInt = jsonArray.length();
            id_productStrings = new String[lengthAnInt];
            productNameStrings = new String[lengthAnInt];
            productImageStrings = new String[lengthAnInt];
            productPromotionStrings = new String[lengthAnInt];
            productPriceStrings = new String[lengthAnInt];


            int i;
            for (i = 0; i < lengthAnInt; i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_productStrings[i] = jsonObject.getString("id");
                productNameStrings[i] = jsonObject.getString("ProductName");
                productImageStrings[i] = jsonObject.getString("ProductImage");
                productPromotionStrings[i] = jsonObject.getString("promotion");
                productPriceStrings[i] = jsonObject.getString("ProductPrice");

            }   //for

            EditFoodAdapter editFoodAdapter = new EditFoodAdapter(EditPromotionActivityz.this,
                    productImageStrings, productNameStrings);
            listView.setAdapter(editFoodAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(EditPromotionActivityz.this, EditPromotionManage.class);
                    intent.putExtra("idFood", id_productStrings[position]);
                    intent.putExtra("nameFood", productNameStrings[position]);
                    intent.putExtra("priceFood", productPriceStrings[position]);
                    intent.putExtra("imageFood", productImageStrings[position]);
                    intent.putExtra("promotion", productPromotionStrings[position]);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.d(tag, "e create ListView ==> " + e.toString());
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
        iconImageView = (ImageView) findViewById(R.id.imvIcon);
        nameFoodTextView = (TextView) findViewById(R.id.txtTitle);
        saveButton = (Button) findViewById(R.id.btnSave);
        listView = (ListView) findViewById(R.id.livProduct);
    }

}
