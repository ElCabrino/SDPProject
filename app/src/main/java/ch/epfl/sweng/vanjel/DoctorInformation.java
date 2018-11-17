package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.List;

import ch.epfl.sweng.vanjel.chat.ChatActivity;

public class DoctorInformation extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    TextView lastName, firstName, activity, street, streetNumber, city, country;
    private Bundle bundle;
    private Doctor doctor;
    String doctorUID;
    // database
    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    DatabaseReference ref;

    private Button takeAppointment;
    private Button chat;

    // map
    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyA9vanYX7kgGCS4A3cffxn2-YnwDNf6zEU";

    // confirmation value that database and map are ready, the second element that calls will launch the marker
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
            // get Doctor
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
        takeAppointment = findViewById(R.id.buttonTakeAppointment);
        takeAppointment.setOnClickListener(this);
        // map reference
        mapView = findViewById(R.id.mapViewDoctorInfo);
        chat = findViewById(R.id.buttonChat);
        chat.setOnClickListener(this);

    }

    public void onClick(View v) {
        if(v.getId() == R.id.buttonTakeAppointment){
            Intent intent = new Intent(this, PatientCalendarActivity.class);

            intent.putExtra("doctorUID", doctorUID);

            startActivity(intent);
        }
        if(v.getId() == R.id.buttonChat){
            Intent intent = new Intent(this, ChatActivity.class);

            intent.putExtra("doctorUID",doctorUID);
            intent.putExtra("doctorName",doctor.toString());

            startActivity(intent);
        }
    }

    private void getDocWithUID(String uid){

        ref = database.getReference().child("Doctor").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
                setData();
                isDatabaseReady = true;
                putMarkerOnMap();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DoctorInformation.this, "@+id/database_error", Toast.LENGTH_SHORT).show();
            }
        });



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

    public void putMarkerOnMap(){
        if(isMapReady && isDatabaseReady) {
            LatLng doctorLocation = getLocationFromAddress(doctor);

            // if address does not exist, we zoom in Lausanne and don't put any marker
            if (doctorLocation == null) {
                doctorLocation = new LatLng(46.519962, 6.633597);
            } else {
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

    public LatLng getLocationFromAddress(User user){

        //        String strAddress = "Place de la Gare 9, 1003 Lausanne, Switzerland";
        String strAddress = user.getStreet() + " " + user.getStreetNumber() + ", " + user.getCity() + ", " + user.getCountry();

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        // default value Lausanne, just for the compilation: the real default value is in onMapReady()
        LatLng locationForMap = new LatLng(	46.519962, 	6.633597);


        try {
            address = coder.getFromLocationName(strAddress,5);
            if (address.isEmpty()){
                return null;
            }
            Address location=address.get(0);
            location.getLatitude();
            location.getLongitude();

            locationForMap = new LatLng(location.getLatitude(), location.getLongitude());

            return locationForMap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationForMap;
    }
}

