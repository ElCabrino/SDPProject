package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DoctorCalendar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<DoctorCalendarItem> calendarItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_calendar);

        recyclerView = (RecyclerView) findViewById(R.id.calender_recyclerView);
        recyclerView.setHasFixedSize(true); // every item in recycler view has a fixed size
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        calendarItems = new ArrayList<>();
        //LocalDate.now();

        Calendar cal = Calendar.getInstance();
        // dummy dates and text
        for(int i = 0; i <= 30; i++) {
            DoctorCalendarItem calendarItem = new DoctorCalendarItem(formatCalendarString(cal.getTime().toString()),
                    "No consultation");
            calendarItems.add(calendarItem);
            cal.add(Calendar.DATE, 1);
        }

        adapter = new DoctorCalendarAdapter(calendarItems, this);

        recyclerView.setAdapter(adapter);

    }


    private String formatCalendarString(String s) {
        return s.substring(0,10) + s.substring(29,s.length());
    }



}
