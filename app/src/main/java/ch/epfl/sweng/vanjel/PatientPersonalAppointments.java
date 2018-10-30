package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientPersonalAppointments extends AppCompatActivity {

    DatabaseReference dbAp;
    DatabaseReference dbDoc;

    ListView listViewAp;
    String id = "Gaq9alb1yohthmwm1A9GrkVrBgp2";

    List<PtPersonalAppointment> apList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_appointments);

        // to be corrected
        //dbAp = FirebaseDatabase.getInstance().getReference("Patient/I3h9NVPXwmb0Ab2auVnaMSgjaLY2/Appointments");

        dbAp = FirebaseDatabase.getInstance().getReference("Requests");
        dbDoc = FirebaseDatabase.getInstance().getReference("Doctor");

        listViewAp = (ListView) findViewById(R.id.ptPersonalAppointmentsListView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        dbAp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apList.clear();
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    //PtPersonalAppointment ap = ds.getValue(PtPersonalAppointment.class);
                    String date = dateSnapshot.getKey();
                        for (DataSnapshot ds : dateSnapshot.getChildren()) {
                            if (ds.child("patient").getValue(String.class).equals(id)) {
                                String a = ds.child("patient").getValue(String.class);
                                PtPersonalAppointment ap = new PtPersonalAppointment(date, a, "b", "c");
                                apList.add(ap);
                            }
                        }
                }


                PtPersonalAppointmentsList adapter = new PtPersonalAppointmentsList(PatientPersonalAppointments.this,apList);
                listViewAp.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
