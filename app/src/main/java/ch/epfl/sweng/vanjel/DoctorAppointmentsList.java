package ch.epfl.sweng.vanjel;

import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorAppointmentsList extends AppCompatActivity {

    private DoctorAppointmentListAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Appointment> appointmentsList;
    private DatabaseReference dbReferenceAppointments;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_list);
        this.appointmentsList = new ArrayList<>();
        this.uid = FirebaseAuthCustomBackend.getInstance().getCurrentUser().getUid();
        this.dbReferenceAppointments = FirebaseDatabaseCustomBackend.getInstance().getReference().child("Requests");
        initAdapter();
        getAppointments();
    }

    private void initAdapter() {
        recyclerView = findViewById(R.id.appointmentCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAppointmentListAdapter(this, this.appointmentsList);
        recyclerView.setAdapter(adapter);
    }

    private void getAppointments() {
        ValueEventListener valueListener = getAppointmentValueEventListener();

        dbReferenceAppointments.addValueEventListener(valueListener);
    }

    public ValueEventListener getAppointmentValueEventListener() {
        Log.d("TESTRUNNING", "in eve list");
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String day, hour, patientUid, doctorUid;
                Log.d("TESTRUNNING", "in datachange: " +  dataSnapshot.toString());
                for (DataSnapshot dayRequest: dataSnapshot.getChildren()){
                    Log.d("TESTRUNNING", "in for loop");
                    day = dayRequest.child("date").getValue().toString();
                    doctorUid = dayRequest.child("doctor").getValue().toString();
                    hour = dayRequest.child("time").getValue().toString();
                    patientUid = dayRequest.child("patient").getValue().toString();
                    fillAppointmentsList(day, hour, patientUid, doctorUid);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private void fillAppointmentsList(String day, String hour, String patientUid, String doctorUid) {
        if (this.uid.equals(doctorUid)){
            Appointment appointment = new Appointment(day, hour, doctorUid, patientUid);
            appointmentsList.add(appointment);
            adapter = new DoctorAppointmentListAdapter(this, appointmentsList);
            recyclerView.setAdapter(adapter);
        }

    }
}
