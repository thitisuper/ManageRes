package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class addFoodActivity extends AppCompatActivity {
    ImageView imageBack;
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

    }   //Main Method

    private void initialView() {
        imageBack = (ImageView) findViewById(R.id.imvBack);
    }
}   //Main Class
