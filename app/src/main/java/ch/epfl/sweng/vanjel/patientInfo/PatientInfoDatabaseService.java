package ch.epfl.sweng.vanjel.patientInfo;

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

import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

/**
 * @author Vincent CABRINI
 * @reviewer Nicolas BRANDT
 */
class PatientInfoDatabaseService {


    private final AppCompatActivity activity;
    private final DatabaseReference userDatabaseReference;


    //TEMPORARY ID
    /*TODO: put the user ID of the logged user*/
    PatientInfoDatabaseService(AppCompatActivity activity, String patientID) {
        this.activity = activity;
        FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
        this.userDatabaseReference = database.getReference("Patient").child(patientID);
    }



    //LISTENERS
    @SuppressWarnings("unchecked")
    <T> void addListListener(final List<T> typeList, final ListView listView, final String category, final Class c, final ArrayAdapter<T> adapter) {
        DatabaseReference db = userDatabaseReference.child(category);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                typeList.clear();
                for (DataSnapshot snap: dataSnapshot.getChildren()) {
                    T item = (T) snap.getValue(c);

                    typeList.add(item);

                }

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

    <T> void addItemToDatabase(String item, String category, T itemObject) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        if (!TextUtils.isEmpty(item)) {
            dbCat.child(item).setValue(itemObject);
            String categoryText = category.concat(" added");
            Toast.makeText(this.activity, categoryText, Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(this.activity, "Please enter the ".concat(category.toLowerCase()).concat(" information you want to add"), Toast.LENGTH_LONG).show();
        }
    }

    void addAmount(String amount, String category) {
        DatabaseReference dbCat = userDatabaseReference.child(category);
        if(!TextUtils.isEmpty(amount)) {
            dbCat.setValue(amount);
            Toast.makeText(this.activity,category.concat(" added"),Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this.activity,"Please enter the ".concat(category).concat(" amount"), Toast.LENGTH_LONG).show();
        }
    }

}
