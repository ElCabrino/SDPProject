package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RegistrationStatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recuperating the registration status
        Bundle b = getIntent().getExtras();
        TextView text = findViewById(R.id.loginStatus);
        //setting the text with the current registration status
        text.setText(b.getString("status"));
        //displaying
        setContentView(R.layout.activity_registration_status);

    }
}
