package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
            productPromotionStrings;
    String id_product, productPromotion;

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

            for (int i = 0; i < lengthAnInt; i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_productStrings[i] = jsonObject.getString("id");
                productNameStrings[i] = jsonObject.getString("ProductName");
                productImageStrings[i] = jsonObject.getString("ProductImage");
                productPromotionStrings[i] = jsonObject.getString("promotion");

            }   //for

            EditPromotionAdapter editPromotionAdapter = new EditPromotionAdapter(EditPromotionActivityz.this,
                    productNameStrings, productImageStrings, productPromotionStrings);
            listView.setAdapter(editPromotionAdapter);


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
        promotionEditText = (EditText) findViewById(R.id.edtPromotion);
        nameFoodTextView = (TextView) findViewById(R.id.txtTitle);
        saveButton = (Button) findViewById(R.id.btnSave);
        listView = (ListView) findViewById(R.id.livProduct);
    }

}
