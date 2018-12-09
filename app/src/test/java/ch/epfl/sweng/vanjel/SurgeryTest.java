package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.patientInfo.Surgery;

import static org.junit.Assert.assertEquals;
/**
 * @author Aslam CADER
 * @reviewer
 */
public class SurgeryTest {

    String typeSurgery = "eye laser";
    String year = "2018";

    @Test
    public void testEntityEmptyConstructor(){
        Surgery mySurgery = new Surgery();

        mySurgery.setType(typeSurgery);
        mySurgery.setYear(year);

        assertEquals("Unexpected type in Surgery entity without parameter constructor", typeSurgery, mySurgery.getType());
        assertEquals("Unexpected year in Surgery entity without parameter constructor", year, mySurgery.getYear());
        assertEquals("Unexpected android info in surgery ", typeSurgery, mySurgery.getAndroidInfo());
    }

    @Test
    public void testEntityParameterizedConstructor(){
        Surgery mySurgery = new Surgery(typeSurgery, year);


        assertEquals("Unexpected type in Surgery entity", typeSurgery, mySurgery.getType());
        assertEquals("Unexpected year in Surgery entity", year, mySurgery.getYear());


    }
}