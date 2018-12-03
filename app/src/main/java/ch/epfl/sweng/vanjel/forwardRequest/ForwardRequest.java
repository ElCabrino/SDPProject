package ch.epfl.sweng.vanjel.forwardRequest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.Doctor;
import ch.epfl.sweng.vanjel.DoctorComingAppointments;
import ch.epfl.sweng.vanjel.DoctorComingAppointmentsAdapter;
import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.R;

/**
 * @author Aslam CADER
 * @author Etienne CAQUOT
 * @reviewer
 */
public class ForwardRequest extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String currentUserUID;
    private DatabaseReference ref;

    private RecyclerView recyclerView;
    private ForwardRequestAdapter adapter;

    Map<String,Forward> forward;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forwaded_requests);
        init();

        // For debugging
        // forward.add(new Forward("patient1", "VkRC41z4S4U57QQwmcLnyLuEYCv2", "W7ReyyyOwAQKaganjsMQuHRb0Aj2", "John Smith", "Peter Capaldi"));
        // forward.add(new Forward("patfffient1", "VkRC41z4S4U57QQwmcLnyLuEYCv2", "W7ReyyyOwAQKaganjsMQuHRb0Aj2", "Matt Smith", "Clara Oswald"));

        notifyAdapter();
    }

    public void init(){
        recyclerView = findViewById(R.id.forwardCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        forward = new HashMap<>();
        ref = database.getReference().child("Forwards");
        currentUserUID = FirebaseAuthCustomBackend.getInstance().getUid();
        getMyForwards();
    }

    public void getMyForwards(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                forward = new HashMap<>(); // in case the the forward is updated, we need to remove ther old stuff
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Forward dbForward = dataSnapshot1.getValue(Forward.class);
                    if(dbForward.getPatient().equals(currentUserUID))
                        forward.put(dataSnapshot1.getKey(),dbForward);
                }
                notifyAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void notifyAdapter() {
        adapter = new ForwardRequestAdapter(ForwardRequest.this, forward);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
