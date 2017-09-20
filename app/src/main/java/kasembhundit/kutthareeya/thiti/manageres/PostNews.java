package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.ByteArrayOutputStream;

/**
 * Created by User on 9/19/2017.
 */

public class PostNews extends AsyncTask<String, Void, String> {

    private Context context;
    private String titleString, detailString;
    private Bitmap imageBitmap;

    public PostNews(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Title", params[0])
                    .add("Detail", params[1])
                    .add("Image", params[2])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[3]).post(body).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();


        } catch (Exception e) {
            Log.d("20SepV1", "e from PostNew ==> " + e.toString());
        }

        return null;
    }
}   //Main Class
