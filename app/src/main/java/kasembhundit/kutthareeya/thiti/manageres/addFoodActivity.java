package kasembhundit.kutthareeya.thiti.manageres;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class addFoodActivity extends AppCompatActivity {
    private ImageView imageBack, addProductImageView;
    private EditText nameEditText, priceEditText;
    private Spinner categorySpinner;
    private Button saveButton;
    private String nameFoodString, priceFoodString, categoryFoodString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        //Initial View
        initialView();

        //back controller
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Create Spinner
        createSpinner();

        //Button Controller
        buttonController();

    }   //Main Method

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Upload To server
                uploadToServer();

            }
        });
    }

    private void uploadToServer() {
        String tag = "20SepV3";
        Bitmap bitmap = ((BitmapDrawable) addProductImageView.getDrawable()).getBitmap();
        nameFoodString = nameEditText.getText().toString().trim();
        priceFoodString = priceEditText.getText().toString().trim();

        Log.d(tag, "nameFood ==> " + nameFoodString);
        Log.d(tag, "priceFood ==> " + priceFoodString);

    }

    private void createSpinner() {
        MyConstant myConstant = new MyConstant();
        final String[] categoryStrings = myConstant.getCategory();

        //For Build Spinner
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(addFoodActivity.this,
                android.R.layout.simple_list_item_1, categoryStrings);
        categorySpinner.setAdapter(categoryArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryFoodString = categoryStrings[position];
                changeCategoryFood(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryFoodString = categoryStrings[0];
            }
        });

    }

    private int changeCategoryFood(int position) {

        int resultAnInt = 0;
        if (categoryFoodString.equals("อาหารจานเดียว")) {
            resultAnInt = 0;
        }
        if (categoryFoodString.equals("ประเภทกับข้าว")) {
            resultAnInt = 1;
        }
        if (categoryFoodString.equals("ประเภทเส้น")) {
            resultAnInt = 2;
        }
        return resultAnInt;
    }


    private void initialView() {
        imageBack = (ImageView) findViewById(R.id.imvBack);
        categorySpinner = (Spinner) findViewById(R.id.categorySpin);
        addProductImageView = (ImageView) findViewById(R.id.imvAddNew);
        nameEditText = (EditText) findViewById(R.id.edtNameFood);
        priceEditText = (EditText) findViewById(R.id.edtPriceFood);
        saveButton = (Button) findViewById(R.id.btnSave);
    }
}   //Main Class
