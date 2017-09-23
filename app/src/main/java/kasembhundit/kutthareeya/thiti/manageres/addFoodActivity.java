package kasembhundit.kutthareeya.thiti.manageres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class addFoodActivity extends AppCompatActivity {
    private ImageView imageBack, addProductImageView;
    private EditText nameEditText, priceEditText;
    private Spinner categorySpinner;
    private Button saveButton;
    private String nameFoodString, priceFoodString, categoryFoodString, categoryString;

    Uri fileUri;

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

        //Image Take Photo
        imageTakePhoto();

        //Button Controller
        buttonController();

    }   //Main Method

    private void imageTakePhoto() {
        addProductImageView.setOnClickListener(new View.OnClickListener() {
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
                addProductImageView.setImageBitmap(newProfilePic);
            }
        }
    }

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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        Log.d("21SepV1", "Name Food ==> " + nameFoodString);
        Log.d("21SepV1", "price Food ==> " + priceFoodString);
        Log.d("21SepV1", "Category ==> " + categoryString);
        Log.d("21SepV1", "Image ==> " + encodedImage);

        MyAlert myAlert = new MyAlert(addFoodActivity.this);

        if (nameFoodString.equals("") || priceFoodString.equals("")) {
            //Have Space
            myAlert.myDialogError("มีช่องว่างค่ะ",
                    "กรุณากรอกข้อมูลให้ครบถ้วนค่ะ");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(addFoodActivity.this);
            builder.setCancelable(false);
            builder.setTitle("ตรวจเช็คข้อมูลให้ถูกต้อง");
            builder.setMessage("ชื่ออาหาร = " + nameFoodString + "\n" +
                    "ราคา = " + priceFoodString + " บาท" + "\n" +
                    "หมวดหมู่ = " + categoryFoodString);
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    try {

                        MyConstant myConstant = new MyConstant();
                        PostProduct postProduct = new PostProduct(addFoodActivity.this);
                        postProduct.execute(nameFoodString, priceFoodString, encodedImage,
                                categoryString, myConstant.getUrlPostProduct());
                        String result = postProduct.get();

                        Log.d("21SepV1", "Test 2 ==> " + result);

                        if (Boolean.parseBoolean(result)) {
                            finish();
                            Toast.makeText(addFoodActivity.this,
                                    "เพิ่มรายการอาหารเรียบร้อยแล้ว",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            MyAlert myAlert1 = new MyAlert(addFoodActivity.this);
                            myAlert1.myDialogError("ไม่สามารถเพิ่มรายการอาหารได้", "กรุณาเช็คความถูกต้องให้เรียบร้อยค่ะ");
                        }

                        dialog.dismiss();

                    } catch (Exception e) {
                        Log.d("21SepV1", "e from addFood UploadServer ==> " + e.toString());
                    }

                }
            });
            builder.show();
        }

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
                Log.d("21SepV1", "Category ==> " + categoryFoodString);
                changeCategoryFood(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryFoodString = categoryStrings[0];
            }
        });

    }

    private void changeCategoryFood(int key) {

        String categoryStrings = null;
        switch (key) {
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
        categoryString = categoryStrings;
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
