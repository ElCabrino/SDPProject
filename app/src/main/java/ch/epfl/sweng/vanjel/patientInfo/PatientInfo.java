package ch.epfl.sweng.vanjel.patientInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;

/**
 * A class to represent the medical information of a patient.
 *
 * @author Nicolas BRANDT
 * @reviewer Vincent CABRINI
 */
public class PatientInfo extends AppCompatActivity implements View.OnClickListener {

    private PatientInfoDatabaseService patientInfoDatabaseService;

    private EditText priorConditionsReg;
    private EditText surgeriesReg;
    private EditText surgeriesYearReg;
    private EditText allergyReg;
    private EditText drugReactionDrugReg;
    private EditText drugReactionReactionReg;
    private EditText drugRegimenDrugReg;
    private EditText drugRegimenDosageReg;
    private EditText drugRegimenTimesReg;
    private EditText substancesReg;
    private EditText smokingReg;
    private EditText drinkingReg;
    private EditText exerciseReg;

    private Button buttonConditions;
    private Button buttonSurgeries;
    private Button buttonAllergies;
    private Button buttonDrugReactions;
    private Button buttonDrug;
    private Button buttonSubstance;
    private Button buttonSmoking;
    private Button buttonDrinking;
    private Button buttonExercise;

    private ListView listViewConditions;
    private ListView listViewSurgeries;
    private ListView listViewAllergies;
    private ListView listViewDrugReactions;
    private ListView listViewDrugs;
    private ListView listViewSubstances;
    private TextView textViewSmoking;
    private TextView textViewDrinking;
    private TextView textViewExercise;

    private List<InfoString> conditionList;
    private List<Surgery> surgeryList;
    private List<InfoString> allergyList;
    private List<DrugReaction> drugReactionList;
    private List<Drug> drugList;
    private List<InfoString> substanceList;

    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        //patientInfoDatabaseService = new PatientInfoDatabaseService(this,auth.getUid());
        if (auth.getCurrentUser() != null) {
            patientInfoDatabaseService = new PatientInfoDatabaseService(this, auth.getCurrentUser().getUid());
            getAllEditText();
            getAllPatientInfoFields();
            initializeButtonsListeners();
            initializeLists();
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_LONG).show();
        }

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
                InfoString.class, new InfoList<>(this, conditionList, R.layout.list_conditions_layout, R.id.textViewConditions));
        patientInfoDatabaseService.addListListener(surgeryList,listViewSurgeries,"Surgery",
                Surgery.class, new InfoList<>(this, surgeryList, R.layout.list_surgeries_layout, R.id.textViewSurgeries));
        patientInfoDatabaseService.addListListener(allergyList,listViewAllergies,"Allergy",
                InfoString.class, new InfoList<>(this, allergyList, R.layout.list_allergies_layout, R.id.textViewAllergies));
        patientInfoDatabaseService.addListListener(drugReactionList,listViewDrugReactions,"DrugReaction",
                DrugReaction.class, new InfoList<>(this, drugReactionList, R.layout.list_drug_reactions_layout, R.id.textViewDrugReactions));
        patientInfoDatabaseService.addListListener(drugList,listViewDrugs,"Drug",
                Drug.class, new InfoList<>(this, drugList, R.layout.list_drugs_layout, R.id.textViewDrugs));
        patientInfoDatabaseService.addListListener(substanceList,listViewSubstances,"Substance",
                InfoString.class, new InfoList<>(this, substanceList, R.layout.list_substances_layout, R.id.textViewSubstances));


        patientInfoDatabaseService.addAmountListener(textViewSmoking, "Smoking");
        patientInfoDatabaseService.addAmountListener(textViewDrinking, "Drinking");
        patientInfoDatabaseService.addAmountListener(textViewExercise, "Exercise");

        //add listeners to listviews for updates
        patientInfoDatabaseService.listViewListener(listViewConditions, conditionList, "Condition", this);
        patientInfoDatabaseService.listViewListener(listViewAllergies, allergyList, "Allergy", this);
        patientInfoDatabaseService.listViewListener(listViewSubstances, substanceList, "Substance", this);
        patientInfoDatabaseService.listViewListener(listViewSurgeries, surgeryList, "Surgery", this);
        patientInfoDatabaseService.listViewListener(listViewDrugReactions, drugReactionList, "DrugReaction", this);
        patientInfoDatabaseService.listViewListener(listViewDrugs, drugList, "Drug", this);


    }

    //TODO: consistency
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.buttonPriorConditions:
                patientInfoDatabaseService.
                        addItemToDatabase(getTextFromField(priorConditionsReg), "Condition", new InfoString(getTextFromField(priorConditionsReg)));
                break;
            case R.id.buttonSurgery:
                patientInfoDatabaseService.addItemToDatabase(getTextFromField(surgeriesReg), "Surgery",
                        new Surgery(getTextFromField(surgeriesReg), getTextFromField(surgeriesYearReg)));
                break;
            case R.id.buttonAllergy:
                patientInfoDatabaseService.addItemToDatabase(getTextFromField(allergyReg), "Allergy",
                        new InfoString(allergyReg.getText().toString().trim()));
                break;
            case R.id.buttonDrugRegimen:
                Drug drug = new Drug(getTextFromField(drugRegimenDrugReg), getTextFromField(drugRegimenDosageReg),
                        getTextFromField(drugRegimenTimesReg));
                patientInfoDatabaseService.addItemToDatabase(getTextFromField(drugRegimenDrugReg), "Drug", drug);
                break;
            case R.id.buttonDrugReaction:
                patientInfoDatabaseService.addItemToDatabase(getTextFromField(drugReactionDrugReg),
                        "DrugReaction", new DrugReaction(getTextFromField(drugReactionDrugReg),
                                getTextFromField(drugReactionReactionReg)));
                break;
            case R.id.buttonSubstance:
                patientInfoDatabaseService.addItemToDatabase(getTextFromField(substancesReg), "Substance",
                        new InfoString(getTextFromField(substancesReg)));
                break;
            case R.id.buttonSmoking:
                patientInfoDatabaseService.addAmount(getTextFromField(smokingReg), "Smoking");
                break;
            case R.id.buttonDrinking:
                patientInfoDatabaseService.addAmount(getTextFromField(drinkingReg), "Drinking");
                break;
            case R.id.buttonExercise:
                patientInfoDatabaseService.addAmount(getTextFromField(exerciseReg), "Exercise");
                break;

        }
    }

    static String getTextFromField(EditText field) {
        return field.getText().toString().trim();
    }


}
