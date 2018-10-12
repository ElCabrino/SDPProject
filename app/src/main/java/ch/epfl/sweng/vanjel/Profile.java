package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
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

    Button logoutButton;

    String newLastName;
    String newFirstName;
    String newStreet;
    String newStreetNumber;
    String newCity;
    String newCountry;

    Button editButton;
    Button saveButton;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    String userType = "Doctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUser();
    }

    private void loadContent() {
        setContentView(R.layout.activity_profile);
        getButtonsView();

        logoutButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    private ValueEventListener createValueEventListener(final String type) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getAllTextView();
                if (type.compareTo("Patient") == 0) {
                    setTextFirebase(dataSnapshot, Patient.class);
                } else if (type.compareTo("Doctor") == 0) {
                    setTextFirebase(dataSnapshot, Doctor.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
        return listener;
    }

    private void setTextFirebase(DataSnapshot dataSnapshot, Class<? extends User> c) {
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
        if (v.getId() == R.id.logoutButton) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.editButton) {
            setEditText(true, View.GONE, View.VISIBLE);
        } else if (v.getId() == R.id.saveButton) {
            getStringFromFields();
            saveNewValues();
            setEditText(false, View.VISIBLE, View.VISIBLE);
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
    }

    // Enables editing of some fields and replaces Edit button with Save.
    private void setEditText(boolean set, int s1, int s2 ) {
        getAllTextView();
        this.lastName.setEnabled(set);
        this.lastName.requestFocus();
        this.firstName.setEnabled(set);
        this.firstName.requestFocus();
        this.email.setEnabled(set);
        this.email.requestFocus();
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
        database.getReference(userType).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userValues).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    void getUser() {
        DatabaseReference patientRef = database.getReference("Patient");
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    userType = "Patient";
                    database.getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(createValueEventListener("Patient"));
                    loadContent();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        DatabaseReference doctorRef = database.getReference("Doctor");
        doctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                    database.getReference("Doctor").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(createValueEventListener("Doctor"));
                    loadContent();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        });
    }
}
