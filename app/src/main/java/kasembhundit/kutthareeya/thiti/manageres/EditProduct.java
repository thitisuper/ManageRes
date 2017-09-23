package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by User on 9/23/2017.
 */

public class EditProduct extends AsyncTask<String, Void, String>{

    private Context context;

    public EditProduct(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("id", params[0])
                    .add("ProductName", params[1])
                    .add("ProductPrice", params[2])
                    .add("ProductImage", params[3])
                    .add("Category", params[4])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[5]).post(body).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("23SepV2", "e doIn EditProduct ==> " + e.toString());
            return null;
        }
    }
}   //Main Class
