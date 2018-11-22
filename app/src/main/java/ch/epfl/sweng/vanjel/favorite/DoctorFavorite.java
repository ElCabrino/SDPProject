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
}
