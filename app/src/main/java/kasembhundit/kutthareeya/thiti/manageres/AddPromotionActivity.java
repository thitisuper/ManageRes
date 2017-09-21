package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;

public class AddPromotionActivity extends AppCompatActivity {
    ImageView imageBack, addNewImageView;
    EditText titleEditText, detailEditText;
    String titleString, detailString;
    Button saveButton;
    Context context;

    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);

        //Initial View
        initialView();

        //back Controller
        backController();

        //Image Take Photo
        imageTakePhoto();

        //Button Controller
        buttonController();


    }   //Main Method

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //upload to Server
                uploadToServer();

            }
        });
    }

    private void uploadToServer() {
        String tag = "20SepV1";
        Bitmap bitmap = ((BitmapDrawable) addNewImageView.getDrawable()).getBitmap();
        titleString = titleEditText.getText().toString().trim();
        detailString = detailEditText.getText().toString().trim();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        final String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        final MyAlert myAlert = new MyAlert(AddPromotionActivity.this);

        if (titleString.equals("") || detailString.equals("") || encodedImage.equals("")) {
            //มีช่องว่าง
            myAlert.myDialogError("มีช่องว่าง", "กรุณากรอกให้ครบทุกช่องนะคะ");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPromotionActivity.this);
            builder.setCancelable(false);
            builder.setTitle("ยืนยันการเพิ่มโปรโมชั่น");
            builder.setMessage("หัวข้อ = " + titleString + "\n" +
                    "รายละเอียด" + "\n" + detailString + "\n");
            builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        MyConstant myConstant = new MyConstant();
                        PostNews postNews = new PostNews(AddPromotionActivity.this);
                        postNews.execute(titleString, detailString, encodedImage, myConstant.getUrlPostNew());
                        String result = postNews.get();
                        Log.d("21SepV1", "result ==> " + result);

                        if (Boolean.parseBoolean(result)) {
                            finish();
                        } else {
                            MyAlert myAlert1 = new MyAlert(AddPromotionActivity.this);
                            myAlert1.myDialogError("ไม่สามารถเพิ่มข่าวสารได้", "กรุณาเช็คความถูกต้องให้เรียบร้อยก่อนยืนยัน");
                        }

                        dialog.dismiss();

                    } catch (Exception e) {
                        Log.d("20SepV1", "e to ยืนยันส่งข้อมูลไปเซิฟเวอร์ ==> " + e.toString());
                    }
                }
            });
            builder.show();
        }

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
                addNewImageView.setImageBitmap(newProfilePic);
            }
        }
    }

    private void imageTakePhoto() {
        addNewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Take Photo
                takePhoto();
            }
        });
    }

    private void takePhoto() {
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

    private void backController() {
        imageBack = (ImageView) findViewById(R.id.imvBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialView() {
        addNewImageView = (ImageView) findViewById(R.id.imvAddNew);
        titleEditText = (EditText) findViewById(R.id.edtNameNews);
        detailEditText = (EditText) findViewById(R.id.edtDetail);
        saveButton = (Button) findViewById(R.id.btnSave);
    }


}   //Main Class
