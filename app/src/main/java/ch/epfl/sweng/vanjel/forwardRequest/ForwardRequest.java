package ch.epfl.sweng.vanjel.forwardRequest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.LayoutHelper;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;


/**
 * This activity displays to the patient the forward requests:
 *  shows the doctors that has been advised to the patient
 *
 *  It uses a cardview displayer : ForwardRequestAdapter
 */

/**
 * @author Aslam CADER
 * @author Etienne CAQUOT
 * @reviewer Vincent CABRINI
 */
public class ForwardRequest extends AppCompatActivity {

    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String currentUserUID;
    private DatabaseReference ref;

    private RecyclerView recyclerView;

    private Map<String,Forward> forward;

    private TextView noRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forwaded_requests);

        try {

            init();
            notifyAdapter();

        } catch (FirebaseAuthInvalidUserException e) {

            Toast.makeText(this, "No user logged in", Toast.LENGTH_LONG).show();
            e.printStackTrace();

        }
    }

    private void init() throws FirebaseAuthInvalidUserException {

        noRequests = findViewById(R.id.noForward);
        recyclerView = findViewById(R.id.forwardCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        forward = new HashMap<>();
        ref = database.getReference().child("Forwards");

        if (FirebaseAuthCustomBackend.getInstance().getCurrentUser() != null) {

            currentUserUID = FirebaseAuthCustomBackend.getInstance().getCurrentUser().getUid();

        } else {

            throw new FirebaseAuthInvalidUserException("forwardRequest", "No user logged in");

        }

        getMyForwards();
    }

    private void getMyForwards(){

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // in case the the forward is updated, we need to remove the old stuff
                forward = new HashMap<>();

                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Forward dbForward = dataSnapshot1.getValue(Forward.class);

                    // add the forward requests if its the patients'
                    if ((dbForward !=null) && (dbForward.getPatient().equals(currentUserUID)) && (dataSnapshot1.getKey() != null)){

                        forward.put(dataSnapshot1.getKey(),dbForward);

                    }

                } // end for

                notifyAdapter();
                LayoutHelper.adaptLayoutIfNoData(forward.isEmpty(),noRequests);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


    private void notifyAdapter() {

        ForwardRequestAdapter adapter = new ForwardRequestAdapter(ForwardRequest.this, forward);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
