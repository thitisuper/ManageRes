package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AuthenActivity extends AppCompatActivity {

    //Explicit
    private TextView registerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        //Initial View

        initialView();

        //Register Controler
        registerControler();
    } //Main method

    private void registerControler() {
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialView() {
        registerTextView = (TextView) findViewById(R.id.txtRegister);
    }

} //Main Class
