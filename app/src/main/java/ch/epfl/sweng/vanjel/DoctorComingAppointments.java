package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.text.SimpleDateFormat;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class DoctorComingAppointments extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String uid;
    private DatabaseReference ref;

    private RecyclerView recyclerView;
    private DoctorComingAppointmentsAdapter adapter;

    private ArrayList<Appointment> doctorAppointments;

    private Date currentDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_coming_appointment);
        init();
        getAppointments();

    }

    // set cardview, database reference
    public void init(){
        uid = "W7ReyyyOwAQKaganjsMQuHRb0Aj2";
//        uid = FirebaseAuthCustomBackend.getInstance().getCurrentUser().getUid();
        ref = database.getReference().child("Requests");
        // adapter
        recyclerView = findViewById(R.id.doctorComingAppointmentCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorAppointments = new ArrayList<>();
        adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments);
        recyclerView.setAdapter(adapter);
        currentDate = new Date();

    }

    public void getAppointments(){
//        Appointment appointment = new Appointment("oklm", "12:00", 50, "lol", "oklm");
//        doctorAppointments.add(appointment);
//        adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorAppointments = new ArrayList<>(); // reset list
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    try {
                        addAppointment(dataSnapshot1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                Collections.sort(doctorAppointments, new appointmentComparator());
                adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAppointment(DataSnapshot request) throws ParseException {
        // check if appointment is in the past

        if(request.child("doctor").getValue().equals(uid)){
            String day, hour, patientUid, doctorUid, appointmentID;
            int duration = Integer.valueOf(request.child("duration").getValue().toString());
            appointmentID = request.getKey();
            day = request.child("date").getValue().toString();
            doctorUid = request.child("doctor").getValue().toString();
            hour = request.child("time").getValue().toString();
            patientUid = request.child("patient").getValue().toString();
            currentDate = dateFormat.parse(dateFormat.format(currentDate));
            int comparaison = dateFormat.parse(day).compareTo(currentDate);
            // 0 is today, -1 is before, 1 is after
             if(comparaison != -1 && duration != 0){
                Appointment appointment = new Appointment(day, hour, duration, doctorUid, patientUid);
                doctorAppointments.add(appointment);
            }
        }

    }

    private class appointmentComparator implements Comparator<Appointment> {

        private SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");


        // compare depending date
        @Override
        public int compare(Appointment o1, Appointment o2) {
            try {
                Date o1Date = formatter.parse(o1.getDay());
                Date o2Date = formatter.parse(o2.getDay());
                return o1Date.compareTo(o2Date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return -1;
        }
    }
}