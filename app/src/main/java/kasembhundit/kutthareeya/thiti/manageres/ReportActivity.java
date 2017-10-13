package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    private ImageView backImageView;
    private TextView startDateTextView, endDateTextView, totalTextView;
    private ImageButton startDateImageButton, endDateImageButton;
    private int mYear, mMonth, mDay, mDayTwo, mMonthTwo, mYearTwo, priceTotalStrings;
    Button saveButton;
    static final int CALENDAR_VIEW_ID = 0;
    static final int CALENDAR_VIEW_ID_END = 1;
    private Bundle bundle;
    private String startDateShow, endDateShow, startTest, endTest;
    private String[] id_foodStrings, foodNameStrings, priceOrderStrings,
            dateOrderStrings, itemStrings;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        //Initial View
        initialView();

        //back Controller
        backController();

        //Start Date
        startDate();

        //End Date
        endDate();

        // get the current date
        getTheCurrentDate();

        // display the current date
        updateCurrentDate();

        //Button Controller
        buttonController();


    }   //Main Method

    private void endDate() {
        endDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MyCalendarView.class);
                startActivityForResult(intent, CALENDAR_VIEW_ID_END);
            }
        });
    }

    private void startDate() {
        startDateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, MyCalendarView.class);
                startActivityForResult(intent, CALENDAR_VIEW_ID);
            }
        });
    }

    private void getTheCurrentDate() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    // updates the date we display in the TextView
    private void updateCurrentDate() {
        startDateTextView.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay)
        );
        endDateTextView.setText(
                new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay)
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CALENDAR_VIEW_ID:
                if (resultCode == RESULT_OK) {
                    bundle = data.getExtras();
                    String startGetDate = bundle.getString("dateSelected");
                    startDateTextView.setText(startGetDate);
                    break;
                }
            case CALENDAR_VIEW_ID_END:
                if (resultCode == RESULT_OK) {
                    bundle = data.getExtras();
                    String endGetDate = bundle.getString("dateSelected");
                    endDateTextView.setText(endGetDate);
                    break;
                }
        }
    }

    private void buttonController() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Check Date

                try {

                    startTest = startDateTextView.getText().toString();
                    endTest = endDateTextView.getText().toString();
                    Log.d("10OctV2", "Start Date ==> " + startTest);
                    Log.d("10OctV2", "End Date ==> " + endTest);

                    MyAlert myAlert = new MyAlert(ReportActivity.this);

                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDateDate = (Date) dateFormat.parse(startTest);
                    Date endDateDate = (Date) dateFormat.parse(endTest);
                    String testDate = dateFormat.format(startDateDate);
                    String TestEnd = dateFormat.format(endDateDate);
                    long eneTime = endDateDate.getTime();
                    long curTime = startDateDate.getTime();

                    if (curTime > eneTime) {
                        //Have Space
                        myAlert.myDialogError("เลือกวันที่ไม่ถูกค่ะ",
                                "กรุณาตรวจสอบวันที่เริ่มและวันที่สุดท้ายใหม่อีกครั้งค่ะ");
                    } else {

                        Log.d("12OctV2", "StartDateTest :: " + testDate);
                        Log.d("12OctV2", "EndDateTest :: " + TestEnd);
                        //Select Date to ListView
                        selectDateToListview(testDate, TestEnd);
//                        while (curTime <= eneTime) {
//                            dates.add(new Date(curTime));
//                            curTime += interval;
//                        }
//                        for (int i = 0; i < dates.size(); i++) {
//                            Date lDate = (Date) dates.get(i);
//                            String ds = dateFormat.format(lDate);
//                        }
//                        //Log.d("10OctV1", "Date is = " + ds);
                    }

                } catch (Exception e) {
                    Log.d("12OctV2", "e Button Report ==> " + e.toString());
                }
            }   //End Onclick
        });
    }

    private void selectDateToListview(String testDate, String testEnd) {
        try {
            MyConstant myConstant = new MyConstant();
            CheckDateMoney checkDateMoney = new CheckDateMoney(ReportActivity.this);
            checkDateMoney.execute(testDate, testEnd, myConstant.getUrlCheckDate());
            String result = checkDateMoney.get();
            Log.d("12OctV2", "result ==> " + result);

            //จองหน่วยความจำ
            JSONArray jsonArray = new JSONArray(result);
            int lengthAnInt = jsonArray.length();
            id_foodStrings = new String[lengthAnInt];
            foodNameStrings = new String[lengthAnInt];
            priceOrderStrings = new String[lengthAnInt];
            dateOrderStrings = new String[lengthAnInt];
            itemStrings = new String[lengthAnInt];

            for (int i = 0; i < lengthAnInt; i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_foodStrings[i] = jsonObject.getString("id_Food");
                foodNameStrings[i] = myFindName(0, id_foodStrings[i]);
                priceOrderStrings[i] = jsonObject.getString("PriceOrder");
                dateOrderStrings[i] = jsonObject.getString("Dateorder");
                itemStrings[i] = jsonObject.getString("Item");
            }

            //Create ListView
            ListView listView = (ListView) findViewById(R.id.livShowInfo);
            ReportAdapter reportAdapter = new ReportAdapter(ReportActivity.this, foodNameStrings,
                    dateOrderStrings, priceOrderStrings);
            listView.setAdapter(reportAdapter);

            //Show Total
            int totalAnInt = 0;
            Log.d("12OctV2", "Test ==> " + itemStrings.length);
            for (int i = 0; i < itemStrings.length; i += 1) {
                totalAnInt = totalAnInt + Integer.parseInt(priceOrderStrings[i]);
            }
            NumberFormat numberFormat = NumberFormat.getNumberInstance();
            String totalMoney = numberFormat.format(totalAnInt);

            TextView totalTextView = (TextView) findViewById(R.id.txtTotalReport);
            totalTextView.setText("ราคารวม: " + totalMoney + " บาท");


        } catch (Exception e) {
            Log.d("12OctV2", "e SelectDateToListView ==> " + e.toString());
            MyAlert myAlert1 = new MyAlert(ReportActivity.this);
            //Have Space
            myAlert1.myDialogError(null,
                    "ไม่มีรายการในวันที่คุณต้องการค่ะ");
        }
    }

    private String myFindName(int index, String id_foodStrings) {

        String tag = "12OctV2";
        MyConstant myConstant = new MyConstant();
        String myResultString = null;

        try {

            GetFoodWhere getFoodWhere = new GetFoodWhere(ReportActivity.this);
            getFoodWhere.execute("id", id_foodStrings, myConstant.getUrlGetProductWhereID());
            String resultString = getFoodWhere.get();
            Log.d(tag, "result ==> " + resultString);

            JSONArray jsonArray = new JSONArray(resultString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            switch (index) {
                case 0:
                    myResultString = jsonObject.getString("ProductName");
                    break;
            }

            return myResultString;

        } catch (Exception e) {
            Log.d(tag, "e to myFindName ==> " + e.toString());
            return null;
        }

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
        startDateTextView = (TextView) findViewById(R.id.txtStartDate);
        endDateTextView = (TextView) findViewById(R.id.txtEndDate);
        startDateImageButton = (ImageButton) findViewById(R.id.imvButtonStart);
        endDateImageButton = (ImageButton) findViewById(R.id.imvButtonEnd);
        backImageView = (ImageView) findViewById(R.id.imvBack);
        saveButton = (Button) findViewById(R.id.btnDate);

    }
}   //Main Class
