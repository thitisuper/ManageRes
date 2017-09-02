package kasembhundit.kutthareeya.thiti.manageres;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ReceiveActivity extends AppCompatActivity {
    private String refString, dateString, timeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        //Get String From SQLite
        getStringFromSQLite();

        //Create Listview
        createListview();

    }   //Main Method

    private void createListview() {
        ListView listView = (ListView) findViewById(R.id.LivReceive);
        String tag = "1SepV1";

        try {

            MyConstant myConstant = new MyConstant();
            GetOrderWhereIdRef getOrderWhereIdRef = new GetOrderWhereIdRef(ReceiveActivity.this);
            getOrderWhereIdRef.execute(refString, myConstant.getUrlGetOrderWhereIdRef());
            String result = getOrderWhereIdRef.get();
            Log.d(tag, "result ==> " + result);

        } catch (Exception e) {
            Log.d(tag, "e createListView ==> " + e.toString());
        }


    }   //Create ListView

    private void getStringFromSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM receiveTABLE ORDER BY id DESC", null);
        cursor.moveToFirst();
        refString = cursor.getString(1);
        dateString = cursor.getString(2);
        timeString = cursor.getString(3);

        TextView refTextView = (TextView) findViewById(R.id.txtRef);
        TextView dateTextView = (TextView) findViewById(R.id.txtDate);
        TextView timeTextView = (TextView) findViewById(R.id.txtTime);

        refTextView.setText(refString);
        dateTextView.setText("วันที่: " + dateString);
        timeTextView.setText("เวลา: " + timeString);


    }
}   //Main Class
