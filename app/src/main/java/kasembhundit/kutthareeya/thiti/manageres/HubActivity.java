package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HubActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView favoriteImageView, foodPackImageView,
            foodSingleImageView, noodleImageView, foodAllImageView,
            profileImageView, promotionImageView;
    TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        String[] loginString = getIntent().getStringArrayExtra("Login");
        Log.d("20SepV2", "id ==> " + loginString[0]);
        Log.d("20SepV2", "Name ==> " + loginString[1]);
        Log.d("20SepV2", "Surname ==> " + loginString[2]);
        Log.d("20SepV2", "Build ==> " + loginString[3]);
        Log.d("20SepV2", "Room ==> " + loginString[4]);
        Log.d("20SepV2", "User ==> " + loginString[5]);
        Log.d("20SepV2", "password ==> " + loginString[6]);
        Log.d("20SepV2", "status ==> " + loginString[7]);

        userTextView = (TextView) findViewById(R.id.txtUser);
        userTextView.setText(loginString[1] + " " + loginString[2]);

        //Initial View
        initialView();

        //Controller
        controller();

        //profile Controller
        profileController();


    }   //Main Method

    private void profileController() {
        profileImageView = (ImageView) findViewById(R.id.imvProfife);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HubActivity.this, UserActivity.class);
                intent.putExtra("Login", getIntent().getStringArrayExtra("Login"));
                startActivity(intent);
            }
        });
    }

    private void controller() {
        favoriteImageView.setOnClickListener(this);
        foodPackImageView.setOnClickListener(this);
        foodSingleImageView.setOnClickListener(this);
        noodleImageView.setOnClickListener(this);
        foodAllImageView.setOnClickListener(this);
        promotionImageView.setOnClickListener(this);
    }

    private void initialView() {
        favoriteImageView = (ImageView) findViewById(R.id.imvFavorite);
        foodPackImageView = (ImageView) findViewById(R.id.imvPack);
        foodSingleImageView = (ImageView) findViewById(R.id.imvFood);
        noodleImageView = (ImageView) findViewById(R.id.imvNoodle);
        foodAllImageView = (ImageView) findViewById(R.id.imvFoodAll);
        promotionImageView = (ImageView) findViewById(R.id.imvPromotion);
    }

    @Override
    public void onClick(View v) {
        int intFavorite = 0;
        int intGroup = 0;

        switch (v.getId()) {
            case R.id.imvFavorite:
                intFavorite = 1;
                break;
            case R.id.imvPromotion:
                intFavorite = 2;
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
