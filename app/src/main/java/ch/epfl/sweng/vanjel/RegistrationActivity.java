package ch.epfl.sweng.vanjel;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity implements
        View.OnClickListener{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

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
    }

    private void registerAccount(String email, String password, String confirmedPassword,
                                 String firstName, String lastName, String birthday, String street,
                                 String streetNumber, String city, String country, String gender,
                                 String userType){

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        //if the register button is pressed we call the method registerAccount with the right argument
        if (i == R.id.buttonReg){
            registerAccount(mailReg.getText().toString(), passwordReg.getText().toString(),
                    confirmPasswordReg.getText().toString(), firstNameReg.getText().toString(),
                    lastNameReg.getText().toString(), birthdayReg.getText().toString(),
                    streetReg.getText().toString(), numberReg.getText().toString(),
                    cityReg.getText().toString(), countryReg.getText().toString(),
                    genderReg.toString(), userTypeReg.toString());
        }
    }
}