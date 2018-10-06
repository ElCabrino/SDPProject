package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseRegistration extends AppCompatActivity {

    private Button buttonPatient;
    private Button buttonDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_registration);

        buttonPatient = findViewById(R.id.patientButton);
        buttonPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(v.getContext(), Registration.class);
                intent1.putExtra("DoctorReg", false);
                v.getContext().startActivity(intent1);
            }
        });

        buttonDoctor = findViewById(R.id.doctorButton);
        buttonDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(v.getContext(), Registration.class);
                intent2.putExtra("DoctorReg", true);
                v.getContext().startActivity(intent2);
            }
        });
    }
}
