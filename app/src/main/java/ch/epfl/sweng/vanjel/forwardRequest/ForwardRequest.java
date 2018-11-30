package ch.epfl.sweng.vanjel.forwardRequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ch.epfl.sweng.vanjel.DoctorComingAppointments;
import ch.epfl.sweng.vanjel.DoctorComingAppointmentsAdapter;
import ch.epfl.sweng.vanjel.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.R;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class ForwardRequest extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private String uid;
    private DatabaseReference ref, patientRef;

    private RecyclerView recyclerView;
    private ForwardRequestAdapter adapter;

    ArrayList<Forward> forward;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forwaded_requests);
        init();

        // TODO: Remove these mocks and put db listener instead
        forward.add(new Forward("patient1", "VkRC41z4S4U57QQwmcLnyLuEYCv2", "W7ReyyyOwAQKaganjsMQuHRb0Aj2", "John Smith", "Peter Capaldi"));
        forward.add(new Forward("patfffient1", "VkRC41z4S4U57QQwmcLnyLuEYCv2", "W7ReyyyOwAQKaganjsMQuHRb0Aj2", "Matt Smith", "Clara Oswald"));

        notifyAdapter();
    }

    public void init(){
        recyclerView = findViewById(R.id.forwardCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        forward = new ArrayList<>();
    }


    public void notifyAdapter() {
        adapter = new ForwardRequestAdapter(ForwardRequest.this, forward);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
