package ch.epfl.sweng.vanjel.mainMenu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.doctorAppointment.DoctorAppointmentsList;
import ch.epfl.sweng.vanjel.doctorAppointment.DoctorComingAppointments;
import ch.epfl.sweng.vanjel.chat.ChatListActivity;
import ch.epfl.sweng.vanjel.doctorAvailability.DoctorAvailabilityActivity;
import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.favoriteList.PatientFavoriteListActivity;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.forwardRequest.ForwardRequest;
import ch.epfl.sweng.vanjel.login.LoginActivity;
import ch.epfl.sweng.vanjel.nearbyDoctor.NearbyDoctor;
import ch.epfl.sweng.vanjel.patientAppointment.PatientPersonalAppointments;
import ch.epfl.sweng.vanjel.patientInfo.PatientInfo;
import ch.epfl.sweng.vanjel.profile.Profile;
import ch.epfl.sweng.vanjel.searchDoctor.SearchDoctor;
import ch.epfl.sweng.vanjel.treatedPatient.TreatedPatientsActivity;

/**
 * @author Etienne CAQUOT
 * @reviewer Aslam CADER
 */
public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    private TextView requests;
    private TextView search;
    private TextView treated;
    private TextView nearby;
    private TextView setAvailability;
    private TextView favorite;
    private TextView infos;
    private TextView recommendations;

    private String userType;

    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        init();
        isUserPatient();
    }

    private void init() {
        TextView logout = findViewById(R.id.logoutMainMenu);
        logout.setOnClickListener(this);
        TextView profile = findViewById(R.id.profileMainMenu);
        profile.setOnClickListener(this);
        TextView nextAppointment = findViewById(R.id.nextAppointMainMenu);
        nextAppointment.setOnClickListener(this);
        TextView chat = findViewById(R.id.chatMainMenu);
        chat.setOnClickListener(this);
        requests = findViewById(R.id.requestsMainMenu);
        requests.setOnClickListener(this);
        search = findViewById(R.id.searchMainMenu);
        search.setOnClickListener(this);
        treated = findViewById(R.id.treatedMainMenu);
        treated.setOnClickListener(this);
        nearby = findViewById(R.id.nearbyMainMenu);
        nearby.setOnClickListener(this);
        setAvailability = findViewById(R.id.setAvailMainMenu);
        setAvailability.setOnClickListener(this);
        favorite = findViewById(R.id.favoriteMainMenu);
        favorite.setOnClickListener(this);
        infos = findViewById(R.id.infosMainMenu);
        infos.setOnClickListener(this);
        recommendations = findViewById(R.id.recommendationsMainMenu);
        recommendations.setOnClickListener(this);
    }

    private void isUserPatient() {
        DatabaseReference patientRef = database.getReference("Patient");
        patientRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(auth.getCurrentUser().getUid())) {
                    userType = "Patient";
                } else {
                    userType = "Doctor";
                }
                setLayout();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR", "The read failed: " + databaseError.getCode());
            }
        });
    }

    private void setLayout() {
        if (userType.equals("Patient")){
            requests.setVisibility(View.INVISIBLE);
            treated.setVisibility(View.INVISIBLE);
            setAvailability.setVisibility(View.INVISIBLE);
        } else {
            search.setVisibility(View.INVISIBLE);
            nearby.setVisibility(View.INVISIBLE);
            favorite.setVisibility(View.INVISIBLE);
            infos.setVisibility(View.INVISIBLE);
            recommendations.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logoutMainMenu:
                logOut();
                break;
            case R.id.profileMainMenu:
                startActivity(new Intent(this, Profile.class).putExtra("userType",userType));
                break;
            case R.id.nextAppointMainMenu:
                nextAppointments();
                break;
            case R.id.chatMainMenu:
                startActivity(new Intent(this, ChatListActivity.class));
                break;
            case R.id.requestsMainMenu:
                startActivity(new Intent(this ,DoctorAppointmentsList.class));
                break;
            case R.id.searchMainMenu:
                startActivity(new Intent(this, SearchDoctor.class).putExtra("isForward",false)
                        .putExtra("doctor1Forward","").putExtra("patientForward",""));
                break;
            case R.id.treatedMainMenu:
                startActivity(new Intent(this, TreatedPatientsActivity.class));
                break;
            case R.id.nearbyMainMenu:
                startActivity(new Intent(this, NearbyDoctor.class));
                break;
            case R.id.setAvailMainMenu:
                startActivity(new Intent(this, DoctorAvailabilityActivity.class));
                break;
            case R.id.favoriteMainMenu:
                startActivity(new Intent(this, PatientFavoriteListActivity.class));
                break;
            case R.id.infosMainMenu:
                startActivity(new Intent(this, PatientInfo.class));
                break;
            case R.id.recommendationsMainMenu:
                startActivity(new Intent(this, ForwardRequest.class));
                break;
        }
    }

    private void logOut(){
        LocalDatabaseService l = new LocalDatabaseService(this);
        l.nuke();
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void nextAppointments(){
        if (userType.equals("Patient")) {
            startActivity(new Intent(this, PatientPersonalAppointments.class));
        } else {
            startActivity(new Intent(this, DoctorComingAppointments.class));
        }
    }
}
