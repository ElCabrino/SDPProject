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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuthCustomBackend.getInstance();
    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

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

    // strings corresponding to the field
    private String email;
    private String password;
    private String confirmedPassword;
    private String firstName;
    private String lastName;
    private String birthday;
    private String street;
    private String streetNumber;
    private String city;
    private String country;
    private String gender;
    private String activity;

    private DatePickerDialog.OnDateSetListener mDateListener;

    private Boolean DoctorReg = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Bundle bundle= getIntent().getExtras();


        if (bundle!= null) {// to avoid the NullPointerException

            DoctorReg = getIntent().getExtras().getBoolean("DoctorReg");
        }

        getAllFields();

        activityReg.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, DoctorActivity.values()));
        if (DoctorReg) activityReg.setVisibility(View.VISIBLE);

        setBirthdayListener();

        setDateListener();

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

        getStringFromFields();

        Boolean validRegistration;

        validRegistration = areFieldsValid();

        if (!validRegistration) {
            return;
        }

        
        //instantiating doctor
        final Doctor doctor = new Doctor(email, firstName, lastName, birthday, street, streetNumber,
                city, country, Gender.valueOf(gender), DoctorActivity.valueOf(activity));

        final Patient patient = new Patient(email, firstName, lastName, birthday, street, streetNumber,
                city, country, Gender.valueOf(gender));

        //authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, createAuthListener(DoctorReg, doctor, patient));
    }

    private OnCompleteListener<AuthResult> createAuthListener(final Boolean DoctorReg,
                                                              final Doctor doctor,
                                                              final Patient patient) {
        OnCompleteListener<AuthResult> listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // task : create account
                if (task.isSuccessful()) {
                    Task<Void> val = createUser(DoctorReg, patient, doctor);
                    val.addOnCompleteListener(createDatabaseListener());
                } else {
                    Toast.makeText(Registration.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        return listener;
    }

    Task<Void> createUser(Boolean DoctorReg, Patient patient, Doctor doctor){
        Task<Void> val;
        if(DoctorReg) {
            val = database.getReference("Doctor").child(mAuth.getCurrentUser().getUid()).setValue(doctor);
        } else {
            val = database.getReference("Patient").child(mAuth.getCurrentUser().getUid()).setValue(patient);
        }
        return val;
    }

    private OnCompleteListener<Void> createDatabaseListener() {
        OnCompleteListener<Void> listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // task: put data in database
                if (task.isSuccessful()) {
                    Toast.makeText(Registration.this, "Registration Successfully done!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Registration.this, "A problem occured while creating account, please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        };
        return listener;
    }

    void getAllFields(){
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
        getOtherFields();
    }

    // Separation required to avoid CodeClimate duplicate with getAllEditText() from PatientInfo.java
    private void getOtherFields() {
        //Button
        buttonReg = findViewById(R.id.buttonReg);
        //Spinner
        genderReg = findViewById(R.id.genderReg);
        activityReg = findViewById(R.id.activityReg);
    }

    void getStringFromFields(){
        this.email = mailReg.getText().toString().trim();
        this.password = passwordReg.getText().toString().trim();
        this.confirmedPassword = confirmPasswordReg.getText().toString().trim();
        this.firstName = firstNameReg.getText().toString().trim();
        this.lastName = lastNameReg.getText().toString().trim();
        this.birthday = birthdayReg.getText().toString().trim();
        this.street = streetReg.getText().toString().trim();
        this.streetNumber = numberReg.getText().toString().trim();
        this.city = cityReg.getText().toString().trim();
        this.country = countryReg.getText().toString().trim();
        this.gender = genderReg.getSelectedItem().toString().trim();
        this.activity = activityReg.getSelectedItem().toString().trim();
    }

    void setBirthdayListener(){
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
    }

    void setDateListener(){
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                birthdayReg.setText(date);
            }
        };
    }

    boolean isEmailValid(String email){
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return false;
        }
        return true;

    }

    boolean isFieldNotEmpty(EditText editText, String editTextString, int error_id){
        if (editTextString.isEmpty()) {
            editText.setError(getString(error_id));
            editText.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    boolean arePasswordMatching(String password, String confirmedPassword, EditText confirmPasswordReg, int error_id){
        if (password.compareTo(confirmedPassword) != 0) {
            confirmPasswordReg.setError(getString(error_id));
            confirmPasswordReg.requestFocus();
            return false;
        } else { return true; }
    }

    boolean areFieldsValid(){
        boolean valid = true;
        valid = isEmailValid(email)&&isFieldNotEmpty(firstNameReg, firstName, R.string.input_first_name_error)
        &&isFieldNotEmpty(lastNameReg, lastName, R.string.input_last_name_error)
        &&isFieldNotEmpty(passwordReg, password, R.string.input_password_error)
        &&isFieldNotEmpty(confirmPasswordReg, confirmedPassword, R.string.input_password_conf_error)
        &&isFieldNotEmpty(birthdayReg, birthday, R.string.input_birthday_error)
        &&isFieldNotEmpty(streetReg, street, R.string.input_street_name_error)
        &&isFieldNotEmpty(numberReg, streetNumber, R.string.input_street_number_error)
        &&isFieldNotEmpty(countryReg, country, R.string.input_country_error)
        &&arePasswordMatching(password, confirmedPassword, confirmPasswordReg, R.string.input_password_conf_error);

        return valid;
    }
}
