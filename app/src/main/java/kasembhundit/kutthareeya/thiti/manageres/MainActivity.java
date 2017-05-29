package kasembhundit.kutthareeya.thiti.manageres;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create ListView
        createListView();


    }   //Main Method

    private void createListView() {
        ListView listView = (ListView) findViewById(R.id.LivNews);

        try {

        } catch (Exception e) {
            Log.d("20MayV1", "e createListView ==>" + e.toString());
        }
    }   //create ListView

}   //main class
