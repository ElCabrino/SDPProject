package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView lastName;
    private TextView firstName;
    private TextView birthday;
    private TextView gender;
    private TextView email;
    private TextView street;
    private TextView streetNumber;
    private TextView city;
    private TextView postal;
    private TextView country;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("vanjel-mediscan/User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        lastName = findViewById(R.id.lastnameProfile);
        firstName = findViewById(R.id.nameProfile);
        birthday = findViewById(R.id.birthdayProfile);
        gender = findViewById(R.id.typeProfile);
        email = findViewById(R.id.emailProfile);
        street = findViewById(R.id.streetProfile);
        streetNumber = findViewById(R.id.numberStreetProfile);
        city = findViewById(R.id.cityProfile);
        postal = findViewById(R.id.postCodeProfile);
        country = findViewById(R.id.countryProfile);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    email.setText(user.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        }

}
