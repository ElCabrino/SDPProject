package ch.epfl.sweng.vanjel.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ch.epfl.sweng.vanjel.R;

/**
 * @author Etienne CAQUOT
 * @reviewer
 */
public class ChooseRegistration extends AppCompatActivity implements View.OnClickListener {

    private TextView buttonPatient, buttonDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_registration);

        buttonPatient = findViewById(R.id.patientButton);
        buttonPatient.setOnClickListener(this);
        buttonDoctor = findViewById(R.id.doctorButton);
        buttonDoctor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Registration.class);
        switch (v.getId()){
            case R.id.patientButton:
                intent.putExtra("DoctorReg", false);
                break;
            case R.id.doctorButton:
                intent.putExtra("DoctorReg", true);
                break;
        }
        v.getContext().startActivity(intent);
    }
}
