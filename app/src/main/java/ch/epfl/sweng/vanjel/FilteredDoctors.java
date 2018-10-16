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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_doctors);

        init();
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
