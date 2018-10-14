package ch.epfl.sweng.vanjel;

import android.app.AlertDialog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ListView;
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
}
