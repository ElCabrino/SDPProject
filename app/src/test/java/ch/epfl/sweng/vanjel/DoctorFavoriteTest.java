package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.favorite.DoctorFavorite;

import static org.junit.Assert.assertEquals;

public class DoctorFavoriteTest {

    String docID = "docID";
    String firstName = "Vincent";
    String lastName  = "Cabrini";
    String street = "Route de Drize";
    String  number = "11";
    String city = "Carouge";
    String country = "Switzerland";
    String profession = "Generalist";

    @Test
    public void testGetterSetter() {
        DoctorFavorite d = new DoctorFavorite(docID,firstName,lastName, street,number,city,  country, profession);
        assertEquals(docID,d.getDoctorID());
        assertEquals(firstName, d.getFirstName());
        assertEquals(lastName,d.getLastName());
        assertEquals(street, d.getStreet());
        assertEquals(number, d.getStreetNumber());
        assertEquals(city, d.getCity());
        assertEquals(country, d.getCountry());
        assertEquals(profession,d.getProfession());
        d.setDoctorID(docID+"1");
        d.setFirstName(firstName+"1");
        d.setLastName(lastName+"1");
        d.setStreet(street+"1");
        d.setStreetNumber(number+"1");
        d.setCity(city+"1");
        d.setCountry(country+"1");
        d.setProfession(profession+"1");
        assertEquals(docID+"1",d.getDoctorID());
        assertEquals(firstName+"1", d.getFirstName());
        assertEquals(lastName+"1",d.getLastName());
        assertEquals(street+"1", d.getStreet());
        assertEquals(number+"1", d.getStreetNumber());
        assertEquals(city+"1", d.getCity());
        assertEquals(country+"1", d.getCountry());
        assertEquals(profession+"1",d.getProfession());
    }
}
