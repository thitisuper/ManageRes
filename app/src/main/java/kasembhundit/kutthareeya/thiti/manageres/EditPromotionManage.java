package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class EditPromotionManage extends AppCompatActivity {

    private ImageView backImageView, showImageView;
    private TextView nameFoodTextView, priceNowTextView, priceFutureTextView;
    private EditText promotionEditText;
    private String id_productString, productNameString, productPriceString,
            productImageString, promotionString, promotionPrice, totalString;
    MyConstant myConstant;
    Button saveButton;
    float number1, number2, number3, number4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_promotion_manage);

        //Initial View
        initialView();

        //Get Value From Intent
        getValueFromIntent();

        //Show View
        showView();

        //Back Controller
        backController();

        //Edit Promotion
        editPromotion();

        //Button Controller
        buttonController();

    }   //Main Method

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tag = "25SepV1";

                //Post To Server
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPromotionManage.this);
                builder.setCancelable(false);
                builder.setTitle("ตรวจเช็คข้อมูลให้ถูกต้อง");
                builder.setMessage("ชื่ออาหาร = " + productNameString + "\n" +
                        "ลดราคา = " + number1 + " %" + "\n" +
                        "ราคาปัจจุบัน = " + Float.parseFloat(productPriceString) + " บาท" + "\n" +
                        "ราคาที่ลด = " + number4 + " บาท");
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
                            EditPromotion editPromotion = new EditPromotion(EditPromotionManage.this);
                            editPromotion.execute(id_productString, Integer.toString((int) number1),
                                    myConstant.getUrlUpdateProductPromotion());
                            String result = editPromotion.get();
                            Log.d(tag, "Result ==> " + result);

                            if (Boolean.parseBoolean(result)) {
                                finish();
                                Toast.makeText(EditPromotionManage.this,
                                        "เพิ่มโปรโมชั่นเรียบร้อยแล้ว",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                MyAlert myAlert1 = new MyAlert(EditPromotionManage.this);
                                myAlert1.myDialogError("ไม่สามารถเพิ่มโปรโมชั่นได้", "กรุณาเช็คความถูกต้องให้เรียบร้อยค่ะ");
                            }

                        } catch (Exception e) {
                            Log.d(tag, "e button ==> " + e.toString());
                        }

                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
    }

    private void editPromotion() {
        promotionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                priceFutureTextView.setText(calculateTest());
            }
        });
    }

    private String calculateTest() {
        if (promotionEditText.getText().toString() != "" && promotionEditText.getText().length() > 0) {
            number1 = Integer.parseInt(promotionEditText.getText().toString());
            number2 = number1 / 100;
            number3 = Integer.parseInt(productPriceString) * number2;
            number4 = Integer.parseInt(productPriceString) - number3;
        } else {
            number4 = 0;
        }
        Log.d("25SepV1", "Test ==> " + number4);
        return "ราคาที่ลด : " + Integer.toString((int) number4) + " บาท";
    }

    private void backController() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showView() {

        Picasso.with(EditPromotionManage.this).load(productImageString).into(showImageView);
        nameFoodTextView.setText(productNameString);
        promotionEditText.setText(promotionString);
        priceNowTextView.setText("ราคาปัจจุบัน : " + productPriceString + " บาท");
        priceFutureTextView.setText("ราคาที่ลด : " + number4 + " บาท");

    }

    private void getValueFromIntent() {
        myConstant = new MyConstant();
        id_productString = getIntent().getStringExtra("idFood");
        productNameString = getIntent().getStringExtra("nameFood");
        productPriceString = getIntent().getStringExtra("priceFood");
        productImageString = getIntent().getStringExtra("imageFood");
        promotionString = getIntent().getStringExtra("promotion");
    }

    private void initialView() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        showImageView = (ImageView) findViewById(R.id.imvShow);
        nameFoodTextView = (TextView) findViewById(R.id.txtNameFood);
        priceNowTextView = (TextView) findViewById(R.id.showPriceNow);
        priceFutureTextView = (TextView) findViewById(R.id.showPriceFuture);
        promotionEditText = (EditText) findViewById(R.id.edtPromotion);
        saveButton = (Button) findViewById(R.id.btnSave);
    }
}   //Main Class
