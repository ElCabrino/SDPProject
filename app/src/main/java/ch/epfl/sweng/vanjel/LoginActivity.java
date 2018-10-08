package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Views
        emailField = findViewById(R.id.mailLogin);
        passwordField = findViewById(R.id.passwordLogin);

        //Button listener
        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.registationLogin).setOnClickListener(this);

        //Initialize Auth
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        /* TODO: updater l'interface si deja loggé; */

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.buttonLogin) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.buttonReg){
            Intent intent = new Intent(LoginActivity.this,ChooseRegistration.class);
            startActivity(intent);
        }
    }

    private void createAccount(String s, String s1) {
    }

}
