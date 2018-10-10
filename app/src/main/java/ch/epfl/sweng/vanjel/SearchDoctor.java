package ch.epfl.sweng.vanjel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchDoctor extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName;
    private EditText lastName;
    private EditText specialisation;
    private EditText city;

    private Button searchButton;

    private String nameString;
    private String lastNameString;
    private String specialisationString;
    private String cityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        getAllFields();

        searchButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSearch) {
            getFieldStrings();
        }
    }


    private void getAllFields() {
        this.firstName = findViewById(R.id.firstNameSearch);
        this.lastName = findViewById(R.id.lastNameSearch);
        this.specialisation = findViewById(R.id.specialisationSearch);
        this.city = findViewById(R.id.citySearch);
        this.searchButton = findViewById(R.id.buttonSearch);

    }

    private void getFieldStrings() {
        this.nameString = firstName.getText().toString().trim();
        this.lastNameString = lastName.getText().toString().trim();
        this.specialisationString = specialisation.getText().toString().trim();
        this.cityString = city.getText().toString().trim();
    }
}
