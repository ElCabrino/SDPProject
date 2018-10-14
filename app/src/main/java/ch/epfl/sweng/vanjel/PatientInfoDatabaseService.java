package ch.epfl.sweng.vanjel;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientInfoDatabaseService {


    String UserID ; //FirebaseAuth.getInstance().getUid();
    AppCompatActivity activity;
    DatabaseReference userDatabaseReference;


    //TEMPORARY ID
    /*TODO: put the user ID of the logged user*/
    public PatientInfoDatabaseService(String userID, AppCompatActivity activity) {
        UserID = "ABLlrLukjAaPzaf5GA03takkw5k2";
        this.activity = activity;
        this.userDatabaseReference = FirebaseDatabase.getInstance().getReference("Patient").child(UserID);
    }

    void addConditionToDatabase(String condition) {
        DatabaseReference databaseCondition = userDatabaseReference.child("Condition");
        if (!TextUtils.isEmpty(condition)) {
            Condition cond = new Condition(UserID, condition);
            databaseCondition.child(condition).setValue(cond);
            Toast.makeText(this.activity, "Condition added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity, "Please enter the condition you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addSurgery(String surgery, String year) {
        DatabaseReference databaseSurgery = userDatabaseReference.child("Surgery");
        if(!TextUtils.isEmpty(surgery) && !TextUtils.isEmpty(year)) {
            Surgery surgeryObject = new Surgery(UserID,surgery,year);
            databaseSurgery.child(surgery).setValue(surgeryObject);
            Toast.makeText(this.activity,"Surgery added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the surgery and the year you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addAllergy(String allergy) {
        DatabaseReference databaseAllergy = userDatabaseReference.child("Allergy");
        if(!TextUtils.isEmpty(allergy)) {
            Allergy allergyObject = new Allergy(UserID,allergy);
            databaseAllergy.child(allergy).setValue(allergyObject);
            Toast.makeText(this.activity,"Allergy added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the allergy you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addDrugReaction(String drug, String reaction) {
        DatabaseReference databaseDrugReaction = userDatabaseReference.child("DrugReaction");
        if(!TextUtils.isEmpty(drug) && !TextUtils.isEmpty(reaction)) {
            DrugReaction drugReactionObject = new DrugReaction(UserID,drug,reaction);
            databaseDrugReaction.child(drug).setValue(drugReactionObject);
            Toast.makeText(this.activity,"Drug reaction added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the drug and the reaction you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addDrug(String drug, String dosage, String times) {
        DatabaseReference databaseDrug = userDatabaseReference.child("Drug");
        if(!TextUtils.isEmpty(drug) && !TextUtils.isEmpty(dosage) && !TextUtils.isEmpty(times)) {
            Drug drugObject = new Drug(UserID,drug,dosage,times);
            databaseDrug.child(drug).setValue(drugObject);
            Toast.makeText(this.activity,"Drug added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the drug, the dosage and the frequency you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addSubstance(String substance) {
        DatabaseReference databaseSubstance = userDatabaseReference.child("Substance");
        if(!TextUtils.isEmpty(substance)) {
            Substance substanceObject = new Substance(UserID,substance);
            databaseSubstance.child(substance).setValue(substanceObject);
            Toast.makeText(this.activity,"Substance added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the substance you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addSmoking(String amount) {
        DatabaseReference databaseSmoking = userDatabaseReference.child("Smoking");
        if(!TextUtils.isEmpty(amount)) {
            Smoking smokingObject = new Smoking(UserID,amount);
            databaseSmoking.child(amount).setValue(smokingObject);
            Toast.makeText(this.activity,"Smoking added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the average amount of cigarettes you smoke per day", Toast.LENGTH_LONG).show();
        }
    }

    void addDrinking(String amount) {
        DatabaseReference databaseDrinking = userDatabaseReference.child("Drinking");
        if(!TextUtils.isEmpty(amount)) {
            Drinking drinkingObject = new Drinking(UserID,amount);
            databaseDrinking.child(amount).setValue(drinkingObject);
            Toast.makeText(this.activity,"Drinking added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the average amount of alcohol you consume per day", Toast.LENGTH_LONG).show();
        }
    }

    void addExercise(String amount) {
        DatabaseReference databaseExercise = userDatabaseReference.child("Exercise");
        if(!TextUtils.isEmpty(amount)) {
            Exercise exerciseObject = new Exercise(UserID,amount);
            databaseExercise.child(amount).setValue(exerciseObject);
            Toast.makeText(this.activity,"Exercise added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the average hours of exercise you do per week", Toast.LENGTH_LONG).show();
        }
    }


}
