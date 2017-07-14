package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 14/7/2560.
 */

public class MyOpenHelper extends SQLiteOpenHelper{

    private Context context;
    //ประกาศตัว name กับ version เสมอ การสร้าง SQLite
    public static final String database_name = "ManageRes.db";
    private static final int database_version = 1;
    private static final String create_order_table = "CREATE TABLE orderTABLE (" +
            "id INTEGER PRIMARY KEY, " +
            "id_Food TEXT," +
            "Special TEXT," +
            "Topping TEXT," +
            "Item TEXT);";

    public MyOpenHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_order_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}   //Main Class
