package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorAppointmentsList extends AppCompatActivity{

    private DoctorAppointmentListAdapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference dbReferenceAppointments;
    private String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_list);
        this.uid = FirebaseAuthCustomBackend.getInstance().getCurrentUser().getUid();
        this.dbReferenceAppointments = FirebaseDatabaseCustomBackend.getInstance().getReference("Requests");
        initAdapter();
        getAppointments();
    }

    private void initAdapter() {
        recyclerView = findViewById(R.id.appointmentCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAppointmentListAdapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void refreshActivity(){
        Intent intent = new Intent(this, DoctorAppointmentsList.class);
        startActivity(intent);
        finish();
    }

    private void getAppointments() {
        ValueEventListener valueListener = getAppointmentValueEventListener();

        dbReferenceAppointments.addValueEventListener(valueListener);
    }

    public ValueEventListener getAppointmentValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String day, hour, patientUid, doctorUid, appointmentID;
                for (DataSnapshot dayRequest: dataSnapshot.getChildren()){
                    appointmentID = dayRequest.getKey();
                    day = dayRequest.child("date").getValue().toString();
                    doctorUid = dayRequest.child("doctor").getValue().toString();
                    hour = dayRequest.child("time").getValue().toString();
                    patientUid = dayRequest.child("patient").getValue().toString();
                    fillAppointmentsList(day, hour, patientUid, doctorUid, appointmentID);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private void fillAppointmentsList(String day, String hour, String patientUid, String doctorUid, String appointmentID) {
        if (this.uid.equals(doctorUid)){
            Appointment appointment = new Appointment(day, hour, doctorUid, patientUid, appointmentID);
            adapter.appointmentsList.add(appointment);
            adapter = new DoctorAppointmentListAdapter(this, adapter.appointmentsList);
            recyclerView.setAdapter(adapter);
        }

    }
}
