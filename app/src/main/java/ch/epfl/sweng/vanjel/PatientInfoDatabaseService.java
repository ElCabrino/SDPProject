package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

class PatientInfoDatabaseService {


    private String UserID ; //FirebaseAuth.getInstance().getUid();
    private AppCompatActivity activity;
    private DatabaseReference userDatabaseReference;


    //TEMPORARY ID
    /*TODO: put the user ID of the logged user*/
    PatientInfoDatabaseService(String userID, AppCompatActivity activity) {
        UserID = userID;
        this.activity = activity;
        this.userDatabaseReference = FirebaseDatabase.getInstance().getReference("Patient").child(UserID);
    }

    //LISTENERS
    <T> void addListListener(final List<T> typeList, final ListView listView, String category,final Class c, final ArrayAdapter<T> adapter) {
        DatabaseReference db = userDatabaseReference.child(category);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    Object item = snap.getValue(c);

                    typeList.add((T) item);

                }

                //DrugList adapter = new DrugList(activity,drugList);
                //listViewDrugs.setAdapter(adapter);
                listView.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void addAmountListener(final TextView textView, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        dbCat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String s = dataSnapshot.getValue(String.class);
                    textView.setText(s);
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
            Condition cond = new Condition(condition);
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
            Allergy allergyObject = new Allergy(allergy);
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
            Substance substanceObject = new Substance(substance);
            databaseSubstance.child(substance).setValue(substanceObject);
            Toast.makeText(this.activity,"Substance added",Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the substance you want to add", Toast.LENGTH_LONG).show();
        }
    }

    void addAmount(String amount, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        if(!TextUtils.isEmpty(amount)) {
            dbCat.setValue(amount);
            Toast.makeText(this.activity,"%s added".format(category),Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the %s amount".format(category), Toast.LENGTH_LONG).show();
        }
    }

}
