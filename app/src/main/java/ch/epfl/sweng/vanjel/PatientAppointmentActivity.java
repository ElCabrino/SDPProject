package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PatientAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    Toast mToast;

    //Appointment with the doctor of this ID
    String doctorUID;

    String selectedDate;

    Boolean slotSelected = new Boolean(false);
    boolean[] slotsAvailability;

    HashMap<Integer, Button> buttonsAppointment = new HashMap<Integer, Button>();
    HashMap<Integer, Boolean> buttonsState= new HashMap<Integer, Boolean>();
    HashMap<Integer, Integer> slotState = new HashMap<Integer, Integer>();

    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);

        //get the doctor ID
        doctorUID = getIntent().getStringExtra("doctorUID");
        selectedDate = getIntent().getStringExtra("date");

        //buttons for the timetable
        getAllButton();
        addButtonListener();
        initButtonState();

        getDoctorAvailability();

        //validate appointment button
        findViewById(R.id.buttonAppointment).setOnClickListener(this);
    }

    //Fill the button hasmap
    void getAllButton(){
        addButton(R.id.button0800, 0);
        addButton(R.id.button0830, 1);
        addButton(R.id.button0900, 2);
        addButton(R.id.button0930, 3);
        addButton(R.id.button1000, 4);
        addButton(R.id.button1030, 5);
        addButton(R.id.button1100, 6);
        addButton(R.id.button1130, 7);
        addButton(R.id.button1200, 8);
        addButton(R.id.button1230, 9);
        addButton(R.id.button1300, 10);
        addButton(R.id.button1330, 11);
        addButton(R.id.button1400, 12);
        addButton(R.id.button1430, 13);
        addButton(R.id.button1500, 14);
        addButton(R.id.button1530, 15);
        addButton(R.id.button1600, 16);
        addButton(R.id.button1630, 17);
        addButton(R.id.button1700, 18);
        addButton(R.id.button1730, 19);
        addButton(R.id.button1800, 20);
        addButton(R.id.button1830, 21);
    }

    void addButton(int i, int slot_i) {
        buttonsAppointment.put(i, (Button)findViewById(i));
        slotState.put(slot_i, i);
    }

    //Fill the state hashmap with a loop
    void initButtonState(){
        Iterator iterator = buttonsAppointment.entrySet().iterator();
        for (Integer key: buttonsAppointment.keySet()){
            buttonsState.put(key, false);
        }
    }

    //Add listener on all buttons of the hashmap
    void addButtonListener(){
        Iterator iterator = buttonsAppointment.entrySet().iterator();
        for (Button button: buttonsAppointment.values()){
            button.setOnClickListener(this);
        }
    }

    //Change state of button
    void changeState(int i){
        //case where no time slot is selected
        if (!(buttonsState.get(i)) && !slotSelected) {
            findViewById(i).setBackgroundColor(0xFF303F9F);
            buttonsState.put(i, true);
            slotSelected = true;
        }
        //case deselect a time slot
        else if ((buttonsState.get(i)) && slotSelected){
            findViewById(i).setBackgroundColor(0xFF3F51B5);
            buttonsState.put(i, false);
            slotSelected = false;
        }
        //case time slot already selected
        else {
            mToast.makeText(this, "You've already picked a time slot", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonAppointment){
            storeAppointment();
        } else {
            changeState(i);
        }
    }

    // Store appointment request in Firebase
    private void storeAppointment() {
        DatabaseReference ref = database.getReference("Requests");
        for (Integer i: buttonsAppointment.keySet()) {
            if (buttonsState.get(i) == true) {
                String key = ref.push().getKey();
                Map<String, Object> request = generateAppointmentValues(buttonsAppointment.get(i).getContentDescription().toString(), doctorUID, auth.getCurrentUser().getUid());
                ref.child(parseSelectedDate()+"/"+key).updateChildren(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mToast.makeText(PatientAppointmentActivity.this, "Appointment successfully requested.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mToast.makeText(PatientAppointmentActivity.this, "Failed request appointment.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    // Generate date, time and id for appointment
    private Map<String, Object> generateAppointmentValues(String time, String docId, String patientId) {
        Map<String, Object> res = new HashMap<>();
        res.put("time", time);
        res.put("doctor", docId);
        res.put("patient", patientId);
        res.put("duration", "0");
        return res;
    }

    // Return parsed selectedDate by removing time and GMT+01:00
    private String parseSelectedDate() {
        String res = "";
        String[] subStrings = selectedDate.split("[0-9]{2}:[0-9]{2}:[0-9]{2}.+[0-9]{2}:[0-9]{2}");
        for (String s : subStrings) {
            res+=s;
        }
        return res.replaceAll("\\s\\s", " ");
    }

    private void getDoctorAvailability() {
        slotsAvailability = new boolean[TimeAvailability.getIdLength()/6];
        DatabaseReference ref = database.getReference("Doctor/"+doctorUID+"/Availability");
        Log.d("TESTRUNNING", "Doctor/"+doctorUID+"/Availability");
        String weekday = parseSelectedDate().substring(0,Math.min(parseSelectedDate().length(), 3));
        switch (weekday) {
            case "Mon":
                weekday = "Monday";
                break;
            case "Tue":
                weekday = "Tuesday";
                break;
            case "Wed":
                weekday = "Wednesday";
                break;
            case "Thu":
                weekday = "Thursday";
                break;
            case "Fri":
                weekday = "Friday";
                break;
            case "Sat":
                weekday = "Saturday";
                break;
        }
        ref.child(weekday).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<HashMap<String, String>> genericType = new GenericTypeIndicator<HashMap<String, String>>() {};
                        HashMap<String, String> av = dataSnapshot.getValue(genericType);
                        // If Doctor has not set availability, we consider he is available all time.
                        if (av != null) {
                            slotsAvailability = TimeAvailability.parseTimeStringToSlots(av.get("availability"));
                            setDoctorAvailability();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    private void setDoctorAvailability() {
        for (int i=0; i<slotsAvailability.length;i++)
            if (slotsAvailability[i] == false) {
                findViewById(slotState.get(i)).setBackgroundColor(0xFFFFFFFF);
                findViewById(slotState.get(i)).setEnabled(false);
                buttonsState.put(slotState.get(i), false);
            }
        }
}