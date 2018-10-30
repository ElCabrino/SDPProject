package ch.epfl.sweng.vanjel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;

/**
 * Handles interaction with activity_nearby_doctor
 *
 * @author Etienne Caquot
 * @author Aslam Cader
 */
public class NearbyDoctor extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = NearbyDoctor.class.getSimpleName();

    private static final int REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION = 0x01;

    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyA9vanYX7kgGCS4A3cffxn2-YnwDNf6zEU";

    private View permissionDeniedView;
    private TextView permissionDeniedRationaleView;

    private boolean isPermissionAlreadyDenied;

    //to get user Location
    private FusedLocationProviderClient mFusedLocationClient;

    // map
    private MapView mapView;
    private GoogleMap gmap;

    // Database
    FirebaseDatabase database;

    // this will contain the doctors
    ArrayList<Doctor> doctors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_doctor);

        this.permissionDeniedView = findViewById(R.id.permission_denied_view);
        this.permissionDeniedRationaleView = findViewById(R.id.permission_denied_rationale);

        init();

        // map bundle
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        //get permissions
        findViewById(R.id.FrameLayout).post(new Runnable() {
            public void run() {
                int permissionStatus = ContextCompat.checkSelfPermission(NearbyDoctor.this, Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    checkLocation();
                } else if (!isPermissionAlreadyDenied) {
                    ActivityCompat.requestPermissions(NearbyDoctor.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION);
                }
            }
        });
        mapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.getUiSettings().setZoomControlsEnabled(true);
        // for each doctor, we put a pin
        for (Doctor doctor : doctors) {
            LatLng doctorLocation = doctor.getLocationFromAddress(this);
            // if doctor address is incorrect we do not put his marker
            if (doctorLocation == null) {
                Toast.makeText(NearbyDoctor.this, "Some doctors may have incorrect addresses", Toast.LENGTH_SHORT).show();
            } else {
                // put the pin (marker)
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("Dr. " + doctor.getLastName() + " " + doctor.getFirstName());
                markerOptions.position(doctorLocation);
                gmap.addMarker(markerOptions);
            }
        }
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
     * @param grantResult
     */
    public void permissionsMessage(int grantResult){
        switch (grantResult){
            case PackageManager.PERMISSION_GRANTED:
                checkLocation();
            default:
                isPermissionAlreadyDenied = true;
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_short);
                } else {
                    permissionDeniedRationaleView.setText(R.string.permission_denied_rationale_long);
                }

                permissionDeniedView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Method called when the user click on the Grant Permission button. Either Asks for Location
     * permission or go to settings if user wants to grant permission but alredy checked "Don't Ask Again"
      * @param view
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

    /**
     * Get the user Location and initialize the Google Maps if there is a non-null location
     */
    private void checkLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_CODE_PERMISSIONS_ACCESS_FINE_LOCATION);
        }else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        initMap(location);
                    } else {
                        Toast.makeText(NearbyDoctor.this, "Cannot find your Location", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Initialize the Google Maps map components that require user Location
     * @param location
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
    }

    /**
     * Initialize the necessary libraries and references
     */
    private void init() {
        // map reference
        mapView = findViewById(R.id.mapViewNearbyDoctor);
        // database reference
        database = FirebaseDatabase.getInstance();
        // get all doctors: Will get all doctors in database and put them in doctors ArrayList
        database.getReference().child("Doctor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    // retrieve doctor & add it to the array
                    doctors.add(dataSnapshot1.getValue(Doctor.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(NearbyDoctor.this, "@+id/database_error", Toast.LENGTH_SHORT).show();
            }
        });
        // user position
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }
}