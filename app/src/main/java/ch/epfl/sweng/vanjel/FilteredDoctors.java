package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * @author Aslam CADER
 * @reviewer
 */
public class FilteredDoctors extends AppCompatActivity {

    private static final String TAG = "OKLM2727";
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private ArrayList<Doctor> doctors;
    private FilteredDoctorAdapter adapter;

    // bundle to retrieve data from search
    private Bundle bundle;

    // user choices
    private String lastName;
    private String firstName;
    private String specialisation;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_doctors);
        init();
        getUserFilters();
        databaseListener();
    }

    public void init(){
        // get Database pointer
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Doctor");
        recyclerView = findViewById(R.id.doctorCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctors = new ArrayList<Doctor>();
        adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctors);
        recyclerView.setAdapter(adapter);
        bundle = getIntent().getExtras();
    }

    public void getUserFilters(){
        lastName = bundle.getString("lastName");
        firstName = bundle.getString("firstName");
        specialisation = bundle.getString("specialisation");
        city = bundle.getString("city");

    }

    public boolean compareString(String s1, String s2){
        if(s1.toLowerCase().equals(s2.toLowerCase()))
            return true;
        else
            return false;
    }

    public void select(){
        // userDemand correspond to what the user wrote
        // key correspond to the key (firstname, lastname, etc)
        // This method select data from array doctors where the conditions of userDemand are verified
        ArrayList<Doctor> newDoctors = new ArrayList<Doctor>();
        for (Doctor doc: doctors){
            if (compareString(doc.getFirstName(), firstName))
                newDoctors.add(doc);
            else if (compareString(doc.getLastName(), lastName))
                newDoctors.add(doc);
            else if (compareString(doc.getActivity(), specialisation))
                newDoctors.add(doc);
            else if(compareString(doc.getCity(), city))
                newDoctors.add(doc);

        }
        doctors = newDoctors;
        adapter.notifyDataSetChanged();
    }
    public void databaseListener(){

        // useful to see if DB problem or not
//        Doctor myDoctor = new Doctor("lol", "Gregory", "House", "10/08/8010", "Revolution Street", "45", "New Jersey", "US", Gender.Male, DoctorActivity.Generalist);
//        doctors.add(myDoctor);
//        doctors.add(myDoctor);
//        adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctors);
//        recyclerView.setAdapter(adapter);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Doctor myDoctor = dataSnapshot1.getValue(Doctor.class);
                    doctors.add(myDoctor);
                }
                select(); // remove unwanted doctors
                adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctors);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FilteredDoctors.this, "@+id/database_error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
