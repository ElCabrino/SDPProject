package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PatientCalendarActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_calendar);

        findViewById(R.id.buttonSelectSchedule).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonSelectSchedule){
            launchAppointmentActiviy();
        }
    }

    private void launchAppointmentActiviy() {
        Intent intent = new Intent(PatientCalendarActivity.this,PatientAppointmentActivity.class);
        startActivity(intent);
    }
}
