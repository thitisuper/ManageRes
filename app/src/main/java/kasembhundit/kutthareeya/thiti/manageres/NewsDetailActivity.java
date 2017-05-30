
package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    //Explicit
    private ImageView backImageView, iconImageView;
    private TextView titleTextView, detialTextView;
    private String titleString, detailString, iconString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        //Initial View
        initialView();

        //Back Controller
        backController();

        //Show View
        showView();


    } //Main Method

    private void showView() {
        titleString = getIntent().getStringExtra("Title");
        detailString = getIntent().getStringExtra("Detail");
        iconString = getIntent().getStringExtra("Icon");

        titleTextView.setText(titleString);
        detialTextView.setText(detailString);
        Picasso.with(this).load(iconString).into(iconImageView);
    }

    private void backController() {
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialView() {
        backImageView = (ImageView) findViewById(R.id.imvBack);
        iconImageView = (ImageView) findViewById(R.id.imvIcon);
        titleTextView = (TextView) findViewById(R.id.txtTitle);
        detialTextView = (TextView) findViewById(R.id.txtDetail);
    }


} //Main Class
