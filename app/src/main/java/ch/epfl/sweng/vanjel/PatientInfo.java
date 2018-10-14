package ch.epfl.sweng.vanjel;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientInfo extends AppCompatActivity implements View.OnClickListener{

    PatientInfoDatabaseService patientInfoDatabaseService;

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
    EditText smokingReg;
    EditText drinkingReg;
    EditText exerciseReg;

    Button buttonConditions;
    Button buttonSurgeries;
    Button buttonAllergies;
    Button buttonDrugReactions;
    Button buttonDrug;
    Button buttonSubstance;
    Button buttonSmoking;
    Button buttonDrinking;
    Button buttonExercise;

    DatabaseReference databaseCondition;
    DatabaseReference databaseSurgery;
    DatabaseReference databaseAllergy;
    DatabaseReference databaseDrugReaction;
    DatabaseReference databaseDrug;
    DatabaseReference databaseSubstance;
    DatabaseReference databaseSmoking;
    DatabaseReference databaseDrinking;
    DatabaseReference databaseExercise;

    ListView listViewConditions;
    ListView listViewSurgeries;
    ListView listViewAllergies;
    ListView listViewDrugReactions;
    ListView listViewDrugs;
    ListView listViewSubstances;
    TextView textViewSmoking;
    TextView textViewDrinking;
    TextView textViewExercise;

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

        patientInfoDatabaseService =
                new PatientInfoDatabaseService(FirebaseAuth.getInstance().getUid(), this);

        saveButton = (Button) findViewById(R.id.buttonGenInfoPtReg);

        getFirebaseInstances();

        getAllEditText();

        getAllButtons();

        initializeButtonsListeners();

        getAllPatientInfoFields();

        initializeLists();
    }

    private void initializeButtonsListeners() {
        buttonConditions.setOnClickListener(this);
        buttonSurgeries.setOnClickListener(this);
        buttonAllergies.setOnClickListener(this);
        buttonDrugReactions.setOnClickListener(this);
        buttonDrug.setOnClickListener(this);
        buttonSubstance.setOnClickListener(this);
        buttonSmoking.setOnClickListener(this);
        buttonDrinking.setOnClickListener(this);
        buttonExercise.setOnClickListener(this);
    }

    private void initializeLists() {
        conditionList = new ArrayList<>();
        surgeryList = new ArrayList<>();
        allergyList = new ArrayList<>();
        drugReactionList = new ArrayList<>();
        drugList = new ArrayList<>();
        substanceList = new ArrayList<>();
    }

    private void getFirebaseInstances() {
        databaseCondition = FirebaseDatabase.getInstance().getReference("Patient").child("ABLlrLukjAaPzaf5GA03takkw5k2").child("Condition");
        databaseSurgery = FirebaseDatabase.getInstance().getReference("Surgery");
        databaseAllergy = FirebaseDatabase.getInstance().getReference("Allergy");
        databaseDrugReaction = FirebaseDatabase.getInstance().getReference("DrugReaction");
        databaseDrug = FirebaseDatabase.getInstance().getReference("Drug");
        databaseSubstance = FirebaseDatabase.getInstance().getReference("Substance");
        databaseSmoking = FirebaseDatabase.getInstance().getReference("Smoking");
        databaseDrinking = FirebaseDatabase.getInstance().getReference("Drinking");
        databaseExercise = FirebaseDatabase.getInstance().getReference("Exercise");
    }

    private void getAllPatientInfoFields() {
        listViewConditions = (ListView) findViewById(R.id.ptPriorConditionsList);
        listViewSurgeries = (ListView) findViewById(R.id.ptSurgeryList);
        listViewAllergies = (ListView) findViewById(R.id.ptAllergyList);
        listViewDrugReactions = (ListView) findViewById(R.id.ptDrugReactionList);
        listViewDrugs = (ListView) findViewById(R.id.ptDrugRegimenList);
        listViewSubstances = (ListView) findViewById(R.id.ptSubstanceList);
        textViewSmoking = (TextView) findViewById(R.id.ptSmokingValue);
        textViewDrinking = (TextView) findViewById(R.id.ptDrinkingValue);
        textViewExercise = (TextView) findViewById(R.id.ptExerciseValue);
    }

    private void getAllButtons() {
        buttonConditions = findViewById(R.id.buttonPriorConditions);
        buttonSurgeries = findViewById(R.id.buttonSurgery);
        buttonAllergies = findViewById(R.id.buttonAllergy);
        buttonDrugReactions = findViewById(R.id.buttonDrugReaction);
        buttonDrug = findViewById(R.id.buttonDrugRegimen);
        buttonSubstance = findViewById(R.id.buttonSubstance);
        buttonSmoking = findViewById(R.id.buttonSmoking);
        buttonDrinking = findViewById(R.id.buttonDrinking);
        buttonExercise = findViewById(R.id.buttonExercise);
    }

    private void getAllEditText() {
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
        smokingReg = (EditText) findViewById(R.id.ptSmokingReg);
        drinkingReg = (EditText) findViewById(R.id.ptDrinkingReg);
        exerciseReg = (EditText) findViewById(R.id.ptExerciseReg);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // add the database listeners
        patientInfoDatabaseService.addConditionListener(conditionList, listViewConditions);

        /*databaseCondition.addValueEventListener(new ValueEventListener() {
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
        });*/

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

        databaseSmoking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot smokingSnapshot: dataSnapshot.getChildren()) {
                    String s = smokingSnapshot.getValue(Smoking.class).getAmount();
                    textViewSmoking.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseDrinking.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot drinkingSnapshot: dataSnapshot.getChildren()) {
                    String s = drinkingSnapshot.getValue(Drinking.class).getAmount();
                    textViewDrinking.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseExercise.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot exerciseSnapshot: dataSnapshot.getChildren()) {
                    String s = exerciseSnapshot.getValue(Exercise.class).getAmount();
                    textViewExercise.setText(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void addSmoking() {
        String amount = smokingReg.getText().toString().trim();

        if(!TextUtils.isEmpty(amount)) {

            String id = databaseSmoking.push().getKey();
            Smoking cigs = new Smoking(id,amount);

            databaseSmoking.child(id).setValue(cigs);
            Toast.makeText(this,"Smoking added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the average amount of cigarettes you smoke per day", Toast.LENGTH_LONG).show();
        }
    }

    private void addDrinking() {
        String amount = drinkingReg.getText().toString().trim();

        if(!TextUtils.isEmpty(amount)) {

            String id = databaseDrinking.push().getKey();
            Drinking drink = new Drinking(id,amount);

            databaseDrinking.child(id).setValue(drink);
            Toast.makeText(this,"Drinking added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the average amount of alcohol you consume per day", Toast.LENGTH_LONG).show();
        }
    }

    private void addExercise() {
        String amount = exerciseReg.getText().toString().trim();

        if(!TextUtils.isEmpty(amount)) {

            String id = databaseExercise.push().getKey();
            Exercise ex = new Exercise(id,amount);

            databaseExercise.child(id).setValue(ex);
            Toast.makeText(this,"Exercise added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this,"Please enter the average hours of exercise you do per week", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonPriorConditions){
            patientInfoDatabaseService.
                    addConditionToDatabase(priorConditionsReg.getText().toString().trim());
        } else if (i == R.id.buttonSurgery) {
            patientInfoDatabaseService.addSurgery(surgeriesReg.getText().toString().trim(),
                    surgeriesYearReg.getText().toString().trim());
        } else if (i == R.id.buttonAllergy) {
            patientInfoDatabaseService.addAllergy(allergyReg.getText().toString().trim());
        } else if (i == R.id.buttonDrugRegimen) {
            patientInfoDatabaseService.addDrug(drugRegimenDrugReg.getText().toString().trim(),
                    drugRegimenDosageReg.getText().toString().trim(),
                    drugRegimenTimesReg.getText().toString().trim());
        } else if (i == R.id.buttonDrugReaction) {
            patientInfoDatabaseService.addDrugReaction(drugReactionDrugReg.getText().toString().trim(),
                    drugReactionReactionReg.getText().toString().trim());
        } else if (i == R.id.buttonSubstance) {
            patientInfoDatabaseService.addSubstance(substancesReg.getText().toString().trim());
        } else if (i == R.id.buttonSmoking) {
            patientInfoDatabaseService.addSmoking(smokingReg.getText().toString().trim());
        } else if (i == R.id.buttonDrinking) {
            patientInfoDatabaseService.addDrinking(drinkingReg.getText().toString().trim());
        } else if (i == R.id.buttonExercise) {
            patientInfoDatabaseService.addExercise(exerciseReg.getText().toString().trim());
        }
    }
}
