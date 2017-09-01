package kasembhundit.kutthareeya.thiti.manageres;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 14/7/2560.
 */

public class MyManage {

    private Context context;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {
        this.context = context;

        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }

    public long addReceive(String strRef,
                           String strDate,
                           String strTime) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("Ref", strRef);
        contentValues.put("MyDate", strDate);
        contentValues.put("MyTime", strTime);

        return sqLiteDatabase.insert("receiveTABLE", null, contentValues);
    }

    //เปลี่ยนให้เป็นตัวเลข
    public long addOrder(String id_Food,
                         String Special,
                         String Topping,
                         String Item) {

        ContentValues contentValues = new ContentValues();//เปลี่ยน String
        contentValues.put("id_Food", id_Food);
        contentValues.put("Special", Special);
        contentValues.put("Topping", Topping);
        contentValues.put("Item", Item);

        return sqLiteDatabase.insert("orderTABLE", null, contentValues);
    }


}   //Main Class
