package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditFoodManageActivity extends AppCompatActivity {

    ImageView backImageView, editImageView;
    EditText nameFoodEditText, priceFoodEditText;
    Spinner categorySpinner;
    MyConstant myConstant;
    String id_ProductString, nameProductString, priceProductString,
            imageProductString, categoryString, categoryAnIntString;
    Button saveButton;

    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_manage);

        //Initial View
        initialView();

        //back Controller
        backController();

        //Get Value Intent From EditFood
        getValueFromIntent();

        //Show View
        showView();

        //Spinner
        spinner();

        //Take Photo
        takePhoto();

        //Button Controller
        buttonController();


    }   //Main Method

    private void takePhoto() {
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 500);
                intent.putExtra("outputY", 500);
                intent.putExtra("asprctX", 2);
                intent.putExtra("aspectY", 2);
                intent.putExtra("return-data", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                editImageView.setImageBitmap(newProfilePic);
            }
        }
    }

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmDialog();

            }
        });
    }

    private void confirmDialog() {
        uploadValueToServer();

    }

    private void uploadValueToServer() {

        final String tag = "23SepV2";
        Bitmap bitmap = ((BitmapDrawable) editImageView.getDrawable()).getBitmap();
        nameProductString = nameFoodEditText.getText().toString().trim();
        priceProductString = priceFoodEditText.getText().toString().trim();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        MyAlert myAlert = new MyAlert(EditFoodManageActivity.this);

        if (nameProductString.equals("") || priceProductString.equals("")) {
            //Have Space
            myAlert.myDialogError("มีช่องว่างค่ะ",
                    "กรุณากรอกข้อมูลให้ครบถ้วนค่ะ");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditFoodManageActivity.this);
            builder.setCancelable(false);
            builder.setTitle("ตรวจเช็คข้อมูลให้ถูกต้อง");
            builder.setMessage("ชื่ออาหาร = " + nameProductString + "\n" +
                    "ราคา = " + priceProductString + " บาท" + "\n" +
                    "หมวดหมู่ = " + categoryString);
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {

                        myConstant = new MyConstant();
                        EditProduct editProduct = new EditProduct(EditFoodManageActivity.this);
                        editProduct.execute(id_ProductString, nameProductString, priceProductString,
                                encodedImage, categoryAnIntString, myConstant.getUrlUpdateProduct());
                        String result = editProduct.get();

                        Log.d(tag, "result ==> " + result);

                        if (Boolean.parseBoolean(result)) {
                            finish();
                            Toast.makeText(EditFoodManageActivity.this,
                                    "แก้ไขรายการอาหารเรียบร้อยแล้ว",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            MyAlert myAlert1 = new MyAlert(EditFoodManageActivity.this);
                            myAlert1.myDialogError("ไม่สามารถแก้ไขรายการอาหารได้", "กรุณาเช็คความถูกต้องให้เรียบร้อยค่ะ");
                        }
                        dialog.dismiss();

                    } catch (Exception e) {
                        Log.d(tag, "e onclick ==> " + e.toString());
                    }
                }
            });
            builder.show();
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

    private void spinner() {
        String tag = "23SepV2";
        myConstant = new MyConstant();
        final String[] categorySpin = myConstant.getCategory();

        //For Build Spinner
        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>(EditFoodManageActivity.this,
                android.R.layout.simple_list_item_1, categorySpin);
        categorySpinner.setAdapter(categoryArrayAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = categorySpin[position];
                changeCategoryFood(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryString = categorySpin[0];
            }
        });
    }

    private void changeCategoryFood(int position) {
        String categoryStrings = null;
        switch (position) {
            case 0:
                categoryStrings = "0";
                break;
            case 1:
                categoryStrings = "1";
                break;
            case 2:
                categoryStrings = "2";
                break;
        }
        categoryAnIntString = categoryStrings;
    }

    private void showView() {
        nameFoodEditText.setText(nameProductString);
        priceFoodEditText.setText(priceProductString);
        Picasso.with(EditFoodManageActivity.this).load(imageProductString).into(editImageView);
    }

    private void getValueFromIntent() {
        myConstant = new MyConstant();
        id_ProductString = getIntent().getStringExtra("idFood");
        nameProductString = getIntent().getStringExtra("nameFood");
        priceProductString = getIntent().getStringExtra("priceFood");
        imageProductString = getIntent().getStringExtra("imageFood");
        categoryString = getIntent().getStringExtra("categoryFood");
    }

    private void initialView() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        editImageView = (ImageView) findViewById(R.id.imvAddNew);
        nameFoodEditText = (EditText) findViewById(R.id.edtNameFood);
        priceFoodEditText = (EditText) findViewById(R.id.edtPriceFood);
        categorySpinner = (Spinner) findViewById(R.id.categorySpin);
        saveButton = (Button) findViewById(R.id.btnSave);
    }
}   //Main Class
