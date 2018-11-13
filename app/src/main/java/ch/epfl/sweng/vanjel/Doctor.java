package ch.epfl.sweng.vanjel;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

public class Doctor extends User {

    DoctorActivity activity;

    Doctor(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender, DoctorActivity activity) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
        this.activity = activity;
    }

    Doctor() {}

    public String getActivity(){
        return activity.name();
    }

    public void setActivity(DoctorActivity activity){
        this.activity = activity;
    }

    public double getDistance(LatLng userLocation, Context context){

        //LatLng doctorLocation = this.getLocationFromAddress(context);

        LatLng doctorLocation = new LatLng(0,0);

//        if(doctorLocation.equals(null)) return null;

        double latA = userLocation.latitude;
        double lonA = userLocation.longitude;

        double latB = doctorLocation.latitude;
        double lonB = doctorLocation.longitude;

        double earthRadius = 6367445; // in meter

        double distance = earthRadius*Math.acos(Math.sin(latA)*Math.sin(latB) + Math.cos(latA)*Math.cos(latB)*Math.cos(lonA - lonB));

        return distance;

    }





}
