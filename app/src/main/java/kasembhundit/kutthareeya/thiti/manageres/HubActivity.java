package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HubActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView favoriteImageView, foodPackImageView,
            foodSingleImageView, noodleImageView, foodAllImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);

        //Initial View
        initialView();

        //Controller
        controller();

        //Bracker Market Controller
        marketController();


    }   //Main Method

    private void marketController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvMarket);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HubActivity.this, ReceiveActivity.class));
            }
        });
    }

    private void controller() {
        favoriteImageView.setOnClickListener(this);
        foodPackImageView.setOnClickListener(this);
        foodSingleImageView.setOnClickListener(this);
        noodleImageView.setOnClickListener(this);
        foodAllImageView.setOnClickListener(this);
    }

    private void initialView() {
        favoriteImageView = (ImageView) findViewById(R.id.imvFavorite);
        foodPackImageView = (ImageView) findViewById(R.id.imvPack);
        foodSingleImageView = (ImageView) findViewById(R.id.imvFood);
        noodleImageView = (ImageView) findViewById(R.id.imvNoodle);
        foodAllImageView = (ImageView) findViewById(R.id.imvFoodAll);
    }

    @Override
    public void onClick(View v) {
        int intFavorite = 0;
        int intGroup = 0;

        switch (v.getId()) {
            case R.id.imvFavorite:
                intFavorite = 1;
                break;
            case R.id.imvPack:
                intGroup = 0; //อาหารจานเดี่ยว
                break;
            case R.id.imvFood:
                intGroup = 1; // กับข้าว
                break;
            case R.id.imvNoodle:
                intGroup = 2; // อาหารประเภทเส้น
                break;
            case R.id.imvFoodAll:
                intGroup = 3; // อาหารทั้งหมด
                break;
        }


        Intent intent = new Intent(HubActivity.this, ServiceActivity.class);
        intent.putExtra("Favorite", intFavorite);
        intent.putExtra("Group", intGroup);
        intent.putExtra("Login", getIntent().getStringArrayExtra("Login"));
        startActivity(intent);

    }
}   //Main Class
