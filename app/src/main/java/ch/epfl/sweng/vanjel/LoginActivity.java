package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private TextView registrationLogin ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registrationLogin = findViewById(R.id.registationLogin);

        registrationLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ChooseRegistration.class);
                startActivity(intent);
            }
        });
    }


}
