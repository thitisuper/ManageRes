package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by User on 9/1/2017.
 */

public class PostOrderToServer extends AsyncTask<String, Void, String>{

    private Context context;

    public PostOrderToServer(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("id_User", params[0])
                    .add("id_Ref", params[1])
                    .add("id_Food", params[2])
                    .add("Special", params[3])
                    .add("Topping", params[4])
                    .add("Item", params[5])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[6]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}   //Main Class
