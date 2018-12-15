package ch.epfl.sweng.vanjel.nearbyDoctor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.R;

/**
 * Handles interaction with activity_nearby_doctor
 *
 * @author Etienne Caquot
 * @author Aslam Cader
 */
public class NearbyDoctor extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {


    private static final int REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION = 0x01;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyA9vanYX7kgGCS4A3cffxn2-YnwDNf6zEU";

    private View permissionDeniedView;
    private TextView permissionDeniedRationaleView;
    private LinearLayout NearbyDoctorTop;

    //to get user Location
    private FusedLocationProviderClient mFusedLocationClient;

    // map
    private MapView mapView;
    private GoogleMap gmap;

    // Database
    private FirebaseDatabase database;

    private final HashMap<String, Doctor> doctorHashMap = new HashMap<>();

    private RecyclerView recyclerView;

    private LatLng userLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_doctor);
        init();
        // map bundle
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    /**
     * Initialize the necessary libraries and references
     */
    private void init() {
        //permission view
        permissionDeniedView = findViewById(R.id.permission_denied_view);
        permissionDeniedRationaleView = findViewById(R.id.permission_denied_rationale);
        //recycler for list
        recyclerView = findViewById(R.id.listNearbyDoctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // map reference
        mapView = findViewById(R.id.mapViewNearbyDoctor);
        // database reference
        database = FirebaseDatabase.getInstance();
        // user position
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //layout
        TextView mapButton = findViewById(R.id.mapButton);
        mapButton.setOnClickListener(this);
        TextView listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(this);
        NearbyDoctorTop = findViewById(R.id.NearbyTopBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        getUserLocation();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.getUiSettings().setZoomControlsEnabled(true);
        getUserLocation();
    }

    /**
     * Method to get user location, store it in userLocation if not null and then call method to fetch Doctors
     */
    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION);
        } else {
            NearbyDoctorTop.setVisibility(View.VISIBLE);
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                        initMap(location);
                    } else {
                        Toast.makeText(NearbyDoctor.this, "Cannot find your Location " +
                                "please open Google Maps until your Location is find and come back", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Initialize the Google Maps map components that require user Location
     * @param location location of the doctor
     */
    private void initMap(Location location){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION);
        }
        mapView.setVisibility(View.VISIBLE);
        LatLng userPosition = new LatLng(location.getLatitude(),location.getLongitude());
        gmap.setMyLocationEnabled(true);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(userPosition, 18);
        gmap.animateCamera(yourLocation);
        gmap.getUiSettings().setMyLocationButtonEnabled(true);
        getDoctors();
    }

    /**
     * Method to fetch all doctors from firebase and add them to doctorHasMap and then call sort method them by distance.
     */
    private void getDoctors() {
        database.getReference("Doctor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    Doctor myDoctor = dataSnapshotChild.getValue(Doctor.class);
                    String key = dataSnapshotChild.getKey();
                    doctorHashMap.put(key, myDoctor);

                    if (myDoctor!=null) {
                        LatLng doctorLocation = myDoctor.getLocationFromAddress(NearbyDoctor.this);
                        // if doctor address is incorrect we do not put his marker
                        if (doctorLocation == null) {
                            Toast.makeText(NearbyDoctor.this, "Some doctors may have incorrect addresses", Toast.LENGTH_SHORT).show();
                        } else {
                            // put the pin (marker)
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.title("Dr. " + myDoctor.getLastName() + " " + myDoctor.getFirstName());
                            markerOptions.position(doctorLocation);
                            gmap.addMarker(markerOptions);
                        }
                    } //TODO firebase exception
                }
                orderDoctors(doctorHashMap,userLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    /**
     * This method orders the HashMap with Doctors and their ids according to distance to user and then finalize the activity's screen.
     * @param doctorHashMap HashMap of doctor object with their userID as key
     * @param userLocation location of the user that does the query
     */
    private void orderDoctors(HashMap<String, Doctor> doctorHashMap, LatLng userLocation) {
        HashMap<String,Double> distanceHashMap = createDistanceHashMap(doctorHashMap,userLocation);
        LinkedHashMap<String,Doctor> doctorHashMapSorted = new LinkedHashMap<>(); //use LinkedHashMap to keep order
        for (Map.Entry<String,Double> entry : sortHashMapOnValues(distanceHashMap).entrySet()){
            doctorHashMapSorted.put(entry.getKey(),doctorHashMap.get(entry.getKey()));
        }
        RecyclerView.Adapter adapter = new ListNearbyDoctorsAdapter(NearbyDoctor.this, doctorHashMapSorted, userLocation);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * This method creates a HashMap with key : id of doctors and values : distance to doctor for user.
     * @param doctorHashMap HashMap of doctor object with their userID as key
     * @param userLocation location of the user that does the query
     * @return A HashMap of doctor with their distance from the patient
     */
    private HashMap<String,Double> createDistanceHashMap(HashMap<String, Doctor> doctorHashMap, LatLng userLocation){
        HashMap<String,Double> distanceHashMap = new HashMap<>();
        for(Map.Entry<String,Doctor> entry : doctorHashMap.entrySet()){
            Double doctorDistance = entry.getValue().getDistance(userLocation,this);
            distanceHashMap.put(entry.getKey(),doctorDistance);
        }
        return distanceHashMap;
    }

    /**
     * This method sorts a HashMap on its values
     * @param hm HashMap
     * @return LinkedHashMap sorted hm
     */
    private LinkedHashMap<String,Double> sortHashMapOnValues(HashMap<String,Double> hm) {
        List<Map.Entry<String,Double>> list = new LinkedList<>(hm.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,Map.Entry<String, Double> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        LinkedHashMap<String,Double> sorted = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : list) {
            sorted.put(entry.getKey(), entry.getValue());
        }
        return sorted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Boolean request = requestCode == REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION;
        Boolean result = grantResults.length > 0;
        if(request && result){
            permissionsMessage(grantResults[0]);
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * This method checks the grantResult and display message
     * depending on user's device preference toward GPS usage on app
     * @param grantResult The permission to access the localisation of the user
     */
    private void permissionsMessage(int grantResult){
        switch (grantResult){
            case PackageManager.PERMISSION_GRANTED:

                getUserLocation();
                break;
            default:
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_short);
                } else {
                    permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_long);
                }
                permissionDeniedView.setVisibility(View.VISIBLE);
                break;
        }

    }

    /**
     * Method called when the user click on the Grant Permission button. Either Asks for Location
     * permission or go to settings if user wants to grant permission but alredy checked "Don't Ask Again"
     * @param view View where the prompt is displayed
     */
    public void onGrantPermission(View view) {
        permissionDeniedView.setVisibility(View.INVISIBLE);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION);
        } else {
            goToSettings();
        }
    }

    /**
     * Opens the settings App
     */
    private void goToSettings() {
        permissionDeniedView.setVisibility(View.INVISIBLE);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        Intent settingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(settingsIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mapButton:
                mapView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                break;
            case R.id.listButton:
                mapView.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
}