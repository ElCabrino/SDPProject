package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DoctorFavorite {

    @PrimaryKey
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
}
