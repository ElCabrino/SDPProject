package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.models.Gender;
import ch.epfl.sweng.vanjel.models.Patient;

import static org.junit.Assert.assertEquals;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class PatientTest {

    @Test
    public void emptyConstructorEntity(){
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
        Patient testUser2 = new Patient();


        // Setters test
        testUser2.setEmail(email);
        testUser2.setFirstName(firstName);
        testUser2.setLastName(lastName);
        testUser2.setBirthday(birthday);
        testUser2.setStreet(street);
        testUser2.setStreetNumber(streetNumber);
        testUser2.setCity(city);
        testUser2.setCountry(country);
        testUser2.setGender(gender);

        assertEquals("Unexpected email in entity 2", email, testUser2.getEmail());
        assertEquals("Unexpected first name in entity 2", firstName, testUser2.getFirstName());
        assertEquals("Unexpected last name in entity 2", lastName, testUser2.getLastName());
        assertEquals("Unexpected birthday in entity 2", birthday, testUser2.getBirthday());
        assertEquals("Unexpected street in entity 2", street, testUser2.getStreet());
        assertEquals("Unexpected streetNumber in entity 2", streetNumber, testUser2.getStreetNumber());
        assertEquals("Unexpected city in entity 2", city, testUser2.getCity());
        assertEquals("Unexpected country in entity 2", country, testUser2.getCountry());
        assertEquals("Unexpected gender in entity 2", gender, testUser2.getGender());

    }

}