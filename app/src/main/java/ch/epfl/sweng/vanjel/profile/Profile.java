package ch.epfl.sweng.vanjel.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.models.User;

/**
 * @author Luca JOSS
 * @reviewer Vincent CABRINI
 * @reviewer Etienne CAQUOT
 */
public class Profile extends AppCompatActivity implements View.OnClickListener {

    EditText email, lastName, firstName, birthday, gender, street, streetNumber, city, country;

    String newLastName, newFirstName, newStreet, newStreetNumber, newCity, newCountry;

    Button editButton, saveButton;

    String userType;

    final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userType = getIntent().getStringExtra("userType");
        loadContent();
        setUserAs(userType);
    }

    private void setUserAs(String type) {
        userType = type;
        String userUid = auth.getCurrentUser().getUid();
        database.getReference(type).child(userUid).addValueEventListener(createValueEventListener(type));

    }

    private ValueEventListener createValueEventListener(final String type) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (type.compareTo("Patient") == 0) {
                    setTextFields(dataSnapshot, Patient.class);
                } else {
                    setTextFields(dataSnapshot, Doctor.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
        return listener;
    }

    private void setTextFields(DataSnapshot dataSnapshot, Class<? extends User> c) {
        User user = dataSnapshot.getValue(c);
        c.cast(user);
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

    private void loadContent() {
        getAllTextView();
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
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
        this.editButton = findViewById(R.id.editButton);
        this.saveButton = findViewById(R.id.saveButton);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editButton:
                setEditText(true, View.GONE, View.VISIBLE);
                break;
            case R.id.saveButton:
                getStringFromFields(); saveNewValues();
                setEditText(false, View.VISIBLE, View.GONE);
                break;
        }
    }

    // Enables editing of some fields and replaces Edit button with Save.
    private void setEditText(boolean set, int s1, int s2 ) {
        this.lastName.setEnabled(set);
        this.lastName.requestFocus();
        this.firstName.setEnabled(set);
        this.firstName.requestFocus();
        this.street.setEnabled(set);
        this.street.requestFocus();
        this.streetNumber.setEnabled(set);
        this.streetNumber.requestFocus();
        this.city.setEnabled(set);
        this.city.requestFocus();
        this.country.setEnabled(set);
        this.country.requestFocus();

        this.editButton.setVisibility(s1);
        this.saveButton.setVisibility(s2);
        this.birthday.setVisibility(s1);
        this.gender.setVisibility(s1);
        this.email.setVisibility(s1);
    }

    // Get string values from the fields.
    void getStringFromFields(){
        this.newFirstName = this.firstName.getText().toString().trim();
        this.newLastName = this.lastName.getText().toString().trim();
        this.newStreet = this.street.getText().toString().trim();
        this.newStreetNumber = this.streetNumber.getText().toString().trim();
        this.newCity = this.city.getText().toString().trim();
        this.newCountry = this.country.getText().toString().trim();
    }

    // Updates user with values in the fields.
    void saveNewValues() {
        Map<String, Object> userValues = storeUpdatedValues();
        database.getReference(userType).child(auth.getCurrentUser().getUid()).updateChildren(userValues).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(Profile.this, "User successfully updated.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Failed to update user.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Map<String, Object> storeUpdatedValues() {
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("firstName", this.newFirstName);
        userValues.put("lastName", this.newLastName);
        userValues.put("street", this.newStreet);
        userValues.put("streetNumber", this.newStreetNumber);
        userValues.put("city", this.newCity);
        userValues.put("country", this.newCountry);
        return userValues;
    }

}