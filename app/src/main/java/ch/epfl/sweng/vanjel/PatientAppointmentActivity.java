package ch.epfl.sweng.vanjel;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PatientAppointmentActivity extends AppCompatActivity implements View.OnClickListener{

    //Appointment with the doctor of this ID
    String doctorUID;

    Boolean slotSelected = new Boolean(false);

    HashMap<Integer, Button> buttonsAppointment = new HashMap<Integer, Button>();
    HashMap<Integer, Boolean> buttonsState= new HashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);

        //get the doctor ID
        doctorUID = getIntent().getStringExtra("doctorUID");

        //buttons for the timetable
        getAllButton();
        addButtonListener();
        initButtonState();

        //validate appointment button
        findViewById(R.id.buttonAppointment).setOnClickListener(this);
    }



    //Fill the button hasmap
    void getAllButton(){
        addButton(R.id.button0800);
        addButton(R.id.button0830);
        addButton(R.id.button0900);
        addButton(R.id.button0930);
        addButton(R.id.button1000);
        addButton(R.id.button1030);
        addButton(R.id.button1100);
        addButton(R.id.button1130);
        addButton(R.id.button1200);
        addButton(R.id.button1230);
        addButton(R.id.button1300);
        addButton(R.id.button1330);
        addButton(R.id.button1400);
        addButton(R.id.button1430);
        addButton(R.id.button1500);
        addButton(R.id.button1530);
        addButton(R.id.button1600);
        addButton(R.id.button1630);
        addButton(R.id.button1700);
        addButton(R.id.button1730);
        addButton(R.id.button1800);
        addButton(R.id.button1830);
        addButton(R.id.button1900);
        addButton(R.id.button1930);
    }

    void addButton(int i) {
        buttonsAppointment.put(i, (Button)findViewById(i));
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
            Toast.makeText(this, "You've already picked a time slot", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonAppointment){
            Toast.makeText(this, doctorUID, Toast.LENGTH_LONG).show();
        } else {
            changeState(i);
        }
    }
}
