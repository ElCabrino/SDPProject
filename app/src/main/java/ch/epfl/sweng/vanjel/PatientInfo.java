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

    Button saveButton;

    EditText priorConditionsReg;
    EditText surgeriesReg;
    EditText surgeriesYearReg;
    EditText allergyReg;
    EditText drugReactionDrugReg;
    EditText drugReactionReactionReg;
    EditText drugRegimenDrugReg;
    EditText drugRegimenDosageReg;
    EditText drugRegimenTimesReg;
    EditText substancesReg;

    Button buttonConditions;
    Button buttonSurgeries;
    Button buttonAllergies;
    Button buttonDrugReactions;
    Button buttonDrug;
    Button buttonSubstance;

    DatabaseReference databaseCondition;
    DatabaseReference databaseSurgery;
    DatabaseReference databaseAllergy;
    DatabaseReference databaseDrugReaction;
    DatabaseReference databaseDrug;
    DatabaseReference databaseSubstance;

    ListView listViewConditions;
    ListView listViewSurgeries;
    ListView listViewAllergies;
    ListView listViewDrugReactions;
    ListView listViewDrugs;
    ListView listViewSubstances;

    List<Condition> conditionList;
    List<Surgery> surgeryList;
    List<Allergy> allergyList;
    List<DrugReaction> drugReactionList;
    List<Drug> drugList;
    List<Substance> substanceList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        databaseCondition = FirebaseDatabase.getInstance().getReference("Condition");
        databaseSurgery = FirebaseDatabase.getInstance().getReference("Surgery");
        databaseAllergy = FirebaseDatabase.getInstance().getReference("Allergy");
        databaseDrugReaction = FirebaseDatabase.getInstance().getReference("DrugReaction");
        databaseDrug = FirebaseDatabase.getInstance().getReference("Drug");
        databaseSubstance = FirebaseDatabase.getInstance().getReference("Substance");

        saveButton = (Button) findViewById(R.id.buttonGenInfoPtReg);

        priorConditionsReg = (EditText) findViewById(R.id.ptPriorConditionsReg);
        surgeriesReg = (EditText) findViewById(R.id.ptSurgeryReg);
        surgeriesYearReg = (EditText) findViewById(R.id.ptSurgeryYearReg);
        allergyReg = (EditText) findViewById(R.id.ptAllergyReg);
        drugReactionDrugReg = (EditText) findViewById(R.id.ptDrugReactionDrugReg);
        drugReactionReactionReg = (EditText) findViewById(R.id.ptDrugReactionReactionReg);
        drugRegimenDrugReg = (EditText) findViewById(R.id.ptDrugRegimenDrugReg);
        drugRegimenDosageReg = (EditText) findViewById(R.id.ptDrugRegimenDosageReg);
        drugRegimenTimesReg = (EditText) findViewById(R.id.ptDrugRegimenTimesReg);
        substancesReg = (EditText) findViewById(R.id.ptSubstanceReg);

        buttonConditions = (Button) findViewById(R.id.buttonPriorConditions);
        buttonSurgeries = (Button) findViewById(R.id.buttonSurgery);
        buttonAllergies = (Button) findViewById(R.id.buttonAllergy);
        buttonDrugReactions = (Button) findViewById(R.id.buttonDrugReaction);
        buttonDrug = (Button) findViewById(R.id.buttonDrugRegimen);
        buttonSubstance = (Button) findViewById(R.id.buttonSubstance);


        listViewConditions = (ListView) findViewById(R.id.ptPriorConditionsList);
        listViewSurgeries = (ListView) findViewById(R.id.ptSurgeryList);
        listViewAllergies = (ListView) findViewById(R.id.ptAllergyList);
        listViewDrugReactions = (ListView) findViewById(R.id.ptDrugReactionList);
        listViewDrugs = (ListView) findViewById(R.id.ptDrugRegimenList);
        listViewSubstances = (ListView) findViewById(R.id.ptSubstanceList);

        conditionList = new ArrayList<>();
        surgeryList = new ArrayList<>();
        allergyList = new ArrayList<>();
        drugReactionList = new ArrayList<>();
        drugList = new ArrayList<>();
        substanceList = new ArrayList<>();


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
        buttonAllergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAllergy();
            }
        });
        buttonDrugReactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrugReaction();
            }
        });
        buttonDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDrug();
            }
        });
        buttonSubstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSubstance();
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

        databaseAllergy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allergyList.clear();
                for (DataSnapshot allergySnapshot: dataSnapshot.getChildren()) {
                    Allergy allergy = allergySnapshot.getValue(Allergy.class);

                    allergyList.add(allergy);

                }

                AllergyList adapter = new AllergyList(PatientInfo.this,allergyList);
                listViewAllergies.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseDrugReaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drugReactionList.clear();
                for (DataSnapshot drugReactionSnapshot: dataSnapshot.getChildren()) {
                    DrugReaction drugReaction = drugReactionSnapshot.getValue(DrugReaction.class);

                    drugReactionList.add(drugReaction);

                }

                DrugReactionList adapter = new DrugReactionList(PatientInfo.this,drugReactionList);
                listViewDrugReactions.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseDrug.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drugList.clear();
                for (DataSnapshot drugSnapshot: dataSnapshot.getChildren()) {
                    Drug drug = drugSnapshot.getValue(Drug.class);

                    drugList.add(drug);

                }

                DrugList adapter = new DrugList(PatientInfo.this,drugList);
                listViewDrugs.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseSubstance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                substanceList.clear();
                for (DataSnapshot substanceSnapshot: dataSnapshot.getChildren()) {
                    Substance sub = substanceSnapshot.getValue(Substance.class);

                    substanceList.add(sub);

                }

                SubstanceList adapter = new SubstanceList(PatientInfo.this,substanceList);
                listViewSubstances.setAdapter(adapter);

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

    private void addAllergy() {
        String allergy = allergyReg.getText().toString().trim();
        if(!TextUtils.isEmpty(allergy)) {

            String id = databaseAllergy.push().getKey();
            Allergy al = new Allergy(id,allergy);


            databaseAllergy.child(id).setValue(al);
            Toast.makeText(this,"Allergy added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the allergy you want to add", Toast.LENGTH_LONG).show();
        }
    }

    private void addDrugReaction() {
        String drug = drugReactionDrugReg.getText().toString().trim();
        String reaction = drugReactionReactionReg.getText().toString().trim();
        if(!TextUtils.isEmpty(drug) && !TextUtils.isEmpty(reaction)) {

            String id = databaseDrugReaction.push().getKey();
            DrugReaction dr = new DrugReaction(id,drug,reaction);

            databaseDrugReaction.child(id).setValue(dr);
            Toast.makeText(this,"Drug reaction added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the drug and the reaction you want to add", Toast.LENGTH_LONG).show();
        }
    }

    private void addDrug() {
        String drug = drugRegimenDrugReg.getText().toString().trim();
        String dosage = drugRegimenDosageReg.getText().toString().trim();
        String times = drugRegimenTimesReg.getText().toString().trim();

        if(!TextUtils.isEmpty(drug) && !TextUtils.isEmpty(dosage) && !TextUtils.isEmpty(times)) {

            String id = databaseDrug.push().getKey();
            Drug dr = new Drug(id,drug,dosage, times);

            databaseDrug.child(id).setValue(dr);
            Toast.makeText(this,"Drug added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the drug, the dosage and the frequency you want to add", Toast.LENGTH_LONG).show();
        }
    }

    private void addSubstance() {
        String substance = substancesReg.getText().toString().trim();

        if(!TextUtils.isEmpty(substance)) {

            String id = databaseSubstance.push().getKey();
            Substance sub = new Substance(id,substance);

            databaseSubstance.child(id).setValue(sub);
            Toast.makeText(this,"Substance added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the substance you want to add", Toast.LENGTH_LONG).show();
        }
    }





}
