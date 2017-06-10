package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

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
        MyAlert myAlert = new MyAlert(AuthenActivity.this);

        try {
            GetAllData getAllData = new GetAllData(AuthenActivity.this);
            getAllData.execute(myConstant.getUrlGetUser());
            String strJSON = getAllData.get();
            Log.d(tag, "JSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            String[] columnStrings = myConstant.getColumnUserStrings();
            String[] loginStrings = new String[columnStrings.length];
            boolean b = true;

            for (int i=0; i<jsonArray.length(); i+=1){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString(columnStrings[5]))) {
                    b = false;
                    for (int i1=0; i1<columnStrings.length; i1+=1) {
                        loginStrings[i1] = jsonObject.getString(columnStrings[i1]);
                        Log.d(tag, "loginString " + i1 + ") ==>" + loginStrings[i1]);
                    }   //End Inner For Loop
                }   // End if loop
            }// End for loop

            if (b) {
                //User False
                myAlert.myDialogError("User False", "No This User in my Database");
            } else if (passwordString.equals(loginStrings[6])) {
                //Password True
                Toast.makeText(AuthenActivity.this,
                        "Welcome " + loginStrings[1],
                        Toast.LENGTH_SHORT).show();

                myIntentToService(Integer.parseInt(loginStrings[7]), loginStrings);
            } else {
                //Password Failed
                myAlert.myDialogError("Password Failed", "Please Try Again");
            }

        } catch (Exception e) {
            Log.d(tag, "e check ==>" + e.toString());
        }

    }

    private void myIntentToService(int key, String[] loginStrings) {
        Class<?> aClass = null;
        switch (key){
            case 0:
                aClass = HubActivity.class;
                break;
            case 1:
                aClass = MerchantActivity.class;
                break;
        }

        Intent intent = new Intent(AuthenActivity.this, aClass);
        intent.putExtra("Login", loginStrings);
        startActivity(intent);
        finish();

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
