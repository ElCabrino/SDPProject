package ch.epfl.sweng.vanjel.appointment;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

import static ch.epfl.sweng.vanjel.firebase.FirebaseHelper.dataSnapshotChildToString;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class DoctorComingAppointments extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String uid;
    private DatabaseReference ref, patientRef;

    private RecyclerView recyclerView;
    private DoctorComingAppointmentsAdapter adapter;

    private ArrayList<Appointment> doctorAppointments;
    private HashMap<String, Patient> patientHashMap;

    private Date currentDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy");

    Boolean appointmentsReady = false;
    Boolean patientHashMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_coming_appointment);
        init();
        patientListener();
        getAppointments();

    }

    // set cardview, database reference
    public void init(){
        if (FirebaseAuthCustomBackend.getInstance().getCurrentUser()!= null) {
            uid = FirebaseAuthCustomBackend.getInstance().getCurrentUser().getUid();
        } //TODO null user exception
        ref = database.getReference("Requests");
        patientRef = database.getReference("Patient");
        // adapter
        recyclerView = findViewById(R.id.doctorComingAppointmentCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorAppointments = new ArrayList<>();
        patientHashMap = new HashMap<>();
        adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments, patientHashMap);
        recyclerView.setAdapter(adapter);
        currentDate = new Date();

    }

    public void getAppointments(){
        // for debbuging:
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
                appointmentsReady = true;
                if(patientHashMapReady) notifyAdapter();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addAppointment(DataSnapshot request) throws ParseException {
        // check if appointment is in the past

        if(uid.equals(request.child("doctor").getValue(String.class))){
            String day, hour, patientUid, doctorUid;
            int duration = Integer.valueOf(dataSnapshotChildToString(request, "duration"));
            day = request.child("date").getValue(String.class);
            doctorUid = request.child("doctor").getValue(String.class);
            hour = request.child("time").getValue(String.class);
            patientUid = request.child("patient").getValue(String.class);
            currentDate = dateFormat.parse(dateFormat.format(currentDate));
            int comparison = dateFormat.parse(day).compareTo(currentDate);
            // 0 is today, -1 is before, 1 is after
             if ((comparison > -1) && (duration != 0)){
                Appointment appointment = new Appointment(day, hour, duration, doctorUid, patientUid);
                doctorAppointments.add(appointment);
            }
        }

    }

    private class appointmentComparator implements Comparator<Appointment> {

        private SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
        DateFormat hourFormatter = new SimpleDateFormat("HH:mm");

        // compare depending date
        @Override
        public int compare(Appointment o1, Appointment o2) {
            try {
                Date o1Date = formatter.parse(o1.getDay());
                Date o2Date = formatter.parse(o2.getDay());

                int comparator = o1Date.compareTo(o2Date);

                if(comparator == 0) {
                    // we need to compare hour
                    Date o1Hour = hourFormatter.parse(o1.getHour());
                    Date o2Hour = hourFormatter.parse(o2.getHour());

                    return o1Hour.compareTo(o2Hour);
                }

                return comparator;


            } catch (ParseException e) {
                e.printStackTrace();
            }

            return -1;
        }
    }

    public void patientListener() {
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Patient myPatient = dataSnapshot1.getValue(Patient.class);
                    String key = dataSnapshot1.getKey();
                    patientHashMap.put(key, myPatient);
                }
                patientHashMapReady = true;
                if(appointmentsReady) notifyAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void notifyAdapter() {
        adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments, patientHashMap);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}