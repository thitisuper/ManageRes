package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {
    private ImageView backImageView;
    private TextView startDateTextView,endDateTextView,totalTextView;
    private ImageButton startDateImageButton,endDateImageButton;
    private int mYear,mMonth,mDay,mDayTwo,mMonthTwo,mYearTwo;
    static final int CALENDAR_VIEW_ID = 0;
    static final int CALENDAR_VIEW_ID_END = 1;
    private Bundle bundle;
    private String startDateShow, endDateShow;

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

        try {

            List<Date> dates = new ArrayList<Date>();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDateDate = (Date) dateFormat.parse(startDateShow);
            Date endDateDate = (Date) dateFormat.parse(endDateShow);
            long interval = 24 * 1000 * 60 * 60;
            long eneTime = endDateDate.getTime();
            long curTime = startDateDate.getTime();
            while (curTime <= eneTime) {
                dates.add(new Date(curTime));
                curTime += interval;
            }
            for (int i=0; i<dates.size(); i++) {
                Date lDate = (Date) dates.get(i);
                String ds = dateFormat.format(lDate);
                Log.d("10OctV1", "Date is = " + ds + "\n");
            }

        } catch (Exception e) {
            Log.d("10OctV1", "e ==> " + e.toString());
        }




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
                        .append(mDay).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mYear + 543).append(" "));
        endDateTextView.setText(
                new StringBuilder()
                        .append(mDay).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mYear + 543).append(" "));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CALENDAR_VIEW_ID:
                if (resultCode == RESULT_OK) {
                    bundle = data.getExtras();
                    startDateShow = bundle.getString("dateSelected");
                    startDateTextView.setText(startDateShow);
                    break;
                }
            case CALENDAR_VIEW_ID_END:
                if (resultCode == RESULT_OK) {
                    bundle = data.getExtras();
                    endDateShow = bundle.getString("dateSelected");
                    endDateTextView.setText(endDateShow);
                }
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

    }
}   //Main Class
