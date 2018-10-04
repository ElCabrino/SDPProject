package ch.epfl.sweng.vanjel;

public class Doctor extends User {
    Doctor(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
    }
}
