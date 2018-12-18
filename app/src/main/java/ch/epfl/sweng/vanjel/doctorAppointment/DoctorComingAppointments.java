package ch.epfl.sweng.vanjel.doctorAppointment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import ch.epfl.sweng.vanjel.LayoutHelper;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.appointment.Appointment;
import ch.epfl.sweng.vanjel.appointment.AppointmentComparator;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.models.Patient;


/**
 * @author Aslam CADER
 * @reviewer Vincent CABRINI
 */
public class DoctorComingAppointments extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String uid;
    private DatabaseReference ref, patientRef;

    private RecyclerView recyclerView;
    private DoctorComingAppointmentsAdapter adapter;

    private ArrayList<Appointment> doctorAppointments;
    private HashMap<String, Patient> patientHashMap;

    private Date currentDate;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd yyyy");

    private Boolean appointmentsReady = false;
    private Boolean patientHashMapReady = false;

    private TextView noAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_coming_appointment);
        init();
        patientListener();
        getAppointments();

    }

    // set cardview, database reference
    private void init(){
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
        noAppointments = findViewById(R.id.docNoAppointements);
    }

    private void getAppointments(){
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
                Collections.sort(doctorAppointments, new AppointmentComparator());
                appointmentsReady = true;
                if(patientHashMapReady) notifyAdapter();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addAppointment(DataSnapshot request) throws ParseException {
        // check if appointment is in the past

        if(uid.equals(request.child("doctor").getValue(String.class))){
            String durationText = request.child("duration").getValue(String.class);
            if (durationText != null) {
                appendListAppointment(durationText, request);
            } else {
                Toast.makeText(this, "An error occured when adding the appointment", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void appendListAppointment(String durationText, DataSnapshot request) throws ParseException {
        String day, hour, patientUid, doctorUid;
        int duration = Integer.valueOf(durationText);
        //int duration = Integer.valueOf(FirebaseHelper.dataSnapshotChildToString(request, "duration"));
        day = request.child("date").getValue(String.class);
        doctorUid = request.child("doctor").getValue(String.class);
        hour = request.child("time").getValue(String.class);
        patientUid = request.child("patient").getValue(String.class);
        currentDate = dateFormat.parse(dateFormat.format(currentDate));
        int comparison = dateFormat.parse(day).compareTo(currentDate);
        // 0 is today, -1 is before, 1 is after
        if ((comparison > -1) && (duration != 0)) {
            Appointment appointment = new Appointment(day, hour, duration, doctorUid, patientUid);
            doctorAppointments.add(appointment);
        }
    }


    private void patientListener() {

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

    private void notifyAdapter() {
        adapter = new DoctorComingAppointmentsAdapter(DoctorComingAppointments.this, doctorAppointments, patientHashMap);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        LayoutHelper.adaptLayoutIfNoData(doctorAppointments.isEmpty(),noAppointments);
    }
}