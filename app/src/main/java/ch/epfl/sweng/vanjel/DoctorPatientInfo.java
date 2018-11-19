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

public class DoctorPatientInfo extends AppCompatActivity {

    PatientInfoDatabaseService patientInfoDatabaseService;

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
        setContentView(R.layout.activity_doctor_patient_info);

        //TODO: add corect uid
        String UserID = FirebaseAuth.getInstance().getUid();
        patientInfoDatabaseService = new PatientInfoDatabaseService(this);

        getAllPatientInfoFields();

        initializeLists();
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
        patientInfoDatabaseService.addAmountListener(textViewSmoking, "Smoking");
        patientInfoDatabaseService.addAmountListener(textViewDrinking, "Drinking");
        patientInfoDatabaseService.addAmountListener(textViewExercise, "Exercise");
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
    }

}
