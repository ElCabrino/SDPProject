package ch.epfl.sweng.vanjel.searchDoctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.LayoutHelper;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.models.Doctor;


/**
 * This activity is launched by SearchDoctor with filters on the bundle
 * This activity retrieves the doctors and displays only the one corresponding to the user filters
 * This uses a cardview displayer with FilteredDoctorsAdapter to display them
 */

/**
 * @author Aslam CADER
 * @reviewer Vincent CABRINI
 * @reviewer Etienne CAQUOT
 */

public class FilteredDoctors extends AppCompatActivity {


    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private FilteredDoctorAdapter adapter;

    // bundle to retrieve data from search
    private Bundle bundle;

    // user choices
    private String lastName, firstName, specialisation, city;

    // if it's a forward request, we will display the forward button
    private Boolean isForward;
    private HashMap<String, Object> isForwardDetails;
    private HashMap<String, Doctor> allDoctors;

    private HashMap<String, Doctor> doctorHashMap;

    private TextView noFiltered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_doctors);
        init();
        getUserFilters();
        databaseListener();
    }

    private void init(){
      
        noFiltered = findViewById(R.id.noFiltered);
        // get Database pointer
        ref = database.getReference().child("Doctor");
        recyclerView = findViewById(R.id.doctorCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorHashMap = new HashMap<>();
        allDoctors = new HashMap<>();
        bundle = getIntent().getExtras();

        if (bundle != null) {

            isForward = bundle.getBoolean("isForward");

        } else {

            // by default the forward button is not displayed
            isForward = Boolean.FALSE;

        }

        isForwardDetails = new HashMap<>();
        String doctor1Forward = bundle.getString("doctor1Forward");
        String patientForward = bundle.getString("patientForward");
        isForwardDetails.put("patient", patientForward);
        isForwardDetails.put("doctor1UID", doctor1Forward);
        adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctorHashMap, isForward, isForwardDetails, allDoctors);
        recyclerView.setAdapter(adapter);

    }

    private void getUserFilters(){

        lastName = bundle.getString("lastName");
        firstName = bundle.getString("firstName");
        specialisation = bundle.getString("specialisation");
        city = bundle.getString("city");


    }

    private boolean compareString(String s1, String s2){ return s1.toLowerCase().equals(s2.toLowerCase()); }

    // This method add the doctor to doctorHashMap if it correspond to the user filters
    private void select(){
        // userDemand correspond to what the user wrote
        // key correspond to the key (firstname, lastname, etc)
        // This method select data from array doctors where the conditions of userDemand are verified

        // local hashMap to store the selected doctors only
        HashMap<String, Doctor> selectedDoctorsHashMap = new HashMap<>();


        for (Map.Entry<String, Doctor> oneDoc : doctorHashMap.entrySet()) {
            Doctor doc = oneDoc.getValue();

            Boolean b1 = compareString(doc.getFirstName(), firstName);
            Boolean b2 = compareString(doc.getLastName(), lastName);
            Boolean b3 = compareString(doc.getActivity(), specialisation);
            Boolean b4 = compareString(doc.getCity(), city);

            if (b1 || b2 || b3 || b4) {

                selectedDoctorsHashMap.put(oneDoc.getKey(), doc);

            }


        }

        doctorHashMap = selectedDoctorsHashMap;
        adapter.notifyDataSetChanged();

    }

    private void databaseListener(){

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    doctorHashMap.put(dataSnapshot1.getKey(), dataSnapshot1.getValue(Doctor.class));
                }

                allDoctors = doctorHashMap;
                select(); // remove unwanted doctors
                adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctorHashMap, isForward, isForwardDetails, allDoctors);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                LayoutHelper.adaptLayoutIfNoData(doctorHashMap.isEmpty(),noFiltered);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(FilteredDoctors.this, "@+id/database_error", Toast.LENGTH_SHORT).show();

            }

        });
    }
}
