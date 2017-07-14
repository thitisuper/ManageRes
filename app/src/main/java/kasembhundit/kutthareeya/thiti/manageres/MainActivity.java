package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Explicit
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create ListView
        createListView();

        //Image Controller
        imageController();

    }   //Main Method

    private void imageController() {
        imageView = (ImageView) findViewById(R.id.imvAuthen);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthenActivity.class);
                startActivity(intent);
            }
        });
    }


    private void createListView() {
        ListView listView = (ListView) findViewById(R.id.LivNews);

        try {
            GetAllData getAllData = new GetAllData(this);
            MyConstant myConstant = new MyConstant();
            getAllData.execute(myConstant.getUrlGetNews());
            String strJSON = getAllData.get();
            Log.d("29MayV1", "JSON ==>" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            final String[] titleStrings = new String[jsonArray.length()];
            final String[] detailStrings = new String[jsonArray.length()];
            final String[] imageStrings = new String[jsonArray.length()];

            for (int i = 0;i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                titleStrings[i] = jsonObject.getString("Title");
                detailStrings[i] = jsonObject.getString("Detail");
                imageStrings[i] = jsonObject.getString("Image");

            } // for loop

            //Create ListView
            NewAdapter newAdapter = new NewAdapter(this, titleStrings, detailStrings, imageStrings);
            listView.setAdapter(newAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MainActivity.this, NewsDetailActivity.class);
                    intent.putExtra("Title", titleStrings[position]);
                    intent.putExtra("Icon", imageStrings[position]);
                    intent.putExtra("Detail", detailStrings[position]);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.d("20MayV1", "e createListView ==>" + e.toString());
        }
    }   //create ListView

}   //main class
