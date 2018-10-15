package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static ch.epfl.sweng.vanjel.R.id.logoutButton;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    Button patientInfoButton;
    Button logoutButton;
    TextView userProfile;

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
            Intent intent = new Intent(this, PatientInfo.class);
            startActivity(intent);
        }

    }

}
