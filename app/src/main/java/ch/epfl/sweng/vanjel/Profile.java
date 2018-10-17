package ch.epfl.sweng.vanjel;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    TextView email;
    TextView lastName;
    TextView firstName;
    TextView birthday;
    TextView gender;
    TextView street;
    TextView streetNumber;
    TextView city;
    TextView country;

    Button patientInfoButton;
    Button logoutButton;

    String newLastName;
    String newFirstName;
    String newStreet;
    String newStreetNumber;
    String newCity;
    String newCountry;

    Button editButton;
    Button saveButton;
    Button searchButton;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    String userType;


    Boolean isPatient = new Boolean(false);
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchUserIn("Patient");
        searchUserIn("Doctor");
        loadContent();
    }

    private void loadContent() {
        setContentView(R.layout.activity_profile);
        getButtonsView();


        patientInfoButton = findViewById(R.id.patientInfoButton);
        logoutButton = findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        patientInfoButton.setOnClickListener(this);

        isPatientUser();
    }

    private ValueEventListener createValueEventListener(final String type) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllTextView();
                if (type.compareTo("Patient") == 0) {
                    setTextFields(dataSnapshot, Patient.class);
                } else if (type.compareTo("Doctor") == 0) {
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

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (v.getId() == R.id.logoutButton) {
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
        } else if (v.getId() == R.id.editButton) {
            setEditText(true, View.GONE, View.VISIBLE);
        } else if (v.getId() == R.id.saveButton) {
            getStringFromFields();
            saveNewValues();
            setEditText(false, View.VISIBLE, View.GONE);
        } else if (v.getId() == R.id.searchDoctorButton) {
            Intent intent = new Intent(this, SearchDoctor.class);
            startActivity(intent);
        }
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
        this.logoutButton = findViewById(R.id.logoutButton);
        this.searchButton = findViewById(R.id.searchDoctorButton);
    }

    // Enables editing of some fields and replaces Edit button with Save.
    private void setEditText(boolean set, int s1, int s2 ) {
        getAllTextView();
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
    // Updates user with values in the fields.
    void saveNewValues() {
        Map<String, Object> userValues = storeUpdatedValues();
        database.getReference(userType).child(getUserFirebaseID()).updateChildren(userValues).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    // Get the reference to the logged in user.
    void searchUserIn(final String category) {
        DatabaseReference patientRef = database.getReference(category);
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(getUserFirebaseID())) {
                        userType = category;
                        database.getReference(category).child(getUserFirebaseID()).addValueEventListener(createValueEventListener(category));
                        loadContent();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    // Gets the ID of the logged user. If no user is logged, get mock data of a test user.
    public String getUserFirebaseID() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            return "0N5Bg2yoxrgVzD9U5jWz1RuJLyj2";
        }
    }
}
