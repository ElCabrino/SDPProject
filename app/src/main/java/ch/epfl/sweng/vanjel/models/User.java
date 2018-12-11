package ch.epfl.sweng.vanjel.models;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * @author Vincent CABRINI
 * @reviewer Luca JOSS
 */

public abstract class User {

    private String email, firstName, lastName, birthday, street, streetNumber, city, country;

    private Gender gender;

    User(String email, String firstName, String lastName, String birthday, String street,
         String streetNumber, String city, String country, Gender gender) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.gender = gender;
    }

    User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LatLng getLocationFromAddress(Context context){

        //        String strAddress = "Place de la Gare 9, 1003 Lausanne, Switzerland";
        String strAddress = this.getStreet() + " " + this.getStreetNumber() + ", " + this.getCity() + ", " + this.getCountry();

        Geocoder coder = new Geocoder(context);

        List<Address> address;
        // default value Lausanne, just for the compilation: the real default value is in onMapReady()
//        LatLng locationForMap = new LatLng(	46.519962, 	6.633597);
        LatLng locationForMap = null;

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

        return null;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
