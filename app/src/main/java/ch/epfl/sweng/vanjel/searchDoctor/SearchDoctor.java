package ch.epfl.sweng.vanjel.searchDoctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.NoSuchElementException;

import ch.epfl.sweng.vanjel.R;

/**
 * @author Luca JOSS
 * @reviewer Aslam CADER
 */
public class SearchDoctor extends AppCompatActivity implements View.OnClickListener {

    private EditText firstName, lastName, specialisation, city;

    private Button searchButton;

    // Strings that are put in the bundle
    private String nameString, lastNameString, specialisationString, cityString;

    // Strings that are getted in the bundle
    private String doctor1Forward, patientForward;
    private Boolean isForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);
        getAllFields();
        getBundle();
        getAllButtons();
        searchButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonSearch) {
            getFieldStrings();
            Intent intent = new Intent(SearchDoctor.this, FilteredDoctors.class);
            Bundle b = new Bundle();
            b.putString("lastName",lastNameString);
            b.putString("firstName",nameString);
            b.putString("specialisation",specialisationString);
            b.putString("city",cityString);
            b.putString("doctor1Forward", doctor1Forward);
            b.putString("patientForward", patientForward);
            intent.putExtra("isForward", isForward);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
    }

    private void getBundle(){
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isForward = bundle.getBoolean("isForward");
            doctor1Forward = bundle.getString("doctor1Forward");
            patientForward = bundle.getString("patientForward");
        } else {
            throw new NoSuchElementException("Extras in search doctor are empty");
        }

    }


    private void getAllFields() {
        this.firstName = findViewById(R.id.firstNameSearch);
        this.lastName = findViewById(R.id.lastNameSearch);
        this.specialisation = findViewById(R.id.specialisationSearch);
        this.city = findViewById(R.id.citySearch);

    }
    private void getAllButtons() {
        this.searchButton = findViewById(R.id.buttonSearch);
    }

    private void getFieldStrings() {
        this.nameString = firstName.getText().toString().trim();
        this.lastNameString = lastName.getText().toString().trim();
        this.specialisationString = specialisation.getText().toString().trim();
        this.cityString = city.getText().toString().trim();
    }
}
