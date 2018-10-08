package ch.epfl.sweng.vanjel;

public class Doctor extends User {

    DoctorActivity activity;

    Doctor(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender, DoctorActivity activity) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
        this.activity = activity;
    }
}
