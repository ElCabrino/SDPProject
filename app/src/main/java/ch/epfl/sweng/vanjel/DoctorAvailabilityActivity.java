package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DoctorAvailabilityActivity extends AppCompatActivity {

    private static final String TAG = "DoctorAvailability";

    private int NUMBER_OF_SLOTS = TimeAvailability.getIdLength();

    private Button valid;

    private ToggleButton[] buttons;

    private Boolean[] slots;
//    private boolean[] oldSlots = new boolean[TimeAvailability.getIdLength()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_availability);

        initButtons();

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        loadAvailability();
    }

    private void initButtons() {

        buttons = new ToggleButton[NUMBER_OF_SLOTS];

        for (int i=0; i<NUMBER_OF_SLOTS; i++) {
            buttons[i] = findViewById(TimeAvailability.times[i]);
        }
        valid = findViewById(R.id.valid);
    }

    private void validate() {
        slots = new Boolean[NUMBER_OF_SLOTS];
        for (int i = 0; i < NUMBER_OF_SLOTS; i++) {
            slots[i] = buttons[i].isChecked();
        }

        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        List<Map<String, Object>> availabilities = storeAvailability();

        int i = 0;
        for (String d: days) {
            FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID() + "/Availability/"+d).updateChildren(availabilities.get(i++)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(DoctorAvailabilityActivity.this, "Doctor availability successfully updated.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DoctorAvailabilityActivity.this, "Failed to update Doctor availability.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    public String getUserFirebaseID() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            return "0N5Bg2yoxrgVzD9U5jWz1RuJLyj2";
        }
    }

    private List<Map<String, Object>> storeAvailability() {
        List<Map<String, Object>> newAvailability = new ArrayList<>();
        Map<String, Object> monday = getDayAvailability(TimeAvailability.MONDAY);
        Map<String, Object> tuesday = getDayAvailability(TimeAvailability.TUESDAY);
        Map<String, Object> wednesday = getDayAvailability(TimeAvailability.WEDNESDAY);
        Map<String, Object> thursday = getDayAvailability(TimeAvailability.THURSDAY);
        Map<String, Object> friday = getDayAvailability(TimeAvailability.FRIDAY);
        Map<String, Object> saturday = getDayAvailability(TimeAvailability.SATURDAY);
        newAvailability.add(monday);
        newAvailability.add(tuesday);
        newAvailability.add(wednesday);
        newAvailability.add(thursday);
        newAvailability.add(friday);
        newAvailability.add(saturday);
        return newAvailability;
    }

    public Map<String, Object> getDayAvailability(int start) {
        Map<String, Object> day = new HashMap<>();
/*        int isChain = 0;
        int minutes = 480;
        String t = "";
        for (int i=start;i<start+22;i++) {
            if(slots[i] == true && isChain == 0) {
                isChain = minutes;
            } else {
                t = t+buildAvailabilityString(isChain, minutes, t.isEmpty());
                isChain = 0;
            }
            minutes +=30;
        }*/
        day.put("availability", getStringFromSlots(start));
        return day;
    }

    private String getStringFromSlots(int start) {
        int isChain = 0;
        int minutes = 480;
        String t = "";
        for (int i=start;i<start+22;i++) {
            if(slots[i] == true && isChain == 0) {
                isChain = minutes;
            } else {
                t = t+buildAvailabilityString(isChain, minutes, t.isEmpty());
                isChain = 0;
            }
            minutes +=30;
        }
        return t;
    }

    private String buildAvailabilityString(int start, int end, boolean firstTime) {
        if (start != 0) {
            if (firstTime) {
                return minutesToTime(start)+"-"+minutesToTime(end);
            } else {
                return " / " + minutesToTime(start)+"-"+minutesToTime(end);
            }
        }
        return "";
    }

    private String minutesToTime(int total) {
        String time;
        String hour = Integer.toString(total/60);
        String minutes = Integer.toString(total%60);
        if (hour.length() < 2) {
            hour = "0"+hour;
        }
        if (minutes.length() < 2) {
            minutes = "0"+minutes;
        }
        time = hour+":"+minutes;
        return time;
    }

    private void loadAvailability() {
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Monday").addValueEventListener(createValueEventListener(TimeAvailability.MONDAY));
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Tuesday").addValueEventListener(createValueEventListener(TimeAvailability.TUESDAY));
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Wednesday").addValueEventListener(createValueEventListener(TimeAvailability.WEDNESDAY));
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Thursday").addValueEventListener(createValueEventListener(TimeAvailability.THURSDAY));
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Friday").addValueEventListener(createValueEventListener(TimeAvailability.FRIDAY));
        FirebaseDatabase.getInstance().getReference("Doctor").child(getUserFirebaseID()+"/Availability/Saturday").addValueEventListener(createValueEventListener(TimeAvailability.SATURDAY));
    }

    private ValueEventListener createValueEventListener(final int dayindex) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> tm = (Map<String, Object>) dataSnapshot.getValue();
                if (tm != null) {
                    setOldSlots(TimeAvailability.getDayAvailability(dayindex, tm.get("availability").toString()), dayindex);
                } else {
                    Log.d("ERROR", "tm is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
        return listener;
    }

    private void setOldSlots(boolean[] oldSlots, final int dayindex) {
        for (int i=dayindex;i<dayindex+22;i++) {
            buttons[i].setChecked(oldSlots[i]);
        }
    }
}
