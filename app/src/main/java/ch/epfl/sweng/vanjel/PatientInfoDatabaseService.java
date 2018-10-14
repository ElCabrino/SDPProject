package ch.epfl.sweng.vanjel;

import android.app.AlertDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    //LISTENERS
    void addConditionListener(final List<Condition> conditionList, final ListView listViewConditions){
        DatabaseReference databaseCondition = userDatabaseReference.child("Condition");
        databaseCondition.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                conditionList.clear();
                for (DataSnapshot conditionSnapshot: dataSnapshot.getChildren()) {
                    Condition condition = conditionSnapshot.getValue(Condition.class);
                    conditionList.add(condition);
                }

                ConditionList adapter = new ConditionList(activity,conditionList);
                listViewConditions.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addSurgeryListener(final List<Surgery> surgeryList, final ListView listViewSurgeries){
        DatabaseReference databaseSurgery = userDatabaseReference.child("Surgery");
        databaseSurgery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                surgeryList.clear();
                for (DataSnapshot surgerySnapshot: dataSnapshot.getChildren()) {
                    Surgery surgery = surgerySnapshot.getValue(Surgery.class);

                    surgeryList.add(surgery);

                }

                SurgeryList adapter = new SurgeryList(activity,surgeryList);
                listViewSurgeries.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addAllergyListener(final List<Allergy> allergyList, final ListView listViewAllergies){
        DatabaseReference databaseAllergy = userDatabaseReference.child("Allergy");
        databaseAllergy.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allergyList.clear();
                for (DataSnapshot allergySnapshot: dataSnapshot.getChildren()) {
                    Allergy allergy = allergySnapshot.getValue(Allergy.class);
                    allergyList.add(allergy);
                }
                AllergyList adapter = new AllergyList(activity,allergyList);
                listViewAllergies.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addDrugReactionListener(final List<DrugReaction> drugReactionList, final ListView listViewDrugReactions){
        DatabaseReference databaseDrugReaction = userDatabaseReference.child("DrugReaction");
        databaseDrugReaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drugReactionList.clear();
                for (DataSnapshot drugReactionSnapshot: dataSnapshot.getChildren()) {
                    DrugReaction drugReaction = drugReactionSnapshot.getValue(DrugReaction.class);

                    drugReactionList.add(drugReaction);

                }

                DrugReactionList adapter = new DrugReactionList(activity,drugReactionList);
                listViewDrugReactions.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addDrugListener(final List<Drug> drugList, final ListView listViewDrugs){
        DatabaseReference databaseDrug = userDatabaseReference.child("Drug");
        databaseDrug.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drugList.clear();
                for (DataSnapshot drugSnapshot: dataSnapshot.getChildren()) {
                    Drug drug = drugSnapshot.getValue(Drug.class);

                    drugList.add(drug);

                }

                DrugList adapter = new DrugList(activity,drugList);
                listViewDrugs.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addSubstanceListener(final List<Substance> substanceList, final ListView listViewSubstances){
        DatabaseReference databaseSubstance = userDatabaseReference.child("Substance");
        databaseSubstance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                substanceList.clear();
                for (DataSnapshot substanceSnapshot: dataSnapshot.getChildren()) {
                    Substance sub = substanceSnapshot.getValue(Substance.class);

                    substanceList.add(sub);

                }

                SubstanceList adapter = new SubstanceList(activity,substanceList);
                listViewSubstances.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addSmokingListener(final TextView textViewSmoking){
        DatabaseReference databaseSmoking = userDatabaseReference.child("Smoking");
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
    }

    void addDrinkingListener(final TextView textViewDrinking){
        DatabaseReference databaseDrinking = userDatabaseReference.child("Drinking");
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
    }

    void addExerciseListener(final TextView textViewExercise){
        DatabaseReference databaseExercise = userDatabaseReference.child("Exercise");
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

    //SETTERS
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
