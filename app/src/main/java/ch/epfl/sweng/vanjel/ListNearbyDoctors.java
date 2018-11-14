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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Etienne CAQUOT
 */
public class ListNearbyDoctors extends AppCompatActivity {

    private final String TAG = "ListNearbyDoctors";

    private final int REQUEST_CODE = 1;

    private DatabaseReference mDatabase;

    private HashMap<String, Doctor> doctorHashMap;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng userLocation;

    private View permissionDeniedView;
    private TextView permissionDeniedRationaleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nearby_doctors);
        begin();
        getUserLocation();
    }

    /**
     * Method to initialize data, firebase, layout and location.
     */
    private void begin() {
        //data
        doctorHashMap = new HashMap<>();

        //firebase
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor");

        //layout
        mRecyclerView = findViewById(R.id.listNearbyDoctors);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        permissionDeniedView = findViewById(R.id.permission_denied_view);
        permissionDeniedRationaleView = findViewById(R.id.permission_denied_rationale);

        //location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * Method to get user location, store it in userLocation if not null and then call method to fetch Doctors
     */
    private void getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        userLocation = new LatLng(location.getLatitude(),location.getLongitude());
                        getDoctors();
                    } else {
                        Toast.makeText(ListNearbyDoctors.this, "Cannot find your Location " +
                                "please open Google Maps until your Location is find and come back", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Method to fetch all doctors from firebase and add them to doctorHasMap and then call sort method them by distance.
     */
    private void getDoctors() {
        Log.d(TAG,"GETTING DOCS");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG,"DATA CHANGE");
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    Log.d(TAG,"EACH DOC");
                    Doctor myDoctor = dataSnapshotChild.getValue(Doctor.class);
                    String key = dataSnapshotChild.getKey();
                    doctorHashMap.put(key, myDoctor);
                }
                Log.d(TAG,"GOT ALL DOC");
                orderDoctors(doctorHashMap,userLocation);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Boolean request = requestCode == REQUEST_CODE;
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
     * This method orders the HashMap with Doctors and their ids according to distance to user and then finalize the activity's screen.
     * @param doctorhashMap
     * @param userLocation
     */
    private void orderDoctors(HashMap<String, Doctor> doctorhashMap, LatLng userLocation) {
        Log.d(TAG,"-ORDERING");
        HashMap<String,Double> distanceHashMap = createDistanceHashMap(doctorhashMap,userLocation);
        Log.d(TAG,"-CREATED DSITANCE");
        LinkedHashMap<String,Doctor> doctorHashMapSorted = new LinkedHashMap<>(); //use LinkedHashMap to keep order
        Log.d(TAG,"-PUT INTO LINKEDHASH");
        for (Map.Entry<String,Double> entry : sortHashMapOnValues(distanceHashMap).entrySet()){
            Log.d(TAG,"-PUTTING INTO LINKEDHASH");
            doctorHashMapSorted.put(entry.getKey(),doctorhashMap.get(entry.getKey()));
        }
        Log.d(TAG,"- DONE PUT");
        mRecyclerView.setVisibility(View.VISIBLE);
        mAdapter = new ListNearbyDoctorsAdapter(ListNearbyDoctors.this, doctorHashMapSorted,userLocation);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        Log.d(TAG,"- DONE");
    }

    /**
     * This method creates a HashMap with key : id of doctors and values : distance to doctor for user.
     * @param doctorhashMap
     * @param userLocation
     * @return
     */
    private HashMap<String,Double> createDistanceHashMap(HashMap<String, Doctor> doctorhashMap, LatLng userLocation){
        Log.d(TAG,"-- CREATE DISTANCE");
        HashMap<String,Double> distanceHashMap = new HashMap<>();
        for(Map.Entry<String,Doctor> entry : doctorhashMap.entrySet()){
            Log.d(TAG,"-- CREATE DISTANCE ONE BY ONE");
            Double doctorDistance = entry.getValue().getDistance(userLocation,this);
            distanceHashMap.put(entry.getKey(),doctorDistance);
        }
        Log.d(TAG,"-- CREATED DISTANCE");
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

    /**
     * Method called when the user click on the Grant Permission button. Either Asks for Location
     * permission or go to settings if user wants to grant permission but alredy checked "Don't Ask Again"
     * @param view
     */
    public void onGrantPermission(View view) {
        permissionDeniedView.setVisibility(View.INVISIBLE);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE);
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
}
