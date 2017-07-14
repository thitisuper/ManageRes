package kasembhundit.kutthareeya.thiti.manageres;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    private String[] loginStrings, toppingStrings;
    private String nameFoodString, priceToolbarString, iconString,
            idFoodString, categoryString, toppingChooseString, specialString = "0",
            itemString = "1", deliveryString = "0";
    private TextView nameFoodTextView, priceToolbarTextView, textView;
    private ImageView iconImageView;
    private MyConstant myConstant;
    private int intHour, intMinus;

    private TextView showTotalPriceTextView;
    private int totalPriceAnInt, factorPriceAnInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Get Value From Intent
        getValueFromIntent();

        //Initial View
        initialView();

        //Find Categoty
        findCategory();

        //Show View
        showView();

        //Back Controller
        backController();

        //Order Controller
        orderController();

        //Topping Spinner
        toppingSpinner();

        //Item Spinner
        itemSpinner();

        //Receive Time Product
        receiveTimeProduct();

        //Show Total Price
        totalPriceAnInt = factorPriceAnInt;
        showTotalPrice();


    }   //Main Method

    private void showTotalPrice() {
        showTotalPriceTextView = (TextView) findViewById(R.id.txtTotalPrice);
        showTotalPriceTextView.setText("ราคารวม = " + Integer.toString(totalPriceAnInt) + " บาท");
    }

    private void deliveryCheckbox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chbDelivery);
        if (checkBox.isChecked()) {
            deliveryString = "1";
        }
        if (!checkBox.isChecked()) {
            deliveryString = "0";
        }
    }


    private void receiveTimeProduct() {

        String tag = "13JulyV2";

        //Get Current Time
        final Calendar calendar = Calendar.getInstance();
        final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        final String strCurrentTime = dateFormat.format(calendar.getTime());
        Log.d(tag, "CurrentTime ==> " + strCurrentTime);

        textView = (TextView) findViewById(R.id.txtShowTime);
        myShowTime(strCurrentTime);

        intHour = calendar.get(Calendar.HOUR_OF_DAY);
        intMinus = calendar.get(Calendar.MINUTE);

        ImageView imageView = (ImageView) findViewById(R.id.imvChooseTime);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(OrderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int Hour, int Minus) {

                                if (Hour >= intHour) {

                                    calendar.set(Calendar.HOUR_OF_DAY, Hour);
                                    calendar.set(Calendar.MINUTE, Minus);
                                    String chooseTime = dateFormat.format(calendar.getTime());
                                    myShowTime(chooseTime);

                                } else {

                                    MyAlert myAlert = new MyAlert(OrderActivity.this);
                                    myAlert.myDialogError("ชั่วโมงย้อนหลัง", "โปรดเลือกชั่วโมงใหม่");

                                }

                            }
                        }, intHour, intMinus, false);
                timePickerDialog.show();

            }
        });
    }

    private void myShowTime(String strCurrentTime) {
        textView.setText("เวลารับอาหาร : " + strCurrentTime);
    }

    private void itemSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spnItem);
        final String[] itemStrings = myConstant.getItemStrings();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(OrderActivity.this,
                android.R.layout.simple_list_item_1, itemStrings);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemString = itemStrings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemString = itemStrings[0];
            }
        });

    }

    private void specialCheckBox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chbSpecial);

        if (checkBox.isChecked()) {
            specialString = "1";
        }

        if (!checkBox.isChecked()) {
            specialString = "0";
        }
        Log.d("13JulyV1", "specialString ==> " + specialString);

    }

    private void findCategory() {

        String tag = "13JulyV1";

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(OrderActivity.this);
            getFoodWhere.execute("id", idFoodString, myConstant.getUrlGetProductWhereID());
            String strJSON = getFoodWhere.get();
            Log.d(tag, "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            categoryString = jsonObject.getString("Category");
            Log.d(tag, "Category ==> " + categoryString);

            String strPrice = jsonObject.getString("ProductPrice");
            factorPriceAnInt = Integer.parseInt(strPrice);

            switch (Integer.parseInt(categoryString)) {
                case 0:
                    toppingStrings = myConstant.getTopping0();
                    break;
                case 1:
                    toppingStrings = myConstant.getTopping1();
                    break;
                case 2:
                    toppingStrings = myConstant.getTopping2();
                    break;
            }

        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());
        }

    }

    private void toppingSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spnTopping);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(OrderActivity.this,
                android.R.layout.simple_list_item_1, toppingStrings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toppingChooseString = toppingStrings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toppingChooseString = toppingStrings[0];
            }
        });

    }

    private void orderController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvOrder);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Special CheckBox
                specialCheckBox();

                //Delivery Checkbox
                deliveryCheckbox();

            }   //onClick
        });
    }

    private void backController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void showView() {
        nameFoodTextView.setText(nameFoodString);
        priceToolbarTextView.setText(priceToolbarString);
        Picasso.with(OrderActivity.this).load(iconString).into(iconImageView);
    }

    private void initialView() {
        nameFoodTextView = (TextView) findViewById(R.id.txtNameFood);
        priceToolbarTextView = (TextView) findViewById(R.id.txtPriceFood);
        iconImageView = (ImageView) findViewById(R.id.imvIcon);
    }

    private void getValueFromIntent() {
        myConstant = new MyConstant();
        loginStrings = getIntent().getStringArrayExtra("Login");
        nameFoodString = getIntent().getStringExtra("NameFood");
        priceToolbarString = getIntent().getStringExtra("PriceFood");
        iconString = getIntent().getStringExtra("iconFood");
        idFoodString = getIntent().getStringExtra("idFood");
    }
}   //Main Class
