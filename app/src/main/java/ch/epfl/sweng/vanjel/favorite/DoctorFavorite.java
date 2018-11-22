package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class DoctorFavorite {

    @PrimaryKey
    @NonNull
    String doctorID;

    @ColumnInfo(name = "first_name")
    String firstName;

    @ColumnInfo(name = "last_name")
    String lastName;

    @ColumnInfo(name = "street")
    String street;

    @ColumnInfo(name = "street_number")
    String streetNumber;

    @ColumnInfo(name = "city")
    String city;

    @ColumnInfo(name = "country")
    String country;

    @ColumnInfo(name = "profession")
    String profession;

    public DoctorFavorite(String doctorID, String firstName, String lastName, String street, String streetNumber, String city, String country, String profession) {
        this.doctorID = doctorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.streetNumber = streetNumber;
        this.city = city;
        this.country = country;
        this.profession = profession;
    }


    @NonNull
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(@NonNull String doctorID) {
        this.doctorID = doctorID;
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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
