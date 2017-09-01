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
 * Created by User on 9/1/2017.
 */

public class GetOrderWhereIdRef extends AsyncTask<String, Void, String> {

    private Context context;

    public GetOrderWhereIdRef(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("id_Ref", params[0])
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(params[1]).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("10JuneV2", "e doIn ==> " + e.toString());
            return null;
        }

    }   //Main Method
}   //Main Class
