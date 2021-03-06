package ch.epfl.sweng.vanjel.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.appointment.AppointmentNotificationBackgroundService;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.mainMenu.MainMenu;
import ch.epfl.sweng.vanjel.registration.ChooseRegistration;

/**
 * @author Vincent CABRINI
 * @reviewer Aslam CADER
 * @reviewer Etienne CAQUOT
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText emailField, passwordField;

    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Views
        emailField = findViewById(R.id.mailLogin);
        passwordField = findViewById(R.id.passwordLogin);

        //Button listener
        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        findViewById(R.id.registrationLogin).setOnClickListener(this);

    }


    @Override
    public void onStart() {

        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser a = auth.getCurrentUser();
        if(a != null){
            updateUI();
        }
    }

    private void updateUI() {
        // user is logged, open his profile page
        startActivity(new Intent(LoginActivity.this,MainMenu.class));

        // start Appointment service
        startService(new Intent(this, AppointmentNotificationBackgroundService.class));
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonLogin) {
            // user presses login button
            login(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.registrationLogin){
            // user presses registration button
            startActivity(new Intent(LoginActivity.this,ChooseRegistration.class));
        }
    }

    private void login(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        Task<AuthResult> t = auth.signInWithEmailAndPassword(email, password);
        Log.d("Login","TaskCreated");
        t.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Login","onComplete");
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, R.string.login_failed_error, Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

}
