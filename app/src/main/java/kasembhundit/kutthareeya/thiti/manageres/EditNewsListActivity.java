package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class EditNewsListActivity extends AppCompatActivity {

    private String[] id_newStrings, titleStrings, detailStrings, imageStrings;
    private ImageView backImageView, refreshImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_news_list);

        //Initial View
        initialView();

        //Back Controller
        backController();

        //Refresh Controller
        refreshController();

        //Create ListView
        createListView();

    }   //Main Method

    private void createListView() {
        ListView newListView = (ListView) findViewById(R.id.livEdtNews);
        String tag = "27SepV1";

        try {

            GetAllData getAllData = new GetAllData(EditNewsListActivity.this);
            MyConstant myConstant = new MyConstant();
            getAllData.execute(myConstant.getUrlGetNews());
            String strJSON = getAllData.get();
            Log.d(tag, "result ==> " + strJSON);

            //จองหน่วยความจำ
            JSONArray jsonArray = new JSONArray(strJSON);
            id_newStrings = new String[jsonArray.length()];
            titleStrings = new String[jsonArray.length()];
            detailStrings = new String[jsonArray.length()];
            imageStrings = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_newStrings[i] = jsonObject.getString("id");
                titleStrings[i] = jsonObject.getString("Title");
                detailStrings[i] = jsonObject.getString("Detail");
                imageStrings[i] = jsonObject.getString("Image");
            }   //for Loop

            //Create ListView
            NewAdapter newAdapter = new NewAdapter(EditNewsListActivity.this, titleStrings,
                    detailStrings, imageStrings);
            newListView.setAdapter(newAdapter);
            newListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(EditNewsListActivity.this, EditNewsActivity.class);
                    intent.putExtra("id", id_newStrings[position]);
                    intent.putExtra("Title", titleStrings[position]);
                    intent.putExtra("Icon", imageStrings[position]);
                    intent.putExtra("Detail", detailStrings[position]);
                    startActivity(intent);
                }
            });


        } catch (Exception e) {
            Log.d(tag, "e Create Listview ==> " + e.toString());
        }

    }

    private void refreshController() {
        refreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(EditNewsListActivity.this, EditNewsListActivity.class);
                startActivity(intent);
            }
        });
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
        refreshImageView = (ImageView) findViewById(R.id.imvRefresh);

    }
}   //Main Class
