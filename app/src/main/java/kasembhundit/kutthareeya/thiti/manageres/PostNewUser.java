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
 * Created by User on 10/6/2560.
 */

public class PostNewUser extends AsyncTask<String, Void, String>{

    private Context context;

    public PostNewUser(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", params[0])
                    .add("Surname", params[1])
                    .add("Build", params[2])
                    .add("Room", params[3])
                    .add("User", params[4])
                    .add("Password", params[5])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[6]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("10JuneV1", "e doIn ==> " + e.toString());
            return null;
        }
    }
}   //Main Class
