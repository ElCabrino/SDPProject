package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.models.DoctorActivity;
import ch.epfl.sweng.vanjel.models.Gender;
import ch.epfl.sweng.vanjel.models.Patient;

import static org.junit.Assert.assertEquals;

/**
    Test class for User class

    author: Aslam CADER
    reviewer:
 **/

public class UserTest {

    /**
     * Tests that the constructors and getters/setters work as intended
     */
    @Test
    public void testEntity() {
        String email = "user@test.ch";
        String firstName = "John";
        String lastName = "Smith";
        String birthday = "27/09/2018";
        String street = "Best avenue";
        String streetNumber = "42";
        String city = "Gaillard";
        String country = "EPFL Land";
        Gender gender = Gender.Male;


        // Construct entity with full constructor call
        Patient testUser = new Patient(email, firstName, lastName, birthday, street,
                streetNumber, city, country, gender);

        // checking the getters
        assertEquals("Unexpected email in entity", email, testUser.getEmail());
        assertEquals("Unexpected first name in entity", firstName, testUser.getFirstName());
        assertEquals("Unexpected last name in entity", lastName, testUser.getLastName());
        assertEquals("Unexpected birthday in entity", birthday, testUser.getBirthday());
        assertEquals("Unexpected street in entity", street, testUser.getStreet());
        assertEquals("Unexpected streetNumber in entity", streetNumber, testUser.getStreetNumber());
        assertEquals("Unexpected city in entity", city, testUser.getCity());
        assertEquals("Unexpected country in entity", country, testUser.getCountry());
        assertEquals("Unexpected gender in entity", gender, testUser.getGender());
        assertEquals("error in to string",firstName+" "+lastName,testUser.toString());


        // setter test
        // creating variables for it
        DoctorActivity activity = DoctorActivity.Generalist;

        String email2 = "user2@test.ch";
        String firstName2 = "Elon";
        String lastName2 = "Musk";
        String birthday2 = "01/01/2018";
        String street2 = "Best Regards";
        String streetNumber2 = "97";
        String city2 = "Tesla";
        String country2 = "US";
        Gender gender2 = Gender.Female;




        Doctor testUser2 = new Doctor(email, firstName, lastName, birthday, street,
                streetNumber, city, country, gender, activity);

        // Setters test
        testUser2.setEmail(email2);
        testUser2.setFirstName(firstName2);
        testUser2.setLastName(lastName2);
        testUser2.setBirthday(birthday2);
        testUser2.setStreet(street2);
        testUser2.setStreetNumber(streetNumber2);
        testUser2.setCity(city2);
        testUser2.setCountry(country2);
        testUser2.setGender(gender2);

        assertEquals("Unexpected email in entity 2", email2, testUser2.getEmail());
        assertEquals("Unexpected first name in entity 2", firstName2, testUser2.getFirstName());
        assertEquals("Unexpected last name in entity 2", lastName2, testUser2.getLastName());
        assertEquals("Unexpected birthday in entity 2", birthday2, testUser2.getBirthday());
        assertEquals("Unexpected street in entity 2", street2, testUser2.getStreet());
        assertEquals("Unexpected streetNumber in entity 2", streetNumber2, testUser2.getStreetNumber());
        assertEquals("Unexpected city in entity 2", city2, testUser2.getCity());
        assertEquals("Unexpected country in entity 2", country2, testUser2.getCountry());
        assertEquals("Unexpected gender in entity 2", gender2, testUser2.getGender());



    }



}