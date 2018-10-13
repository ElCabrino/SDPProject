package ch.epfl.sweng.vanjel;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientInfoDatabaseService {

    void addConditionToDatabase(String condition, String UserID, AppCompatActivity activity) {
        DatabaseReference databaseCondition = FirebaseDatabase.getInstance().getReference("Patient");
        if (!TextUtils.isEmpty(condition)) {
            Condition cond = new Condition(UserID, condition);
            databaseCondition.child(UserID).setValue(cond);
            Toast.makeText(activity, "Condition added", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(activity, "Please enter the condition you want to add", Toast.LENGTH_LONG).show();
        }
    }
}
