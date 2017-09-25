package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class EditPromotionManage extends AppCompatActivity {

    private ImageView backImageView, showImageView;
    private TextView nameFoodTextView, priceNowTextView, priceFutureTextView;
    private EditText promotionEditText;
    private String id_productString, productNameString, productPriceString,
            productImageString, promotionString, promotionPrice, totalString;
    MyConstant myConstant;
    int resultTotal;

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

        //Button Controller


    }   //Main Method

    private void showView() {
        promotionPrice = promotionEditText.getText().toString().trim();
        resultTotal = Integer.parseInt(productPriceString) - (Integer.parseInt(productPriceString) * (Integer.parseInt(promotionString)/100));

        Picasso.with(EditPromotionManage.this).load(productImageString).into(showImageView);
        nameFoodTextView.setText(productNameString);
        promotionEditText.setText(promotionString);
        priceNowTextView.setText("ราคาปัจจุบัน : " + productPriceString + " บาท");
        priceFutureTextView.setText("ราคาที่ลด : " + resultTotal + " บาท");

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
    }
}   //Main Class
