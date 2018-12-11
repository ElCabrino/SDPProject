package ch.epfl.sweng.vanjel.doctorAvailability;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseHelper;

/**
 * @author Luca Joss
 * @reviewer Vincent Cabrini
 */
public class DoctorAvailabilityActivity extends AppCompatActivity {

    private int NUMBER_OF_SLOTS = TimeAvailability.getIdLength();

    private Button valid;

    private ToggleButton[] buttons;

    private Boolean[] slots;

    final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

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

        if (auth.getCurrentUser()!= null) {
            loadAvailability(auth.getCurrentUser().getUid());
        } //TODO user not logged in exception
    }

    private void initButtons() {

        buttons = new ToggleButton[NUMBER_OF_SLOTS];

        for (int i=0; i<NUMBER_OF_SLOTS; i++) {
            buttons[i] = findViewById(TimeAvailability.times[i]);
        }
        valid = findViewById(R.id.valid);
    }

    private void validate() {
        if (auth.getCurrentUser() == null) {
            return;
        } //TODO user not logged in exception

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
            String s = auth.getCurrentUser().getUid() + "/Availability/"+d;
            database.getReference("Doctor").child(s).updateChildren(availabilities.get(i++)).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        String res = getStringFromSlots(start);
        if (res.equals("")) {
            day.put("availability", "-");
        } else {
            day.put("availability", res);
        }
        return day;
    }

    private String getStringFromSlots(int start) {
        int isChain = 0;
        int minutes = 480; //480 minutes corresponds to 8:00
        String t = "";
        for (int i=start;i<start+22;i++) {
            if(slots[i] && isChain == 0) {
                isChain = minutes;
            } else {
                t = t.concat(buildAvailabilityString(isChain, minutes, t.isEmpty()));
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

    private void loadAvailability(String uid) {
        database.getReference("Doctor").child(uid+"/Availability/Monday").addValueEventListener(createValueEventListener(TimeAvailability.MONDAY));
        database.getReference("Doctor").child(uid+"/Availability/Tuesday").addValueEventListener(createValueEventListener(TimeAvailability.TUESDAY));
        database.getReference("Doctor").child(uid+"/Availability/Wednesday").addValueEventListener(createValueEventListener(TimeAvailability.WEDNESDAY));
        database.getReference("Doctor").child(uid+"/Availability/Thursday").addValueEventListener(createValueEventListener(TimeAvailability.THURSDAY));
        database.getReference("Doctor").child(uid+"/Availability/Friday").addValueEventListener(createValueEventListener(TimeAvailability.FRIDAY));
        database.getReference("Doctor").child(uid+"/Availability/Saturday").addValueEventListener(createValueEventListener(TimeAvailability.SATURDAY));
    }

    private ValueEventListener createValueEventListener(final int dayindex) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                @SuppressWarnings("unchecked") //TODO pas sur si on peut faire mieux
                Map<String, Object> tm = (Map<String, Object>) dataSnapshot.getValue();
                if (tm != null) {
                    setOldSlots(TimeAvailability.getAvailability(dayindex, FirebaseHelper.dataSnapshotChildToString(dataSnapshot, "availability")), dayindex);
                } else {
                    Log.d("ERROR", "tm is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
    }

    private void setOldSlots(boolean[] oldSlots, final int dayindex) {
        for (int i=dayindex;i<dayindex+22;i++) {
            buttons[i].setChecked(oldSlots[i]);
        }
    }
}
