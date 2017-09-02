package kasembhundit.kutthareeya.thiti.manageres;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    private String strCurrentTime;

    private TextView showTotalPriceTextView;
    private int totalPriceAnInt, factorPriceAnInt, specialAnInt = 0, toppingAnInt = 0,
            itemAnInt = 1;

    private String id_FoodString, specialSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Get Value From Intent
        getValueFromIntent();

        //Initial View
        initialView();

        //Find Category
        findCategory();

        //Show View
        showView();

        //Back Controller
        backController();

        //Order Controller
        orderController();

        //Special Controller
        specialController();

        //Topping Spinner
        toppingSpinner();

        //Item Spinner
        itemSpinner();

        //Receive Time Product
        //receiveTimeProduct();

        //Show Total Price
        totalPriceAnInt = factorPriceAnInt;
        showTotalPrice();


    }   //Main Method

    private void specialController() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chbSpecial);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    specialAnInt = 10;
                } else {
                    specialAnInt = 0;
                }

                showTotalPrice();

            }
        });
    }

    private void showTotalPrice() {

        //Calculate
        totalPriceAnInt = (factorPriceAnInt + specialAnInt + toppingAnInt) * itemAnInt;


        showTotalPriceTextView = (TextView) findViewById(R.id.txtTotalPrice);
        showTotalPriceTextView.setText("ราคารวม = " + Integer.toString(totalPriceAnInt) + " บาท");
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
                itemAnInt = position + 1;
                showTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                itemString = itemStrings[0];
                itemAnInt = 1;
                showTotalPrice();
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

            //Find Category
            categoryString = jsonObject.getString("Category");
            Log.d(tag, "Category ==> " + categoryString);

            //Find Product Price
            String strPrice = jsonObject.getString("ProductPrice");
            factorPriceAnInt = Integer.parseInt(strPrice);

            //Find id


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

                if (position != 0) {
                    toppingAnInt = 10;
                } else {
                    toppingAnInt = 0;
                }

                showTotalPrice();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toppingChooseString = toppingStrings[0];
            }
        });

    }

    private void orderController() {
        Button button = (Button) findViewById(R.id.btnOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Special CheckBox
                specialCheckBox();

                confirmDialog();
            }
        });
    }

    private void confirmDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Check Order");
        builder.setMessage("NameFood = " + nameFoodString + "\n" +
                "Special = " + showSpecial(specialAnInt) + "\n" +
                "Topping = " + toppingChooseString + "\n" +
                "Item = " + Integer.toString(itemAnInt));
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addValueToSQLite();
                dialog.dismiss();
            }
        });
        builder.show();

    }   //Method ConfirmDialog

    private void addValueToSQLite() {

        String tag = "14JulyV1";

        specialSQLite = findSpecialSQLite();

        Log.d(tag, "id_Food ==> " + idFoodString);
        Log.d(tag, "Special ==> " + specialSQLite);
        Log.d(tag, "Topping ==> " + toppingChooseString);
        Log.d(tag, "Item ==> " + Integer.toString(itemAnInt));

        MyManage myManage = new MyManage(OrderActivity.this);
        myManage.addOrder(idFoodString, specialSQLite,
                toppingChooseString, Integer.toString(itemAnInt));

        Intent intent = new Intent(OrderActivity.this, CheckOrderActivity.class);
        intent.putExtra("Login", loginStrings);
        intent.putExtra("MyTime", strCurrentTime);
        startActivity(intent);
        finish();


    }   //add Value SQLite

    private String findSpecialSQLite() {
        if (specialAnInt == 0) {
            return "0";
        } else {
            return "1";
        }
    }


    private String showSpecial(int specialAnInt) {

        String[] strings = new String[]{"ธรรมดา", "พิเศษ"};

        if (specialAnInt == 0) {
            return strings[0];
        } else {
            return strings[1];
        }

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