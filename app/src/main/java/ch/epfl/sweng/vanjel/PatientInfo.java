package ch.epfl.sweng.vanjel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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

        saveButton = findViewById(R.id.buttonGenInfoPtReg);

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

    private void getAllPatientInfoFields() {
        listViewConditions = findViewById(R.id.ptPriorConditionsList);
        listViewSurgeries = findViewById(R.id.ptSurgeryList);
        listViewAllergies = findViewById(R.id.ptAllergyList);
        listViewDrugReactions = findViewById(R.id.ptDrugReactionList);
        listViewDrugs = findViewById(R.id.ptDrugRegimenList);
        listViewSubstances = findViewById(R.id.ptSubstanceList);
        textViewSmoking = findViewById(R.id.ptSmokingValue);
        textViewDrinking = findViewById(R.id.ptDrinkingValue);
        textViewExercise = findViewById(R.id.ptExerciseValue);
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
        priorConditionsReg = findViewById(R.id.ptPriorConditionsReg);
        surgeriesReg = findViewById(R.id.ptSurgeryReg);
        surgeriesYearReg = findViewById(R.id.ptSurgeryYearReg);
        allergyReg = findViewById(R.id.ptAllergyReg);
        drugReactionDrugReg = findViewById(R.id.ptDrugReactionDrugReg);
        drugReactionReactionReg = findViewById(R.id.ptDrugReactionReactionReg);
        drugRegimenDrugReg = findViewById(R.id.ptDrugRegimenDrugReg);
        drugRegimenDosageReg = findViewById(R.id.ptDrugRegimenDosageReg);
        drugRegimenTimesReg = findViewById(R.id.ptDrugRegimenTimesReg);
        substancesReg = findViewById(R.id.ptSubstanceReg);
        smokingReg = findViewById(R.id.ptSmokingReg);
        drinkingReg = findViewById(R.id.ptDrinkingReg);
        exerciseReg = findViewById(R.id.ptExerciseReg);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // add the database listeners
        patientInfoDatabaseService.addConditionListener(conditionList, listViewConditions);
        patientInfoDatabaseService.addSurgeryListener(surgeryList, listViewSurgeries);
        patientInfoDatabaseService.addAllergyListener(allergyList, listViewAllergies);
        patientInfoDatabaseService.addDrugReactionListener(drugReactionList, listViewDrugReactions);
        patientInfoDatabaseService.addDrugListener(drugList, listViewDrugs);
        patientInfoDatabaseService.addSubstanceListener(substanceList, listViewSubstances);
        patientInfoDatabaseService.addSmokingListener(textViewSmoking);
        patientInfoDatabaseService.addDrinkingListener(textViewDrinking);
        patientInfoDatabaseService.addExerciseListener(textViewExercise);
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
