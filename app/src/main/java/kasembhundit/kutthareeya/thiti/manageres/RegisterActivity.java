package kasembhundit.kutthareeya.thiti.manageres;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

        //Create Spinner
        createSpinner();

    }   //Main Method

    private void createSpinner() {

        MyConstant myConstant = new MyConstant();
        final String[] buildStrings = myConstant.getBuildStrings();

        //For Build Spinner
        ArrayAdapter<String> buildArrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, buildStrings);
        buildSpinner.setAdapter(buildArrayAdapter);

        buildSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buildString = buildStrings[position];
                createRoomSpinner(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                buildString = buildStrings[0];
            }
        });

    }

    private void createRoomSpinner(int key) {

        String[] roomStrings = null;
        MyConstant myConstant = new MyConstant();
        switch (key) {
            case 0:
                roomStrings = myConstant.getRoomAStrings();
                break;
            case 1:
                roomStrings = myConstant.getRoomBStrings();
                break;
            case 2:
                roomStrings = myConstant.getRoomCStrings();
                break;
        }
        ArrayAdapter<String> roomArrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, roomStrings);
        roomSpinner.setAdapter(roomArrayAdapter);

        final String[] finalRoomStrings = roomStrings;
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                roomString = finalRoomStrings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                roomString = finalRoomStrings[0];
            }
        });

    }

    private void buttonController() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value EditText
                nameString = nameEditText.getText().toString().trim();
                surnameString = surnameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(RegisterActivity.this);
                MyConstant myConstant = new MyConstant();

                if (nameString.equals("") || surnameString.equals("") ||
                        userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    myAlert.myDialogError(getResources().getString(R.string.titleHaveSpace),
                            getResources().getString(R.string.messageHaveSpace));
                } else {
                    confirmValue();
                }

            }   //onClick
        });
    }

    private void confirmValue() {
        String tag = "30MayV2";
        Log.d(tag, "Name =>" + nameString);
        Log.d(tag, "Surname =>" + surnameString);
        Log.d(tag, "User =>" + userString);
        Log.d(tag, "Password =>" + passwordString);
        Log.d(tag, "Build =>" + buildString);
        Log.d(tag, "Room =>" + roomString);

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setCancelable(false);
        builder.setTitle("Please Confirm!");
        builder.setMessage("Name = " + nameString + "\n" +
                "SurName = " + surnameString + "\n" +
                "User = " + userString + "\n" +
                "Password = " + passwordString + "\n" +
                "Build = " + buildString + "\n" +
                "Room = " + roomString);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadValueToServer();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private void uploadValueToServer() {
        try {
            MyConstant myConstant = new MyConstant();
            PostNewUser postNewUser = new PostNewUser(RegisterActivity.this);
            postNewUser.execute(nameString, surnameString, buildString,
                    roomString, userString, passwordString,
                    myConstant.getUrlPostUser());
            String strResult = postNewUser.get();
            Log.d("10JuneV1", "Result ==> " + strResult);

            if (Boolean.parseBoolean(strResult)) {
                finish();
            } else {
                MyAlert myAlert = new MyAlert(RegisterActivity.this);
                myAlert.myDialogError("Cannot Upload Value",
                        "Please Try Again Cannot Upload Value To Server");
            }
        } catch (Exception e) {
            Log.d("10JuneV1", "e upload ==> " + e.toString());
        }
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
