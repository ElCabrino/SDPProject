package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static ch.epfl.sweng.vanjel.R.id.logoutButton;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button patientInfoButton;
    Button logoutButton;
    TextView userProfile;


    Boolean isPatient = new Boolean(false);
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        patientInfoButton = findViewById(R.id.patientInfoButton);
        logoutButton = findViewById(R.id.logoutButton);
        userProfile = findViewById(R.id.userProfile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userProfile.setText("Hello " + user.getEmail());


        logoutButton.setOnClickListener(this);
        patientInfoButton.setOnClickListener(this);

        isPatientUser();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if( i == R.id.logoutButton){
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (i == R.id.patientInfoButton) {
            if (isPatient) {
                Intent intent = new Intent(this, PatientInfo.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "You must be a patient to access this feature", Toast.LENGTH_LONG).show();
            }
        }

    }

    void isPatientUser() {
        DatabaseReference patientRef;
        patientRef = database.getReference("Patient");
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                        isPatient = true;
                    } else {
                        isPatient = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
