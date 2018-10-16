package ch.epfl.sweng.vanjel;

import android.view.Gravity;

public class Patient extends User {
    Patient(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
    }

    Patient() {}
}
