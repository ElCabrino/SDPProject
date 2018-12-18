package ch.epfl.sweng.vanjel.treatedPatient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.LayoutHelper;
import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

public class TreatedPatientsActivity extends AppCompatActivity {

    private String docUID;
    private RecyclerView recyclerView;
    private Map<String,Patient> treatedPatients;
    private List<String> treatedPatientsUID;
    private TreatedPatientsAdapter adapter;
    private TextView noTreated;

    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treated_patients);
        noTreated = findViewById(R.id.noTreated);
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
        treatedPatients = new HashMap<>();
        treatedPatientsUID = new ArrayList<>();
        docUID = auth.getCurrentUser().getUid();

        recyclerView = findViewById(R.id.treatedPatientsView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getPatientsFirebase() {
        database.getReference("Doctor").child(docUID).child("TreatedPatientsActivity").addValueEventListener(new ValueEventListener() {
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
                        String uid = dataSnapshot1.getKey();
                        if (treatedPatientsUID.contains(uid)) {
                            treatedPatients.put(uid,dataSnapshot1.getValue(Patient.class));
                        }
                    }
                    adapter = new TreatedPatientsAdapter(TreatedPatientsActivity.this, treatedPatients);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    LayoutHelper.adaptLayoutIfNoData(treatedPatients.isEmpty(),noTreated);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

}
