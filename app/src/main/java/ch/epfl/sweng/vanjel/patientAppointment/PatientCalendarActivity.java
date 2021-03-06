package ch.epfl.sweng.vanjel.patientAppointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;

import ch.epfl.sweng.vanjel.R;

/**
 * @author Vincent CABRINI
 * @reviewer
 */
public class PatientCalendarActivity extends AppCompatActivity implements View.OnClickListener{

    private String doctorUID;
    private String selectedDate = Calendar.getInstance().getTime().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_calendar);

        doctorUID = getIntent().getStringExtra("doctorUID");

        findViewById(R.id.buttonSelectSchedule).setOnClickListener(this);

        setSelectedDateListener();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSelectSchedule){
            launchAppointmentActivity();
        }
    }

    private void launchAppointmentActivity() {
        Intent intent = new Intent(PatientCalendarActivity.this,PatientAppointmentActivity.class);
        intent.putExtra("date", selectedDate);
        intent.putExtra("doctorUID", doctorUID);
        startActivity(intent);
    }

    private void setSelectedDateListener(){
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month,
                                            int day) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                selectedDate = c.getTime().toString();
            }
        });
    }
}
