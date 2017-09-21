package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MerchantActivity extends AppCompatActivity {
    TextView addOrderTextView, addNewTextView, editNewTextView, reportTextView, edtFoodTextView;
    ImageView addOrderImageView, addNewImageView, editNewImageView, reportImageView,
            imageBack, edtFoodImageView;
    String addOrderString, addNewString, editNewString, reportString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        //Initial View
        initialView();

        //Button Controller
        buttonController();

        //back Controller
        backController();


    }   //  Main Method

    private void backController() {
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void buttonController() {
        //Intent To Add Product
        addOrderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, addFoodActivity.class);
                startActivity(intent);
            }
        });
        addOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, addFoodActivity.class);
                startActivity(intent);
            }
        });

        edtFoodImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, EditFoodActivity.class);
                startActivity(intent);
            }
        });

        //Intetn To Edit Product
        edtFoodTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, EditFoodActivity.class);
                startActivity(intent);
            }
        });

        //Intent Add To New
        addNewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, AddPromotionActivity.class);
                startActivity(intent);
            }
        });
        addNewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MerchantActivity.this, AddPromotionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialView() {
        addOrderTextView = (TextView) findViewById(R.id.txtAdd);
        addNewTextView = (TextView) findViewById(R.id.txtNew);
        editNewTextView = (TextView) findViewById(R.id.txtEditNew);
        reportTextView = (TextView) findViewById(R.id.txtReport);
        addOrderImageView = (ImageView) findViewById(R.id.imvAddOrder);
        addNewImageView = (ImageView) findViewById(R.id.imvAddNew);
        editNewImageView = (ImageView) findViewById(R.id.imvEditNew);
        reportImageView = (ImageView) findViewById(R.id.imvReport);
        imageBack = (ImageView) findViewById(R.id.imvBack);
        edtFoodImageView = (ImageView) findViewById(R.id.imvEdtFood);
        edtFoodTextView = (TextView) findViewById(R.id.txtEdtFood);
    }
}   //Main Class
