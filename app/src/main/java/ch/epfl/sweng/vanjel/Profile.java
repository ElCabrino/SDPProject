package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.renderscript.Sampler;
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
import com.google.firebase.database.Query;
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
    DatabaseReference userRef;

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
                    Patient patient = dataSnapshot.getValue(Patient.class);
                    lastName.setText(patient.getLastName());
                    firstName.setText(patient.getFirstName());
                    birthday.setText(patient.getBirthday());
                    gender.setText(patient.getGender().toString());
                    email.setText(patient.getEmail());
                    street.setText(patient.getStreet());
                    streetNumber.setText(patient.getStreetNumber());
                    city.setText(patient.getCity());
                    country.setText(patient.getCountry());

                } else if (type.compareTo("Doctor") == 0) {
                    Doctor doctor = dataSnapshot.getValue(Doctor.class);
                    lastName.setText(doctor.getLastName());
                    firstName.setText(doctor.getFirstName());
                    birthday.setText(doctor.getBirthday());
                    gender.setText(doctor.getGender().toString());
                    email.setText(doctor.getEmail());
                    street.setText(doctor.getStreet());
                    streetNumber.setText(doctor.getStreetNumber());
                    city.setText(doctor.getCity());
                    country.setText(doctor.getCountry());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: "+databaseError.getCode());
            }
        };
        return listener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logoutButton) {
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.editButton) {
            enableEditText();
        } else if (v.getId() == R.id.saveButton) {
            getStringFromFields();
            saveNewValues();
            disableEditText();
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

    // Disables editing of fields and replaces Save button with Edit.
    private void disableEditText() {
        getAllTextView();
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
        Map<String, Object> userValues = new HashMap<>();
        userValues.put("firstName", this.newFirstName);
        userValues.put("lastName", this.newLastName);
        userValues.put("street", this.newStreet);
        userValues.put("streetNumber", this.newStreetNumber);
        userValues.put("city", this.newCity);
        userValues.put("country", this.newCountry);

        this.userRef.updateChildren(userValues).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    // Get the reference to the logged in user.
    void getUser() {
        DatabaseReference patientRef = database.getReference("Patient");
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
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
