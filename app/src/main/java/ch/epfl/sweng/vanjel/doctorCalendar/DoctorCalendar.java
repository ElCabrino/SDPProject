package ch.epfl.sweng.vanjel.doctorCalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.models.Gender;
import ch.epfl.sweng.vanjel.models.Patient;

/**
 * @author Etienne CAQUOT
 * @reviewer Nicolas BRANDT
 */
public class DoctorCalendar extends AppCompatActivity {

    private ArrayList<DoctorCalendarItem> mData = new ArrayList<>();
    private RecyclerView recyclerView;
    private DoctorCalendarAdapter adapter;
    private Button add;
    Calendar cal = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_calendar);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(v.getContext(), DoctorAvailabilityActivity.class);
                v.getContext().startActivity(intent);*/
                mData.add(new DoctorCalendarItem(cal.getTime().toString().substring(0,10),
                        new Patient("patient@patient.com","Another", "Patient",
                                "1 janvier 1970","rue de ","1",
                                "Lausanne","Switzerland",Gender.Other)));
                adapter.notifyItemInserted(mData.size());
                recyclerView.scrollToPosition(mData.size()-1);
            }
        });

        initRecyclerView();
    }

    /* ADD ITEM
    mData.add(new DoctorCalendarItem(cal.getTime().toString().substring(0,10),
                        new Patient("patient@patient.com","Another", "Patient",
                                "1 janvier 1970","rue de ","1",
                                "Lausanne","Switzerland",Gender.Other)));
                adapter.notifyItemInserted(mData.size());
                recyclerView.scrollToPosition(mData.size()-1);
    */

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.calender_recyclerView);
        adapter = new DoctorCalendarAdapter(mData, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
