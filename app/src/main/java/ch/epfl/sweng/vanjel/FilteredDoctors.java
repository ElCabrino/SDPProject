package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import android.util.Log;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class FilteredDoctors extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private ArrayList<Doctor> doctors;
    private FilteredDoctorAdapter adapter;

    private static final String TAG = "FILTERED2727";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_doctors);

        init();
        Log.d(TAG, "onCreate: CONNARD");
        databaseListener();



    }

    public void init(){
        Log.d(TAG, "init départ");
        // get Database pointer
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Doctor");
        recyclerView = findViewById(R.id.doctorCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctors = new ArrayList<Doctor>();
        Log.d(TAG, "init fin");

    }

    public void databaseListener(){
//        Log.d(TAG, "init dblistener");
//        Doctor myDoctor = new Doctor("lol", "Gregory", "House", "10/08/8010", "Revolution Street", "45", "New Jersey", "US", Gender.Male, DoctorActivity.Generalist);
//        doctors.add(myDoctor);
//        doctors.add(myDoctor);
//        adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctors);
//        recyclerView.setAdapter(adapter);
//        Log.d(TAG, "end dblistener");

        Log.d(TAG, "init dblistener");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "datachange");
                Log.d(TAG, "more and more");
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Log.d(TAG, "here" + dataSnapshot1.toString());
                    Doctor myDoctor = dataSnapshot1.getValue(Doctor.class);
                    Log.d(TAG, "my " + myDoctor.getFirstName());
                    doctors.add(myDoctor);
                }
                adapter = new FilteredDoctorAdapter(FilteredDoctors.this, doctors);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
                Toast.makeText(FilteredDoctors.this, "@+id/database_error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
