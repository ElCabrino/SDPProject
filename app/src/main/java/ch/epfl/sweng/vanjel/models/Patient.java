package ch.epfl.sweng.vanjel.models;

/**
 * A class to represent a patient inherits User
 *
 * @author Vincent CABRINI
 * @reviewer Luca JOSS
 */
public class Patient extends User {

    public Patient(String email, String firstName, String lastName, String birthday, String street, String streetNumber, String city, String country, Gender gender) {
        super(email, firstName, lastName, birthday, street, streetNumber, city, country, gender);
    }

    public Patient() {}
}
