package ch.epfl.sweng.vanjel.doctorInformation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.chat.ChatActivity;
import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.patientAppointment.PatientCalendarActivity;

/**
 * @author Aslam CADER
 * @reviewer Vincent CABRINI
 */
public class DoctorInformation extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private TextView lastName, firstName, activity, street, streetNumber, city, country;
  
    private Bundle bundle;
    private Doctor doctor;
    private String doctorUID;
    // database
    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    //local database
    private LocalDatabaseService localDatabaseService;

    private MaterialFavoriteButton favorite;

    private Boolean favoriteState = false;

    // map
    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyA9vanYX7kgGCS4A3cffxn2-YnwDNf6zEU";

    // confirmation value that database and map are ready, the second element that calls will launch the marker
    // since there is two asynchronous call
    private Boolean isDatabaseReady = false;
    private Boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_information);

        init();

        // we see if we have the doctor details, otherwise we quit
        doctorUID = bundle.getString("doctorUID");

        if(doctorUID == null){

            Toast.makeText(DoctorInformation.this, "No doctor content to display", Toast.LENGTH_SHORT).show();
            finish();
            return;

        } else {

            getDocWithUID(doctorUID);

        }

        Bundle mapViewBundle = null;

        if(savedInstanceState != null){

            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);

        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    private void init(){
        // bundle ref to get Extras
        bundle = getIntent().getExtras();
        // doctor details
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        activity = findViewById(R.id.activity);
        street = findViewById(R.id.street);
        streetNumber = findViewById(R.id.streetNumber);
        city = findViewById(R.id.city);
        country = findViewById(R.id.country);
        // take appointment button
        Button takeAppointment = findViewById(R.id.buttonTakeAppointment);
        takeAppointment.setOnClickListener(this);
        // map reference
        mapView = findViewById(R.id.mapViewDoctorInfo);
        //chat
        Button chat = findViewById(R.id.buttonChat);
        chat.setOnClickListener(this);
        //favorite
        favorite = findViewById(R.id.addToFavoriteButton);
        favorite.setOnClickListener(this);

    }

    public void onClick(View v) {

        int i = v.getId();
        Intent intent;
        switch (i) {
        case R.id.buttonTakeAppointment:
            intent = new Intent(this, PatientCalendarActivity.class);
            intent.putExtra("doctorUID", doctorUID);
            startActivity(intent);
            break;
        case R.id.buttonChat:
            intent = new Intent(this, ChatActivity.class);
            intent.putExtra("contactUID",doctorUID);
            intent.putExtra("contactName",doctor.toString());
            startActivity(intent);
            break;
        case R.id.addToFavoriteButton:

            if (!favoriteState){

                favoriteState = true;
                favorite.setFavorite(true);
                this.localDatabaseService.save(this.doctor, this.doctorUID);

            }
            else {

                favoriteState = false;
                favorite.setFavorite(false);
                this.localDatabaseService.delete(this.doctor, this.doctorUID);

            }

        } // end switch case

    }

    private void getDocWithUID(String uid){

        DatabaseReference ref = database.getReference().child("Doctor").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                doctor = snapshot.getValue(Doctor.class);
                initLocalDatabase();
                findIfAlreadyFavoriteButtonState();
                setData();
                isDatabaseReady = true;
                putMarkerOnMap();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(DoctorInformation.this, R.string.database_error, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initLocalDatabase(){
        this.localDatabaseService = new LocalDatabaseService(this);
    }

    private void findIfAlreadyFavoriteButtonState(){

        if (localDatabaseService.getWithKey(this.doctorUID).size() != 0){

            favoriteState = true;
            favorite.setFavorite(true);

        }

    }

    private void setData(){

        firstName.setText(doctor.getFirstName());
        lastName.setText(doctor.getLastName());
        activity.setText(doctor.getActivity());
        street.setText(doctor.getStreet());
        streetNumber.setText(doctor.getStreetNumber());
        city.setText(doctor.getCity());
        country.setText(doctor.getCountry());

    }

    private void putMarkerOnMap(){

        if(isMapReady && isDatabaseReady) {
            LatLng doctorLocation = doctor.getLocationFromAddress(this);

            // if address does not exist, we zoom in Lausanne and don't put any marker
            if (doctorLocation == null){

                doctorLocation = new LatLng(46.519962, 6.633597);

            }
            else {

                // put the pin (marker)
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(doctorLocation);
                markerOptions.title("Dr. " + doctor.getLastName() + " " + doctor.getFirstName());
                gmap.addMarker(markerOptions);
            }

            // move the camera
            gmap.moveCamera(CameraUpdateFactory.newLatLng(doctorLocation));
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);

        if (mapViewBundle == null) {

            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);

        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // method that display the wanted element
    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap = googleMap;
        gmap.setMinZoomPreference(15);


        isMapReady = true;
        putMarkerOnMap();

    }

}

