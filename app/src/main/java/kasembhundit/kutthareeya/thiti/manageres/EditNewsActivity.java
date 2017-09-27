package kasembhundit.kutthareeya.thiti.manageres;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class EditNewsActivity extends AppCompatActivity {

    MyConstant myConstant = new MyConstant();
    private ImageView backImageView, editImageView;
    private EditText titleEditText, detailEditText;
    private String id_newStrings, titleStrings, detailStrings,
            imageStrings;
    private Button saveButton;

    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news);

        //Initial View
        initialView();

        //Back Controller
        backController();

        //Get Value From EditNewSListActivity
        getValueFromIntent();

        //Show View
        showView();

        //Take Photo
        takePhoto();

        //Button Controller
        buttonController();

    }   //main Method

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Upload to Server
                uploadToServer();

            }

            private void uploadToServer() {
                final String tag = "27SepV2";

                Bitmap bitmap = ((BitmapDrawable) editImageView.getDrawable()).getBitmap();
                titleStrings = titleEditText.getText().toString().trim();
                detailStrings = detailEditText.getText().toString().trim();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                MyAlert myAlert = new MyAlert(EditNewsActivity.this);
                if (titleStrings.equals("") || detailStrings.equals("")) {
                    myAlert.myDialogError("มีช่องว่างค่ะ",
                            "กรุณากรอกข้อมูลให้ครบถ้วนค่ะ");
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditNewsActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("ตรวจเช็คข้อมูลให้ถูกต้อง");
                    builder.setMessage("ชื่ออาหาร = " + titleStrings + "\n" +
                            "รายละเอียด = " + detailStrings);
                    builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("ตกลง", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try {
                                myConstant = new MyConstant();
                                EditNews editNews = new EditNews(EditNewsActivity.this);
                                editNews.execute(id_newStrings, titleStrings, detailStrings, encodedImage,
                                        myConstant.getUrlUpdateNews());
                                String result = editNews.get();
                                Log.d(tag, "result ==> " + result);

                                if (Boolean.parseBoolean(result)) {
                                    finish();
                                    Toast.makeText(EditNewsActivity.this,
                                            "แก้ไขข่าวสารเรียบร้อยแล้วค่ะ",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    MyAlert myAlert1 = new MyAlert(EditNewsActivity.this);
                                    myAlert1.myDialogError("ไม่สามารถแก้ไขข่าวสารได้", "กรุณาเช็คความถูกต้องให้เรียบร้อยค่ะ");
                                }
                                dialog.dismiss();

                            } catch (Exception e) {
                                Log.d(tag, "e Click ==> " + e.toString());
                            }

                        }
                    });
                    builder.show();
                }

            }
        });
    }

    private void takePhoto() {
        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                intent.putExtra("crop", "true");
                intent.putExtra("scale", true);
                intent.putExtra("outputX", 500);
                intent.putExtra("outputY", 500);
                intent.putExtra("asprctX", 2);
                intent.putExtra("aspectY", 2);
                intent.putExtra("return-data", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                editImageView.setImageBitmap(newProfilePic);
            }
        }
    }

    private void showView() {
        titleEditText.setText(titleStrings);
        detailEditText.setText(detailStrings);
        Picasso.with(EditNewsActivity.this).load(imageStrings).into(editImageView);
    }

    private void getValueFromIntent() {
        id_newStrings = getIntent().getStringExtra("id");
        titleStrings = getIntent().getStringExtra("Title");
        detailStrings = getIntent().getStringExtra("Detail");
        imageStrings = getIntent().getStringExtra("Icon");
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
        editImageView = (ImageView) findViewById(R.id.imvAddNew);
        titleEditText = (EditText) findViewById(R.id.edtNameNews);
        detailEditText = (EditText) findViewById(R.id.edtDetail);
        saveButton = (Button) findViewById(R.id.btnSave);
    }
}   //Main Class
