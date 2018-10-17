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

    List<InfoString> conditionList;
    List<Surgery> surgeryList;
    List<InfoString> allergyList;
    List<DrugReaction> drugReactionList;
    List<Drug> drugList;
    List<InfoString> substanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        String UserID = FirebaseAuth.getInstance().getUid();
        if (UserID!=null) {
            patientInfoDatabaseService =
                    new PatientInfoDatabaseService(UserID, this);
        } else {
            patientInfoDatabaseService =
                    new PatientInfoDatabaseService("I3h9NVPXwmb0Ab2auVnaMSgjaLY2", this);
        }

        saveButton = findViewById(R.id.buttonGenInfoPtReg);

        getAllEditText();

        getAllButtons();

        getAllPatientInfoFields();

        initializeButtonsListeners();

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

    private void getAllButtons() {

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
        patientInfoDatabaseService.addListListener(conditionList,listViewConditions,"Condition",
                InfoString.class, new InfoList<InfoString>(this, conditionList, R.layout.list_conditions_layout, R.id.textViewConditions));
        patientInfoDatabaseService.addListListener(surgeryList,listViewSurgeries,"Surgery",
                Surgery.class, new InfoList<Surgery>(this, surgeryList, R.layout.list_surgeries_layout, R.id.textViewSurgeries));
        patientInfoDatabaseService.addListListener(allergyList,listViewAllergies,"Allergy",
                InfoString.class, new InfoList<InfoString>(this, allergyList, R.layout.list_allergies_layout, R.id.textViewAllergies));
        patientInfoDatabaseService.addListListener(drugReactionList,listViewDrugReactions,"DrugReaction",
                DrugReaction.class, new InfoList<DrugReaction>(this, drugReactionList, R.layout.list_drug_reactions_layout, R.id.textViewDrugReactions));
        patientInfoDatabaseService.addListListener(drugList,listViewDrugs,"Drug",
                Drug.class, new InfoList<Drug>(this, drugList, R.layout.list_drugs_layout, R.id.textViewDrugs));

        patientInfoDatabaseService.addListListener(substanceList,listViewSubstances,"Substance",
                InfoString.class, new InfoList<InfoString>(this, substanceList, R.layout.list_substances_layout, R.id.textViewSubstances));

        patientInfoDatabaseService.addAmountListener(textViewSmoking, "Smoking");
        patientInfoDatabaseService.addAmountListener(textViewDrinking, "Drinking");
        patientInfoDatabaseService.addAmountListener(textViewExercise, "Exercise");
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        /*if (i == R.id.buttonPriorConditions){
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
        }*/
        switch (i){
            case R.id.buttonPriorConditions:
                patientInfoDatabaseService.
                        addItemToDatabase(priorConditionsReg.getText().toString().trim(),"Condition", new InfoString(priorConditionsReg.getText().toString().trim()));
                        //addConditionToDatabase(priorConditionsReg.getText().toString().trim());
                break;
            case R.id.buttonSurgery:
                patientInfoDatabaseService.addItemToDatabase(surgeriesReg.getText().toString().trim(), "Surgery", new Surgery(getTextFromField(surgeriesReg), getTextFromField(surgeriesYearReg)));
                /*patientInfoDatabaseService.addSurgery(surgeriesReg.getText().toString().trim(),
                        surgeriesYearReg.getText().toString().trim());*/
                break;
            case R.id.buttonAllergy:
                patientInfoDatabaseService.addItemToDatabase(allergyReg.getText().toString().trim(),"Allergy",
                                new InfoString(allergyReg.getText().toString().trim()));
                break;
            case R.id.buttonDrugRegimen:
                Drug drug = new Drug(drugRegimenDrugReg.getText().toString().trim(), drugRegimenDosageReg.getText().toString().trim(),
                        drugRegimenTimesReg.getText().toString().trim());
                patientInfoDatabaseService.addItemToDatabase(drugRegimenDrugReg.getText().toString().trim(), "Drug",drug);
                /*patientInfoDatabaseService.addDrug(drugRegimenDrugReg.getText().toString().trim(),
                        drugRegimenDosageReg.getText().toString().trim(),
                        drugRegimenTimesReg.getText().toString().trim());*/
                break;
            case R.id.buttonDrugReaction:
                patientInfoDatabaseService.addItemToDatabase(drugReactionDrugReg.getText().toString().trim(),
                        "DrugReaction", new DrugReaction(getTextFromField(drugReactionDrugReg), getTextFromField(drugReactionReactionReg)));
                /*patientInfoDatabaseService.addDrugReaction(drugReactionDrugReg.getText().toString().trim(),
                        drugReactionReactionReg.getText().toString().trim());*/
                break;
            case R.id.buttonSubstance:
                patientInfoDatabaseService.addItemToDatabase(substancesReg.getText().toString().trim(),"Substance", new InfoString(substancesReg.getText().toString().trim()));
                    break;
            case R.id.buttonSmoking:
                patientInfoDatabaseService.addAmount(smokingReg.getText().toString().trim(),"Smoking");
                break;
            case R.id.buttonDrinking:
                patientInfoDatabaseService.addAmount(drinkingReg.getText().toString().trim(),"Drinking");
                break;
            case R.id.buttonExercise:
                patientInfoDatabaseService.addAmount(exerciseReg.getText().toString().trim(),"Exercise");
                break;

        }
    }

    String getTextFromField(EditText field){
        return field.getText().toString().trim();
    }
}
