package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class RegisterActivity extends AppCompatActivity {

    //Explicit
    private ImageView imageView;
    private EditText nameEditText, surnameEditText,
            userEditText, passwordEditText;
    private Spinner buildSpinner, roomSpinner;
    private Button button;
    private String nameString, surnameString, userString,
            passwordString, buildString, roomString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Initial View
        initialView();

        //Back Controller
        backController();

        //Button Controller
        buttonController();

    }   //Main Method

    private void buttonController() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value EditText
                nameString = nameEditText.getText().toString().trim();
                surnameString = surnameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                if (nameString.equals("") || surnameString.equals("") ||
                        userString.equals("") || passwordString.equals("")) {
                    //Have Space

                }

            }   //onClick
        });
    }

    private void backController() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialView() {
        imageView = (ImageView) findViewById(R.id.imvBack);
        nameEditText = (EditText) findViewById(R.id.edtname);
        surnameEditText = (EditText) findViewById(R.id.edtSurname);
        userEditText = (EditText) findViewById(R.id.edtUser);
        passwordEditText = (EditText) findViewById(R.id.edtPassword);
        buildSpinner = (Spinner) findViewById(R.id.spnBuilder);
        roomSpinner = (Spinner) findViewById(R.id.spnRoom);
        button = (Button) findViewById(R.id.btnRegister);
    }

}   //Main Class
