package ch.epfl.sweng.vanjel;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity{

    private DatePickerDialog.OnDateSetListener dateSetListener;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

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

    private int registrationStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        //Views
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
                    case R.id.buttonReg:
                        registerAccount(mailReg.getText().toString(), passwordReg.getText().toString(),
                                confirmPasswordReg.getText().toString(), firstNameReg.getText().toString(),
                                lastNameReg.getText().toString(), birthdayReg.getText().toString(),
                                streetReg.getText().toString(), numberReg.getText().toString(),
                                cityReg.getText().toString(), countryReg.getText().toString(),
                                genderReg.toString(), userTypeReg.toString());
                        break;
                }

            }
        });
    }

    /*registerAccount(mailReg.getText().toString(), passwordReg.getText().toString(),
                                    confirmPasswordReg.getText().toString(), firstNameReg.getText().toString(),
                                    lastNameReg.getText().toString(), birthdayReg.getText().toString(),
                                    streetReg.getText().toString(), numberReg.getText().toString(),
                                    cityReg.getText().toString(), countryReg.getText().toString(),
                                    genderReg.toString(), userTypeReg.toString());*/

    private void registerAccount(final String email, final String password, final String confirmedPassword,
                                 final String firstName, final String lastName, final String birthday, final String street,
                                 String streetNumber, String city, final String country, final String gender,
                                 final String userType){
        //get database reference
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        /* TODO: test each field if they were correctly filled */

        //instantiating user
        final User user = new User(email, firstName, lastName, birthday, street, streetNumber,
                city, country, gender, userType);

        //create user with custom information
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            //save user informations in the database, the email is the userID
                            mDatabase.child("users").child(email).setValue(user);
                            registrationStatus = 1;


                        } else {
                            registrationStatus = 0;
                        }
                    }
                });

        Intent intent = new Intent(RegistrationActivity.this,
                RegistrationStatusActivity.class);
        startActivity(intent);

    }
}