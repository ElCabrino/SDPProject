package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.chat.ChatListActivity;
import ch.epfl.sweng.vanjel.favoriteList.PatientFavoriteListActivity;

/**
 * @author Luca JOSS
 * @reviewer Vincent CABRINI
 */
public class Profile extends AppCompatActivity implements View.OnClickListener {

    TextView email, lastName, firstName, birthday, gender, street, streetNumber, city, country;

    Button patientInfoButton, logoutButton;

    String newLastName, newFirstName, newStreet, newStreetNumber, newCity, newCountry;

    Button editButton, saveButton, searchButton, buttonNextAppointments,  treatedPatientsButton;
    Button setAvailabilityButton, requestsListButton, favoriteListButton, appointmentsButton;

    String userType;

    final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadContent();
    }

    private void loadContent() {
        setContentView(R.layout.activity_profile);
        getAllTextView();

        patientInfoButton = findViewById(R.id.patientInfoButton);
        logoutButton = findViewById(R.id.logoutButton);
        treatedPatientsButton = findViewById(R.id.treatedPatientsButton);

        logoutButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        patientInfoButton.setOnClickListener(this);

        setAvailabilityButton.setOnClickListener(this);
        requestsListButton.setOnClickListener(this);
        favoriteListButton.setOnClickListener(this);
        buttonNextAppointments.setOnClickListener(this);
        treatedPatientsButton.setOnClickListener(this);
        isPatientUser();
    }

    private ValueEventListener createValueEventListener(final String type) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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

        switch (v.getId()) {
            case R.id.requestsListButton:
                startActivity(new Intent(this ,DoctorAppointmentsList.class));
                break;
            case R.id.logoutButton:
                logOut();
                break;
            case R.id.patientInfoButton:
                patientInfo();
                break;
            case R.id.editButton:
                setEditText(true, View.GONE, View.VISIBLE);
                break;
            case R.id.saveButton:
                getStringFromFields();
                saveNewValues();
                setEditText(false, View.VISIBLE, View.GONE);
                break;
            case R.id.searchDoctorButton:
                startActivity(new Intent(this, SearchDoctor.class));
                break;
            case R.id.setAvailabilityButton:
                startActivity(new Intent(this, DoctorAvailabilityActivity.class));
                break;
            case R.id.treatedPatientsButton:
                startActivity(new Intent(this, TreatedPatients.class));
                break;
            case R.id.buttonNextAppointments:
                if (userType.equals("Patient")) {
                    startActivity(new Intent(this, PatientPersonalAppointments.class));
                } else {
                    startActivity(new Intent(this, DoctorComingAppointments.class));
                }
                break;
            case R.id.favoriteListButton:
                startActivity(new Intent(this, PatientFavoriteListActivity.class));
        }
    }

    public void patientInfo() {
        if (userType.equals("Patient")) {
            startActivity(new Intent(this, PatientInfo.class));
        } else {
            Toast.makeText(this, "You must be a patient to access this feature", Toast.LENGTH_LONG).show();
        }
    }

    private void logOut(){
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
        this.logoutButton = findViewById(R.id.logoutButton);
        this.searchButton = findViewById(R.id.searchDoctorButton);
        this.buttonNextAppointments = findViewById(R.id.buttonNextAppointments);
        this.setAvailabilityButton = findViewById(R.id.setAvailabilityButton);
        this.requestsListButton = findViewById(R.id.requestsListButton);
        this.favoriteListButton = findViewById(R.id.favoriteListButton);
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

    void isPatientUser() {
        DatabaseReference patientRef;
        patientRef = database.getReference("Patient");
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                    setUserAs("Patient", View.VISIBLE, View.GONE);
                } else {
                    setUserAs("Doctor", View.GONE, View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        });
    }

    private void setUserAs(String type, int v1, int v2) {
        userType = type;
        searchButton.setVisibility(v1);
        setAvailabilityButton.setVisibility(v2);
        String s = auth.getCurrentUser().getUid();
        database.getReference(type).child(s).addValueEventListener(createValueEventListener(type));
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
    
    public void openChats(View v) {
        startActivity(new Intent(this, ChatListActivity.class));
    }
}