package ch.epfl.sweng.vanjel;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;

    // attribute that will contain pointer
    private EditText mailReg;
    private EditText passwordReg;
    private EditText confirmPasswordReg;
    private EditText firstNameReg;
    private EditText lastNameReg;
    private EditText birthdayReg;
    private EditText streetReg;
    private EditText numberReg;
    private EditText cityReg;
    private EditText countryReg;
    private Spinner genderReg;
    private Spinner activityReg;
    private Button buttonReg;

    private DatePickerDialog.OnDateSetListener mDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Boolean DoctorReg = getIntent().getExtras().getBoolean("DoctorReg");

        mAuth = FirebaseAuth.getInstance();

        // getting pointer to corresponding element on screen
        mailReg = findViewById(R.id.mailReg);
        passwordReg = findViewById(R.id.passwordReg);
        confirmPasswordReg = findViewById(R.id.confirmPasswordReg);
        firstNameReg = findViewById(R.id.firstNameReg);
        lastNameReg = findViewById(R.id.lastNameReg);
        birthdayReg = findViewById(R.id.birthdayReg);
        streetReg = findViewById(R.id.streetReg);
        numberReg = findViewById(R.id.numberReg);
        cityReg = findViewById(R.id.cityReg);
        countryReg = findViewById(R.id.countryReg);

        //Spinner
        genderReg = findViewById(R.id.genderReg);
        activityReg = findViewById(R.id.activityReg);
        activityReg.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DoctorActivity.values()));
        if (DoctorReg) activityReg.setVisibility(View.VISIBLE);

        //Button
        buttonReg = findViewById(R.id.buttonReg);

        birthdayReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Registration.this,
                        android.R.style.Theme_Holo_Light,mDateListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
            }
        });

        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                birthdayReg.setText(date);
            }
        };

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DoctorReg) {
                    registerAccount(true);
                } else {
                    registerAccount(false);
                }
            }
        });
    }

    private void registerAccount(final Boolean DoctorReg) {

        final String email = mailReg.getText().toString().trim();
        final String password = passwordReg.getText().toString().trim();
        final String confirmedPassword = confirmPasswordReg.getText().toString().trim();
        final String firstName = firstNameReg.getText().toString().trim();
        final String lastName = lastNameReg.getText().toString().trim();
        final String birthday = birthdayReg.getText().toString().trim();
        final String street = streetReg.getText().toString().trim();
        final String streetNumber = numberReg.getText().toString().trim();
        final String city = cityReg.getText().toString().trim();
        final String country = countryReg.getText().toString().trim();
        final String gender = genderReg.getSelectedItem().toString().trim();
        final String activity = activityReg.getSelectedItem().toString().trim();

        Boolean validRegistration = true;

        /* TODO: test each field if they were correctly filled */


        if (email.isEmpty()) {
            mailReg.setError(getString(R.string.input_email_error));
            mailReg.requestFocus();
            validRegistration = false;
        }

        if (firstName.isEmpty()) {
            firstNameReg.setError(getString(R.string.input_first_name_error));
            firstNameReg.requestFocus();
            validRegistration = false;
        }

        if (lastName.isEmpty()) {
            lastNameReg.setError(getString(R.string.input_last_name_error));
            lastNameReg.requestFocus();
            validRegistration = false;
        }

        if (password.isEmpty()) {
            passwordReg.setError(getString(R.string.input_password_error));
            passwordReg.requestFocus();
            validRegistration = false;
        }

        if (confirmedPassword.isEmpty()) {
            confirmPasswordReg.setError(getString(R.string.input_password_conf_error));
            confirmPasswordReg.requestFocus();
            validRegistration = false;
        }

        if (password.compareTo(confirmedPassword) != 0) {
            confirmPasswordReg.setError(getString(R.string.input_password_matching_error));
            confirmPasswordReg.requestFocus();
            validRegistration = false;
        }
//
//        if (birthday == null) {
//            // TODO: check if he's > 18, conversion in string is weird
//            Toast.makeText(PatientRegistration.this, "Please check your birth date.", Toast.LENGTH_SHORT).show();
//        }
//
        if (street.isEmpty()) {
            streetReg.setError(getString(R.string.input_street_name_error));
            streetReg.requestFocus();
            validRegistration = false;
        }

        if (streetNumber.isEmpty()) {
            numberReg.setError(getString(R.string.input_street_number_error));
            numberReg.requestFocus();
            validRegistration = false;
        }

        if (city.isEmpty()) {
            cityReg.setError(getString(R.string.input_city_name_error));
            streetReg.requestFocus();
            validRegistration = false;
        }

        if (street.isEmpty()) {
            streetReg.setError(getString(R.string.input_street_name_error));
            streetReg.requestFocus();
            validRegistration = false;
        }

        if (country.isEmpty()) {
            countryReg.setError(getString(R.string.input_country_error));
            countryReg.requestFocus();
            validRegistration = false;
        }

//
//        if (gender == null) {
//            // TODO: what? The gender is not a String --> male or female
//        }

        //if fields were incorrectly filled
        if (!validRegistration) {
            return;
        }

        if (DoctorReg) {
            //instantiating doctor
            final Doctor doctor = new Doctor(email, firstName, lastName, birthday, street, streetNumber,
                    city, country, Gender.valueOf(gender), DoctorActivity.valueOf(activity));
            //authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // task : create account
                            if (task.isSuccessful()) {
                              FirebaseDatabase.getInstance().getReference("Doctor").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // task: put data in database
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Registration.this, "Registration Successfully done!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Registration.this, "A problem occured while creating account, please try again later", Toast.LENGTH_SHORT).show();
//                                          Toast.makeText(PatientRegistration.this, "A problem occured while creating account, please try again later" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Registration.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            //instantiating patient
            final Patient patient = new Patient(email, firstName, lastName, birthday, street, streetNumber,
                    city, country, Gender.valueOf(gender));
            //authentication
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // task : create account
                            if (task.isSuccessful()) {
                                FirebaseDatabase.getInstance().getReference("Patient").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // task: put data in database
                                        if(task.isSuccessful()) {
                                            Toast.makeText(Registration.this, "Registration Successfully done!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Registration.this, "A problem occured while creating account, please try again later", Toast.LENGTH_SHORT).show();
//                                          Toast.makeText(PatientRegistration.this, "A problem occured while creating account, please try again later" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(Registration.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
