package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorInformation extends AppCompatActivity implements View.OnClickListener {

    TextView lastName, firstName, activity, street, streetNumber, city, country;
    private Bundle bundle;
    private Doctor doctor;
    String doctorUID;
    // database
    FirebaseDatabase database;
    DatabaseReference ref;

    private Button takeAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_information);

        init();

        doctorUID = bundle.getString("doctorUID");

//        TextView refDoctorUID = findViewById(R.id.doctorUID);
//        refDoctorUID.setText(doctorUID);

        if(doctorUID == null){
            Toast.makeText(DoctorInformation.this, "No doctor content to display", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            // get Doctor
            getDocWithUID(doctorUID);

        }






    }

    private void init(){
        bundle = getIntent().getExtras();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        activity = findViewById(R.id.activity);
        street = findViewById(R.id.street);
        streetNumber = findViewById(R.id.streetNumber);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);

        takeAppointment = findViewById(R.id.buttonTakeAppointment);
        takeAppointment.setOnClickListener(this);


        database = FirebaseDatabase.getInstance();


    }

    public void onClick(View v) {
        if(v.getId() == R.id.buttonTakeAppointment){
            Intent intent = new Intent(this, PatientCalendarActivity.class);

            intent.putExtra("doctorUID", doctorUID);

            startActivity(intent);

        }


    }
    private void getDocWithUID(String uid){


        ref = database.getReference().child("Doctor").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
                setData();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DoctorInformation.this, "@+id/database_error", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setData(){
        firstName.setText(doctor.getFirstName());
        lastName.setText(doctor.getLastName());
        activity.setText(doctor.getActivity());
        street.setText(doctor.getStreet());
        streetNumber.setText(doctor.getStreetNumber());
        city.setText(doctor.getCity());
        country.setText(doctor.getCountry());
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
