package ch.epfl.sweng.vanjel.models;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Class used to represent a doctor, inherits User.
 *
 * @author Vincent CABRINI
 * @reviewer Etienne CAQUOT
 */
public class Doctor extends User {

    private DoctorActivity activity;

    public Doctor(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender, DoctorActivity activity) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
        this.activity = activity;
    }

    public Doctor() {}

    public String getActivity(){
        return activity.name();
    }

    public void setActivity(DoctorActivity activity){
        this.activity = activity;
    }

    public double getDistance(LatLng userLocation, Context context) {
        Log.d("DOC","-- CREATE DISTANCE ONE BY ONE");
        float[] results = new float[1];
        LatLng doctorLocation = this.getLocationFromAddress(context);
//      if(doctorLocation.equals(null)) return null;
        Location.distanceBetween(userLocation.latitude, userLocation.longitude, doctorLocation.latitude, doctorLocation.longitude, results);
        return results[0];
    }
}
