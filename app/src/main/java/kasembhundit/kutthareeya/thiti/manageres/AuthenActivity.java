package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AuthenActivity extends AppCompatActivity {

    //Explicit
    private TextView registerTextView;
    private EditText userEditText, passwordEditText;
    private Button button;
    private String userString, passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authen);

        //Initial View
        initialView();

        //Register Controller
        registerControler();

        //Button Controller
        buttonController();


    } //Main method

    private void buttonController() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value From EditText
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                String tag = "30MayV1";
                String message = null;

                //Check Space
                if ((userString.length() == 0) || (passwordString.length() == 0)) {
                    //Have Space
                    message = "Have Space";

                    MyAlert myAlert = new MyAlert(AuthenActivity.this);
                    myAlert.myDialogError("Have Space", "Please Fill All Every Blank");

                } else {
                    //No Space
                    message = "No Space";
                    CheckUserAndPass();
                }

                Log.d(tag, "Check Space ==> " + message);

            }   // onClick
        });
    }

    private void CheckUserAndPass() {

        String tag = "30MayV1";
        MyConstant myConstant = new MyConstant();

        try {



        } catch (Exception e) {
            Log.d(tag, "e check ==>" + e.toString());
        }

    }

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
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        button = (Button) findViewById(R.id.btnLogin);
    }

} //Main Class
