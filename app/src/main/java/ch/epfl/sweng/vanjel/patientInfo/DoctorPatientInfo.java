package ch.epfl.sweng.vanjel.patientInfo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.vanjel.R;

/**
 * Class to represent a patient's medical information from the doctor's point of view.
 *
 * @author Nicolas BRANDT
 * @reviewer Aslam CADER
 */
public class DoctorPatientInfo extends AppCompatActivity {

    private PatientInfoDatabaseService patientInfoDatabaseService;

    private ListView listViewConditions;
    private ListView listViewSurgeries;
    private ListView listViewAllergies;
    private ListView listViewDrugReactions;
    private ListView listViewDrugs;
    private ListView listViewSubstances;

    private TextView textViewSmoking;
    private TextView textViewDrinking;
    private TextView textViewExercise;

    private final List<InfoString> conditionList = new ArrayList<>();
    private final List<Surgery> surgeryList = new ArrayList<>();
    private final List<InfoString> allergyList = new ArrayList<>();
    private final List<DrugReaction> drugReactionList = new ArrayList<>();
    private final List<Drug> drugList = new ArrayList<>();
    private final List<InfoString> substanceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_info);
        Bundle bundle = getIntent().getExtras();

        String patientID;
        if (bundle != null){
            patientID = bundle.getString("patientUID");
        } else {
            throw new Resources.NotFoundException("Extra not found");
        }

        patientInfoDatabaseService = new PatientInfoDatabaseService(this,patientID);

        getAllPatientInfoFields();
        
    }

    private void getAllPatientInfoFields() {
        listViewConditions = findViewById(R.id.doctorPtPriorConditionsList);
        listViewSurgeries = findViewById(R.id.doctorPtSurgeryList);
        listViewAllergies = findViewById(R.id.doctorPtAllergyList);
        listViewDrugReactions = findViewById(R.id.doctorPtDrugReactionList);
        listViewDrugs = findViewById(R.id.doctorPtDrugRegimenList);
        listViewSubstances = findViewById(R.id.doctorPtSubstanceList);
        textViewSmoking = findViewById(R.id.doctorPtSmokingValue);
        textViewDrinking = findViewById(R.id.doctorPtDrinkingValue);
        textViewExercise = findViewById(R.id.doctorPtExerciseValue);
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
    }

}
