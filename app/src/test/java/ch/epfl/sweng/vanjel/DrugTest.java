package ch.epfl.sweng.vanjel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DrugTest {

    @Test
    public void testEntity() {

        String drug = "Dafalgan";
        String dosage = "2";
        String frequency = "3";

        Drug myTestDrug = new Drug(drug, dosage, frequency);

        assertEquals("Unexpected drug in myTestDrug entity", drug, myTestDrug.getDrug());
        assertEquals("Unexpected dosage in myTestDrug entity", dosage, myTestDrug.getDosage());
        assertEquals("Unexpected frequency in myTestDrug entity", frequency, myTestDrug.getFrequency());

    }

}