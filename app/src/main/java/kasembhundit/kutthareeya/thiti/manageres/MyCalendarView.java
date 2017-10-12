package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class MyCalendarView extends AppCompatActivity {
    private CalendarView calendarView;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calendar_view);

        calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;

                String selectedDate = new StringBuilder()
                        .append(mYear).append("-")
                        .append(mMonth + 1).append("-")
                        .append(mDay).toString();

                Bundle b = new Bundle();
                b.putString("dateSelected", selectedDate);

                //Add the set of extended data to the intent and start it
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();

//                Toast.makeText(MyCalendarView.this,
//                        "วันที่ " + mDay + "-" + mMonth + "-" + mYear,
//                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
