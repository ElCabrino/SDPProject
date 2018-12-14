package ch.epfl.sweng.vanjel.patientInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.R;

/**
 * @author Vincent CABRINI
 * @reviewer Nicolas BRANDT
 */
public class PatientInfo extends AppCompatActivity implements View.OnClickListener{

    PatientInfoDatabaseService patientInfoDatabaseService;

    Button saveButton;

    EditText priorConditionsReg, surgeriesReg, surgeriesYearReg, allergyReg, drugReactionDrugReg, drugReactionReactionReg, drugRegimenDrugReg;
    EditText drugRegimenDosageReg, drugRegimenTimesReg, substancesReg, smokingReg, drinkingReg, exerciseReg;

    Button buttonConditions, buttonSurgeries, buttonAllergies, buttonDrugReactions, buttonDrug, buttonSubstance, buttonSmoking;
    Button buttonDrinking, buttonExercise;

    ListView listViewConditions, listViewSurgeries, listViewAllergies, listViewDrugReactions, listViewDrugs, listViewSubstances;
    TextView textViewSmoking, textViewDrinking, textViewExercise;

    List<InfoString> conditionList;
    List<Surgery> surgeryList;
    List<InfoString> allergyList;
    List<DrugReaction> drugReactionList;
    List<Drug> drugList;
    List<InfoString> substanceList;

    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);

        //patientInfoDatabaseService = new PatientInfoDatabaseService(this,auth.getUid());
        patientInfoDatabaseService = new PatientInfoDatabaseService(this,auth.getCurrentUser().getUid());

        saveButton = findViewById(R.id.buttonGenInfoPtReg);

        getAllEditText();

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

        //add listeners to listviews for updates
        patientInfoDatabaseService.listViewListener(listViewConditions,conditionList,"Condition",this);
        patientInfoDatabaseService.listViewListener(listViewAllergies,allergyList,"Allergy",this);
        patientInfoDatabaseService.listViewListener(listViewSubstances,substanceList,"Substance",this);
        patientInfoDatabaseService.listViewListener(listViewSurgeries,surgeryList,"Surgery",this);
        patientInfoDatabaseService.listViewListener(listViewDrugReactions,drugReactionList,"DrugReaction",this);
        patientInfoDatabaseService.listViewListener(listViewDrugs,drugList,"Drug",this);



    }

    //TODO: consistency
    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i){
            case R.id.buttonPriorConditions:
                patientInfoDatabaseService.
                        addItemToDatabase(priorConditionsReg.getText().toString().trim(),"Condition", new InfoString(priorConditionsReg.getText().toString().trim()));
                break;
            case R.id.buttonSurgery:
                patientInfoDatabaseService.addItemToDatabase(surgeriesReg.getText().toString().trim(), "Surgery", new Surgery(getTextFromField(surgeriesReg), getTextFromField(surgeriesYearReg)));
                break;
            case R.id.buttonAllergy:
                patientInfoDatabaseService.addItemToDatabase(allergyReg.getText().toString().trim(),"Allergy",
                                new InfoString(allergyReg.getText().toString().trim()));
                break;
            case R.id.buttonDrugRegimen:
                Drug drug = new Drug(drugRegimenDrugReg.getText().toString().trim(), drugRegimenDosageReg.getText().toString().trim(),
                        drugRegimenTimesReg.getText().toString().trim());
                patientInfoDatabaseService.addItemToDatabase(drugRegimenDrugReg.getText().toString().trim(), "Drug",drug);
                break;
            case R.id.buttonDrugReaction:
                patientInfoDatabaseService.addItemToDatabase(drugReactionDrugReg.getText().toString().trim(),
                        "DrugReaction", new DrugReaction(getTextFromField(drugReactionDrugReg), getTextFromField(drugReactionReactionReg)));
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

    /*<T>private void initButtons(String item, String category, T itemObject,
                               final T oldItem, Button buttonUpdate, Button buttonDelete, AlertDialog alertDialog) {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String info = editTextName.getText().toString().trim();

                patientInfoDatabaseService.deleteItem(((Info)oldItem).getInfo(), category);
                patientInfoDatabaseService.addItemToDatabase(info, category, new InfoString(info));
                //patientInfoDatabaseService.updateCondition(oldInfo.getInfo(),category);

                alertDialog.dismiss();
            }
        });
        //}

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientInfoDatabaseService.deleteItem(oldInfo.getInfo(),category);
                alertDialog.dismiss();
            }
        });
    }*/


    /*private View initView(Integer id, Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(id,null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Updating drug");
        return dialogView;
    }*/




    //TODO move or refactor
    static String getTextFromField(EditText field){
        return field.getText().toString().trim();
    }


}
