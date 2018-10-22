package ch.epfl.sweng.vanjel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DoctorInformation extends AppCompatActivity {

    TextView doctorUID;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_information);

        init();
        String uid = bundle.getString("doctorUID");
        doctorUID.setText(uid);


    }

    private void init(){
        bundle = getIntent().getExtras();
        doctorUID = findViewById(R.id.doctorUID);
    }
}
