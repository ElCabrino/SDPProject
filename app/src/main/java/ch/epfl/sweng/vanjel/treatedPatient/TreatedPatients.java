package ch.epfl.sweng.vanjel.treatedPatient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

public class TreatedPatients extends AppCompatActivity {

    private String docUID;
    private RecyclerView recyclerView;
    private ArrayList<Patient> treatedPatients;
    private List<String> treatedPatientsUID;
    private TreatedPatientsAdapter adapter;

    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treated_patients);
        try {
            setupValues();
            getPatientsFirebase();
        } catch (FirebaseAuthInvalidUserException e) {
            e.printStackTrace();
        }
    }

    private void setupValues() throws FirebaseAuthInvalidUserException {
        if (auth.getCurrentUser() == null) {
            throw new FirebaseAuthInvalidUserException("treated patient", "User not logged in");
        }
        treatedPatients = new ArrayList<>();
        treatedPatientsUID = new ArrayList<>();
        docUID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.treatedPatientsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getPatientsFirebase() {
        database.getReference("Doctor").child(docUID).child("TreatedPatients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey() != null) {
                        treatedPatientsUID.add(dataSnapshot1.getKey());
                    }
                }
                getPatientsFromUID();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPatientsFromUID() {
        database.getReference("Patient").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if (treatedPatientsUID.contains(dataSnapshot1.getKey())) {
                            treatedPatients.add(dataSnapshot1.getValue(Patient.class));
                        }
                    }
                    adapter = new TreatedPatientsAdapter(TreatedPatients.this, treatedPatients);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

}
