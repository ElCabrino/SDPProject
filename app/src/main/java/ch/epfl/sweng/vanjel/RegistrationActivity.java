package ch.epfl.sweng.vanjel;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class RegistrationActivity extends AppCompatActivity{

    private DatePickerDialog.OnDateSetListener dateSetListener;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

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
    private Spinner userTypeReg;

    private Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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
        userTypeReg = findViewById(R.id.userTypeReg);

        //Button
        buttonReg = findViewById(R.id.buttonReg);


        birthdayReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (birthdayReg.hasFocus()) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(RegistrationActivity.this,
                            android.R.style.Theme_Light_Panel, dateSetListener, year, month, day);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month + "/" + dayOfMonth + "/" + year;
                birthdayReg.setText(date);
            }
        };

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {

                    // If the user press buttonReg (register button), we will register his account
                    case R.id.buttonReg:
                        registerAccount();
                        break;
                }

            }
        });
    }

    /*
        This method is implementing the register functionality:
            -   checks if the user entries are not null
            -   register and save data in DataBase
     */
    private void registerAccount(){

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
        final String gender = genderReg.toString().trim();
        final String userType = userTypeReg.toString().trim();

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

        if (password.compareTo(confirmedPassword) != 0){
            confirmPasswordReg.setError(getString(R.string.input_password_matching_error));
            confirmPasswordReg.requestFocus();
            validRegistration = false;
        }
//
//        if (birthday == null) {
//            // TODO: check if he's > 18, conversion in string is weird
//            Toast.makeText(RegistrationActivity.this, "Please check your birth date.", Toast.LENGTH_SHORT).show();
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
//
//        if (userType == null) {
//            // TODO: what ? (doctor or patient) --> same thing as Gender
//        }

        //if fields were incorrectly filled
        if (!validRegistration){return;}

        //instantiating user
        final User user = new User(email, firstName, lastName, birthday, street, streetNumber,
                city, country, gender, userType);
        //authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // task : create account
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // task: put data in database
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegistrationActivity.this, "Registration Successfully done!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistrationActivity.this, "A problem occured while creating account, please try again later", Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(RegistrationActivity.this, "A problem occured while creating account, please try again later" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegistrationActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}