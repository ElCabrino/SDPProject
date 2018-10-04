package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseRegistration extends AppCompatActivity {

    private Button buttonPatient ;
    private Button buttonDoctor ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_registration);

        buttonPatient = findViewById(R.id.patientButton);
        buttonDoctor = findViewById(R.id.doctorButton);


        buttonPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRegistration.this,PatientRegistration.class);
                startActivity(intent);
            }
        });

        buttonDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseRegistration.this,DoctorRegistration.class);
                startActivity(intent);
            }
        });
    }
}
