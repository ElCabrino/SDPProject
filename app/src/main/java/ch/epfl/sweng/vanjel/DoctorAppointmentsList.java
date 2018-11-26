package ch.epfl.sweng.vanjel;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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


    private void getAppointments() {
        ValueEventListener valueListener = getAppointmentValueEventListener();

        dbReferenceAppointments.addValueEventListener(valueListener);
    }

    public ValueEventListener getAppointmentValueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.appointmentsList = new ArrayList<>();
                for (DataSnapshot request : dataSnapshot.getChildren()) {
                    refreshAppointmentsList(request);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
    }

    private void refreshAppointmentsList(DataSnapshot request) {
        String day, hour, patientUid, doctorUid, appointmentID, duration;
        appointmentID = request.getKey();
        day = request.child("date").getValue(String.class);
        doctorUid = request.child("doctor").getValue(String.class);
        hour = request.child("time").getValue(String.class);
        patientUid = request.child("patient").getValue(String.class);
        duration = request.child("duration").getValue(String.class);
        if ((this.uid.equals(doctorUid))&&(Integer.valueOf(duration) == 0)){ //refresh with new element
            Appointment appointment = new Appointment(day, hour, doctorUid, patientUid, appointmentID);
            adapter.appointmentsList.add(appointment);
            adapter = new DoctorAppointmentListAdapter(this, adapter.appointmentsList);
            recyclerView.setAdapter(adapter);
        } else { //only refresh the view
            adapter = new DoctorAppointmentListAdapter(this, adapter.appointmentsList);
            recyclerView.setAdapter(adapter);
        }

    }
}
