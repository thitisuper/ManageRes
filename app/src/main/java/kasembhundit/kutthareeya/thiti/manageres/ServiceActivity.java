package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView backImageView, showOrderImageView;
    private TextView titleTextView, nameTextView;
    private ListView listView;
    private int favoriteAnInt, groupAnInt;
    private String[] loginStrings;
    private String tag = "10JuneV2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        //Initial VIew
        initialVIew();

        //Get Value And Set Up
        getValueAndSetUp();

        //Controller
        controller();

        //Create ListView
        createListView();

    }   //Main Method

    private void createListView() {

        try {

            MyConstant myConstant = new MyConstant();
            GetFoodWhere getFoodWhere = new GetFoodWhere(ServiceActivity.this);

            //For Favorite == 1
            if (favoriteAnInt == 1) {
                //Get Favorite
                getFoodWhere.execute("Favorite", "1",
                        myConstant.getUrlGetFoodFavorite());
            } else if (groupAnInt == 0) {
                //อาหารจารเดี่ยว
                getFoodWhere.execute("Group", "0",
                        myConstant.getUrlGetFoodGroup());
            } else if (groupAnInt == 1) {
                //กับข้าว
                getFoodWhere.execute("Group", "1",
                        myConstant.getUrlGetFoodGroup());
            } else if (groupAnInt == 2) {
                //ประเภทเส้น
                getFoodWhere.execute("Group", "2",
                        myConstant.getUrlGetFoodGroup());
            } else {
                //For All Food
            }

            String strJSON = getFoodWhere.get();
            Log.d(tag, "strJSON ==> " + strJSON);

        } catch (Exception e) {
            Log.d(tag, "e createListView ==> " + e.toString());
        }

    }

    private void getValueAndSetUp() {
        favoriteAnInt = getIntent().getIntExtra("Favorite", 0);
        groupAnInt = getIntent().getIntExtra("Group", 0);
        loginStrings = getIntent().getStringArrayExtra("Login");
        Log.d(tag, "Fovorite ==> " + favoriteAnInt);
        Log.d(tag, "Group ==> " + groupAnInt);
        Log.d(tag, "loginStringLength ==> " + loginStrings.length);
    }

    private void controller() {
        backImageView.setOnClickListener(ServiceActivity.this);
        showOrderImageView.setOnClickListener(this);
    }

    private void initialVIew() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        showOrderImageView = (ImageView) findViewById(R.id.imvShowOrder);
        titleTextView = (TextView) findViewById(R.id.txtTitle);
        nameTextView = (TextView) findViewById(R.id.txtName);
        listView = (ListView) findViewById(R.id.livFood);
    }

    @Override
    public void onClick(View v) {

        //For Back
        if (v == backImageView) {
            finish();
        }

        //For Show
        if (v == showOrderImageView) {

        }

    }
}   //Main Class
