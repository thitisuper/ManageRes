package kasembhundit.kutthareeya.thiti.manageres;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CheckOrderActivity extends AppCompatActivity {

    private String myTimeString, myDateString,
            deliveryString = "มารับเอง", strCurrentTime;
    private TextView textView;
    private int intHour, intMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);

        receiveTimeProduct();

        setUpTimeAndDate();

        //Add Order
        addOrder();

        //Save Order To Server
        saveOrderToServer();

        //Radio Controller
        radioController();

        //Create ListView
        createListView();

    }   //Main Method

    private void radioController() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radDelivery);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.radDelivery1:
                        deliveryString = "มารับเอง";
                        break;
                    case R.id.radDelivery2:
                        deliveryString = "จัดส่งที่ห้อง";
                        break;
                }
            }
        });
    }

    private void setUpTimeAndDate() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        myDateString = dateFormat.format(calendar.getTime());

        myTimeString = strCurrentTime;

    }


    private void receiveTimeProduct() {

        String tag = "13JulyV2";

        //Get Current Time
        final Calendar calendar = Calendar.getInstance();

        MyConstant myConstant = new MyConstant();

        int addMinus = myConstant.getTimeAnInt();

        calendar.add(Calendar.MINUTE, addMinus);

        final DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        strCurrentTime = dateFormat.format(calendar.getTime());
        Log.d(tag, "Time ==> " + strCurrentTime);

        textView = (TextView) findViewById(R.id.txtShowTime);
        myShowTime(strCurrentTime);

        intHour = calendar.get(Calendar.HOUR_OF_DAY);
        intMinus = calendar.get(Calendar.MINUTE);

        //Image Controller
        ImageView imageView = (ImageView) findViewById(R.id.imvChooseTime);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CheckOrderActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int Hour, int Minus) {

                                if (Hour >= intHour) {

                                    calendar.set(Calendar.HOUR_OF_DAY, Hour);
                                    calendar.set(Calendar.MINUTE, Minus);
                                    String chooseTime = dateFormat.format(calendar.getTime());
                                    myShowTime(chooseTime);

                                } else {

                                    MyAlert myAlert = new MyAlert(CheckOrderActivity.this);
                                    myAlert.myDialogError("ชั่วโมงย้อนหลัง", "โปรดเลือกชั่วโมงใหม่");

                                }

                            }
                        }, intHour, intMinus, false);
                timePickerDialog.show();
            }
        });
    }   //Receive Time


    private void myShowTime(String strTime) {
        textView.setText("เวลารับอาหาร : " + strTime);
        Log.d("2SepV2", "strCurrentTime เก่า ==> " + strCurrentTime);
        Log.d("2SepV2", "strTime ใหม่ ==> " + strTime);
        strCurrentTime = strTime;
        Log.d("2SepV2", "strCurrentTime ใหม่ ==> " + strCurrentTime);
    }

    private void createListView() {
        ListView listView = (ListView) findViewById(R.id.livOrder);
        String tag = "14JulyV2";
        int totalPrice = 0;

        try {

            final SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            final String[] idStrings = new String[cursor.getCount()];
            String[] id_FoodStrings = new String[cursor.getCount()];
            String[] specialStrings = new String[cursor.getCount()];
            String[] toppingStrings = new String[cursor.getCount()];
            String[] itemStrings = new String[cursor.getCount()];
            String[] nameFoodStrings = new String[cursor.getCount()];
            String[] showSpecialStrings = new String[cursor.getCount()];
            String[] factorPriceStrings = new String[cursor.getCount()];
            int[] priceInts = new int[cursor.getCount()];
            String[] showPriceStrings = new String[cursor.getCount()];//จองหน่วยความจำ

            String[] strings = new String[]{"ธรรมดา", "พิเศษ"};

            for (int i = 0; i < cursor.getCount(); i += 1) {

                idStrings[i] = cursor.getString(0);
                id_FoodStrings[i] = cursor.getString(1);
                specialStrings[i] = cursor.getString(2);
                toppingStrings[i] = cursor.getString(3);
                itemStrings[i] = cursor.getString(4);

                nameFoodStrings[i] = findNameFood(id_FoodStrings[i], true);
                Log.d(tag, "nameFood[ " + i + "] ==> " + nameFoodStrings[i]);

                factorPriceStrings[i] = findNameFood(id_FoodStrings[i], false);
                priceInts[i] = Integer.parseInt(factorPriceStrings[i]) * Integer.parseInt(itemStrings[i]);
                totalPrice = totalPrice + priceInts[i];
                showPriceStrings[i] = Integer.toString(priceInts[i]);

                showSpecialStrings[i] = strings[Integer.parseInt(specialStrings[i])];

                cursor.moveToNext();
            }   //for

            TextView textView = (TextView) findViewById(R.id.txtShowTotalPrice);
            textView.setText("Total = " + Integer.toString(totalPrice) + " บาท");

            OrderAdapter orderAdapter = new OrderAdapter(CheckOrderActivity.this,
                    nameFoodStrings,
                    showSpecialStrings,
                    itemStrings,
                    showPriceStrings,
                    toppingStrings);
            listView.setAdapter(orderAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.d("14JulyV2", "You Click ID ==> " + idStrings[position]);
                    deleteOrder(idStrings[position], sqLiteDatabase);

                }
            });

            cursor.close();

        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());
        }


    }   //Create ListView

    private void deleteOrder(final String idString, final SQLiteDatabase sqLiteDatabase) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CheckOrderActivity.this);
        builder.setCancelable(false);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("Are You Sure?");
        builder.setMessage("You want to Delete this Order!");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqLiteDatabase.delete("orderTABLE",
                        "id" + "=" + Integer.parseInt(idString), null);
                createListView();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    private String findNameFood(String idFood, boolean status) {

        String tag = "14JulyV2", strResult = null;
        MyConstant myConstant = new MyConstant();
        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(CheckOrderActivity.this);
            getFoodWhere.execute("id", idFood, myConstant.getUrlGetProductWhereID());
            String strJSON = getFoodWhere.get();

            JSONArray jsonArray = new JSONArray(strJSON);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            if (status) {
                strResult = jsonObject.getString("ProductName");
            } else {
                strResult = jsonObject.getString("ProductPrice");
            }

            return strResult;

        } catch (Exception e) {
            Log.d(tag, "e findName ==> " + e.toString());
            return null;
        }
    }

    private void saveOrderToServer() {
        ImageView imageView = (ImageView) findViewById(R.id.imvOrder);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadValueToServer();

            }   //OnClick
        });
    }

    private void uploadValueToServer() {

        String tag = "14JulyV3";
        Random random = new Random();
        int intRandom = random.nextInt(1000);
        String[] loginStrings = getIntent().getStringArrayExtra("Login");
        String id_ref = loginStrings[1] + Integer.toString(intRandom);
        Log.d(tag, "id_user ==> " + loginStrings[0]);
        Log.d(tag, "id_ref ==> " + id_ref);

        try {

            //Read SQLite
            SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                    MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            String[] id_Food = new String[cursor.getCount()];
            String[] Special = new String[cursor.getCount()];
            String[] Topping = new String[cursor.getCount()];
            String[] Item = new String[cursor.getCount()];

            for (int i = 0; i < cursor.getCount(); i += 1) {

                id_Food[i] = cursor.getString(1);
                Special[i] = cursor.getString(2);
                Topping[i] = cursor.getString(3);
                Item[i] = cursor.getString(4);

                Log.d(tag, "id_Food[" + i + "] ==> " + id_Food[i]);

                PostOrderToServer postOrderToServer = new PostOrderToServer(CheckOrderActivity.this);
                MyConstant myConstant = new MyConstant();

                postOrderToServer.execute(loginStrings[0], id_ref, id_Food[i],
                        Special[i], Topping[i], Item[i],
                        myConstant.getUrlPostOrder());

                String result = postOrderToServer.get();
                Log.d(tag, "Result [" + i + "] ==> " + result);
                if (Boolean.parseBoolean(result)) {
                    postOrderToServer.cancel(true);
                    Log.d(tag, "Thread ==> " + postOrderToServer.isCancelled());
                }

                cursor.moveToNext();

            }   //for

            //Delete All SQLite
            sqLiteDatabase.delete("orderTABLE", null, null);

            //Add New Value to ReceiveTABLE
            MyManage myManage = new MyManage(CheckOrderActivity.this);
            myManage.addReceive(id_ref, myDateString, strCurrentTime);

            setupNotification(strCurrentTime);

            //Intent to ReceiveActivity
            Intent intent = new Intent(CheckOrderActivity.this, ReceiveActivity.class);
            startActivity(intent);
            finish();


        } catch (Exception e) {
            Log.d(tag, "e upload ==> " + e.toString());
        }

    }   //Upload Value To Server

    private void setupNotification(String myTimeString) {

        String tag = "2SepV2";
        Log.d(tag, "Time Notification ==> " + myTimeString);

        String[] strings = myTimeString.split(":");
        int hrAnInt = Integer.parseInt(strings[0]);
        int minAnInt = Integer.parseInt(strings[1]);

        Log.d(tag, "hr ==> " + hrAnInt);
        Log.d(tag, "minus ==> " + minAnInt);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hrAnInt);
        calendar.set(Calendar.MINUTE, minAnInt);
        calendar.set(Calendar.SECOND, 0);//ให้ทำงานที่วินาที ที่ 0

        Log.d(tag, "เวลาแจ้งเตือน ==> " + calendar.getTime().toString());

        sentValueToReceiver(calendar);

    }   // Set up Notification


    private void sentValueToReceiver(Calendar calendar) {

        int intRandom = 0;
        Random random = new Random();
        intRandom = random.nextInt(1000);

        Intent intent = new Intent(getBaseContext(), MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),
                intRandom, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);

    }   // sentValue


    private void addOrder() {
        ImageView imageView = (ImageView) findViewById(R.id.imvAddOrder);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}   //Main Class
