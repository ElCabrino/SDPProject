package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.patientInfo.Drug;

import static org.junit.Assert.assertEquals;

public class DrugTest {

    String drug = "Dafalgan";
    String dosage = "2";
    String frequency = "3";

    @Test
    public void testEntity() {

        Drug myTestDrug = new Drug(drug, dosage, frequency);

        assertEquals("Unexpected drug in myTestDrug entity", drug, myTestDrug.getDrug());
        assertEquals("Unexpected dosage in myTestDrug entity", dosage, myTestDrug.getDosage());
        assertEquals("Unexpected frequency in myTestDrug entity", frequency, myTestDrug.getFrequency());

    }

    @Test
    public void testEntityEmptyConstructor() {
        Drug myDrug = new Drug();

        myDrug.setDosage(dosage);
        myDrug.setDrug(drug);
        myDrug.setFrequency(frequency);

        assertEquals("Unexpected drug in myTestDrug entity", drug, myDrug.getDrug());
        assertEquals("Unexpected drug in myTestDrug entity", drug, myDrug.getAndroidInfo());
        assertEquals("Unexpected dosage in myTestDrug entity", dosage, myDrug.getDosage());
        assertEquals("Unexpected frequency in myTestDrug entity", frequency, myDrug.getFrequency());


    }

}