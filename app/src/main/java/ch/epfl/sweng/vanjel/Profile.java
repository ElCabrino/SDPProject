package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
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

    TextView email;
    TextView lastName;
    TextView firstName;
    TextView birthday;
    TextView gender;
    TextView street;
    TextView streetNumber;
    TextView city;
    TextView country;

    String newEmail;
    String newLastName;
    String newFirstName;
    String newStreet;
    String newStreetNumber;
    String newCity;
    String newCountry;

    Button editButton;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Patient").child("UOcBueg3U1eEQs1ptII3Ga0VXuj1");

        ref.addValueEventListener(createValueEventListener());

        setContentView(R.layout.activity_profile);

        getButtonsView();

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableEditText();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("EDIT", "save edit values:");
                getStringFromFields();
                Log.d("EDIT", newLastName);
                Log.d("EDIT", newEmail);
                Log.d("EDIT", newFirstName);
                Log.d("EDIT", newStreet);
                Log.d("EDIT", newStreetNumber);
                Log.d("EDIT", newCity);
                Log.d("EDIT", newCountry);
                disableEditText();
            }
        });

    }

    private ValueEventListener createValueEventListener() {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllTextView();
                Patient user = dataSnapshot.getValue(Patient.class);
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

    private void getAllTextView() {
        this.email = findViewById(R.id.emailProfile);
        this.lastName = findViewById(R.id.lastnameProfile);
        this.firstName = findViewById(R.id.nameProfile);
        this.birthday = findViewById(R.id.birthdayProfile);
        this.gender = findViewById(R.id.genderProfile);
        this.street = findViewById(R.id.streetProfile);
        this.streetNumber = findViewById(R.id.numberStreetProfile);
        this.city = findViewById(R.id.cityProfile);
        this.country = findViewById(R.id.countryProfile);
    }

    private void getButtonsView() {
        this.editButton = findViewById(R.id.editButton);
        this.saveButton = findViewById(R.id.saveButton);
    }

    private void enableEditText() {
        getAllTextView();
        this.lastName.setEnabled(true);
        this.lastName.requestFocus();
        this.firstName.setEnabled(true);
        this.firstName.requestFocus();
        this.birthday.setVisibility(View.GONE);
        this.gender.setVisibility(View.GONE);
        this.email.setEnabled(true);
        this.email.requestFocus();
        this.street.setEnabled(true);
        this.street.requestFocus();
        this.streetNumber.setEnabled(true);
        this.streetNumber.requestFocus();
        this.city.setEnabled(true);
        this.city.requestFocus();
        this.country.setEnabled(true);
        this.country.requestFocus();

        this.editButton.setVisibility(View.GONE);
        this.saveButton.setVisibility(View.VISIBLE);
    }

    private void disableEditText() {getAllTextView();
        this.lastName.setEnabled(false);
        this.lastName.requestFocus();
        this.firstName.setEnabled(false);
        this.firstName.requestFocus();
        this.birthday.setVisibility(View.VISIBLE);
        this.gender.setVisibility(View.VISIBLE);
        this.email.setEnabled(false);
        this.email.requestFocus();
        this.street.setEnabled(false);
        this.street.requestFocus();
        this.streetNumber.setEnabled(false);
        this.streetNumber.requestFocus();
        this.city.setEnabled(false);
        this.city.requestFocus();
        this.country.setEnabled(false);
        this.country.requestFocus();

        this.editButton.setVisibility(View.VISIBLE);
        this.saveButton.setVisibility(View.GONE);
    }

    void getStringFromFields(){
        this.newEmail = this.email.getText().toString().trim();
        this.newFirstName = this.firstName.getText().toString().trim();
        this.newLastName = this.lastName.getText().toString().trim();
        this.newStreet = this.street.getText().toString().trim();
        this.newStreetNumber = this.streetNumber.getText().toString().trim();
        this.newCity = this.city.getText().toString().trim();
        this.newCountry = this.country.getText().toString().trim();
    }

    void saveNewValues() {
    }
}
