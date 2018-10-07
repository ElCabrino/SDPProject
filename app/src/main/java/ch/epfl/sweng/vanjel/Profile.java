package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Patient").child("UOcBueg3U1eEQs1ptII3Ga0VXuj1");

        ref.addValueEventListener(createValueEventListener());

        setContentView(R.layout.activity_profile);
    }

    private ValueEventListener createValueEventListener() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TextView email = findViewById(R.id.emailProfile);
                TextView lastName = findViewById(R.id.lastnameProfile);
                TextView firstName = findViewById(R.id.nameProfile);
                TextView birthday = findViewById(R.id.birthdayProfile);
                TextView gender = findViewById(R.id.typeProfile);
                TextView street = findViewById(R.id.streetProfile);
                TextView streetNumber = findViewById(R.id.numberStreetProfile);
                TextView city = findViewById(R.id.cityProfile);
                TextView country = findViewById(R.id.countryProfile);
                User user = dataSnapshot.getValue(User.class);
                lastName.setText(user.getLastName());
                firstName.setText(user.getFirstName());
                birthday.setText(user.getBirthday());
                gender.setText(user.getGender().toString());
                email.setText(user.getEmail());
                street.setText(user.getStreet());
                streetNumber.setText(user.getStreetNumber());
                city.setText(user.getCity());
                country.setText(user.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }

        };
        return listener;
    }

}
