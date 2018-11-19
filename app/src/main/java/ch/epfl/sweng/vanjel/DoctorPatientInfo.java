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

    List<InfoString> condList;
    List<Surgery> surList;
    List<InfoString> allList;
    List<DrugReaction> drugReacList;
    List<Drug> drugList;
    List<InfoString> substanceList;

    ListView lvConditions;
    ListView lvSurgeries;
    ListView lvAllergies;
    ListView lvDrugReactions;
    ListView lvDrugs;
    ListView lvSubstances;
    TextView tvSmoking;
    TextView tvDrinking;
    TextView tvExercise;


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
        condList = new ArrayList<>();
        surList = new ArrayList<>();
        allList = new ArrayList<>();
        drugReacList = new ArrayList<>();
        drugList = new ArrayList<>();
        substanceList = new ArrayList<>();
    }

    private void getAllPatientInfoFields() {
        lvConditions = findViewById(R.id.doctorPtPriorConditionsList);
        lvSurgeries = findViewById(R.id.doctorPtSurgeryList);
        lvAllergies = findViewById(R.id.doctorPtAllergyList);
        lvDrugReactions = findViewById(R.id.doctorPtDrugReactionList);
        lvDrugs = findViewById(R.id.doctorPtDrugRegimenList);
        lvSubstances = findViewById(R.id.doctorPtSubstanceList);
        tvSmoking = findViewById(R.id.doctorPtSmokingValue);
        tvDrinking = findViewById(R.id.doctorPtDrinkingValue);
        tvExercise = findViewById(R.id.doctorPtExerciseValue);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // add the database listeners
        patientInfoDatabaseService.addAmountListener(tvSmoking, "Smoking");
        patientInfoDatabaseService.addAmountListener(tvDrinking, "Drinking");
        patientInfoDatabaseService.addAmountListener(tvExercise, "Exercise");
        patientInfoDatabaseService.addListListener(condList,lvConditions,"Condition",
                InfoString.class, new InfoList<InfoString>(this, condList, R.layout.list_conditions_layout, R.id.textViewConditions));
        patientInfoDatabaseService.addListListener(surList,lvSurgeries,"Surgery",
                Surgery.class, new InfoList<Surgery>(this, surList, R.layout.list_surgeries_layout, R.id.textViewSurgeries));
        patientInfoDatabaseService.addListListener(allList,lvAllergies,"Allergy",
                InfoString.class, new InfoList<InfoString>(this, allList, R.layout.list_allergies_layout, R.id.textViewAllergies));
        patientInfoDatabaseService.addListListener(drugReacList,lvDrugReactions,"DrugReaction",
                DrugReaction.class, new InfoList<DrugReaction>(this, drugReacList, R.layout.list_drug_reactions_layout, R.id.textViewDrugReactions));
        patientInfoDatabaseService.addListListener(drugList,lvDrugs,"Drug",
                Drug.class, new InfoList<Drug>(this, drugList, R.layout.list_drugs_layout, R.id.textViewDrugs));

        patientInfoDatabaseService.addListListener(substanceList,lvSubstances,"Substance",
                InfoString.class, new InfoList<InfoString>(this, substanceList, R.layout.list_substances_layout, R.id.textViewSubstances));

    }

}
