package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientInfo extends AppCompatActivity {

    Button detailedInfoButton;
    Button saveButton;

    EditText priorConditionsReg;
    EditText surgeriesReg;
    EditText surgeriesYearReg;

    Button buttonConditions;
    Button buttonSurgeries;

    DatabaseReference databaseCondition;
    DatabaseReference databaseSurgery;

    ListView listViewConditions;
    ListView listViewSurgeries;

    List<Condition> conditionList;
    List<Surgery> surgeryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        databaseCondition = FirebaseDatabase.getInstance().getReference("Condition");
        databaseSurgery = FirebaseDatabase.getInstance().getReference("Surgery");

        saveButton = (Button) findViewById(R.id.buttonGenInfoPtReg);

        priorConditionsReg = (EditText) findViewById(R.id.ptPriorConditionsReg);
        surgeriesReg = (EditText) findViewById(R.id.ptSurgeryReg);
        surgeriesYearReg = (EditText) findViewById(R.id.ptSurgeryYearReg);

        buttonConditions = (Button) findViewById(R.id.buttonPriorConditions);
        buttonSurgeries = (Button) findViewById(R.id.buttonSurgery);

        listViewConditions = (ListView) findViewById(R.id.ptPriorConditionsList);
        listViewSurgeries = (ListView) findViewById(R.id.ptSurgeryList);

        conditionList = new ArrayList<>();
        surgeryList = new ArrayList<>();

        buttonConditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCondition();
            }
        });
        buttonSurgeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSurgery();
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        // add the database listeners
        databaseCondition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                conditionList.clear();
                for (DataSnapshot conditionSnapshot: dataSnapshot.getChildren()) {
                    Condition condition = conditionSnapshot.getValue(Condition.class);

                    conditionList.add(condition);

                }

                ConditionList adapter = new ConditionList(PatientInfo.this,conditionList);
                listViewConditions.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseSurgery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                surgeryList.clear();
                for (DataSnapshot surgerySnapshot: dataSnapshot.getChildren()) {
                    Surgery surgery = surgerySnapshot.getValue(Surgery.class);

                    surgeryList.add(surgery);

                }

                SurgeryList adapter = new SurgeryList(PatientInfo.this,surgeryList);
                listViewSurgeries.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




    private void addCondition() {
        String condition = priorConditionsReg.getText().toString().trim();
        if(!TextUtils.isEmpty(condition)) {
            String id = databaseCondition.push().getKey();
            Condition cond = new Condition(id,condition);
            databaseCondition.child(id).setValue(cond);
            Toast.makeText(this,"Condition added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the condition you want to add", Toast.LENGTH_LONG).show();
        }
    }


    private void addSurgery() {
        String surgery = surgeriesReg.getText().toString().trim();
        String year = surgeriesYearReg.getText().toString().trim();
        if(!TextUtils.isEmpty(surgery) && !TextUtils.isEmpty(year)) {

            String id = databaseSurgery.push().getKey();
            Surgery chir = new Surgery(id,surgery,year);


            databaseSurgery.child(id).setValue(chir);
            Toast.makeText(this,"Surgery added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the surgery and the year you want to add", Toast.LENGTH_LONG).show();
        }
    }





}
