package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {

    private TextView usernameTextView, lastOrderTextView, orderTextView;
    private ImageView backImageView, lastOrderImageView, orderImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        String[] loginStrings = getIntent().getStringArrayExtra("Login");

        //Show Name Surname
        usernameTextView = (TextView) findViewById(R.id.txtUsername);
        usernameTextView.setText(loginStrings[1] + " " + loginStrings[2]);

        //Back Controller
        backController();

        //Market last order
        marketLastOrder();

        //order List
        orderList();


    }   //Main Method

    private void orderList() {
        orderTextView = (TextView) findViewById(R.id.txtOrder);
        orderImageView = (ImageView) findViewById(R.id.imvOrder);
        orderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, StatusActivity.class);
                intent.putExtra("Login", getIntent().getStringArrayExtra("Login"));
                startActivity(intent);
            }
        });
    }

    private void marketLastOrder() {
        lastOrderTextView = (TextView) findViewById(R.id.txtLastOrder);
        lastOrderImageView = (ImageView) findViewById(R.id.imvLastOrder);
        lastOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ReceiveActivity.class));
            }
        });
        lastOrderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserActivity.this, ReceiveActivity.class));
            }
        });

    }

    private void backController() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}   //Main Class
