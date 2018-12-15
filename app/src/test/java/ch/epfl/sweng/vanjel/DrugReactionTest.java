package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.patientInfo.DrugReaction;

import static org.junit.Assert.*;

public class DrugReactionTest {

    String drug;
    String reaction;

    @Test
    public void testEntity() {
        DrugReaction myDrugReaction = new DrugReaction(drug, reaction);

        assertEquals("Unexpected drug in myDrugReaction entity", drug, myDrugReaction.getDrug());
        assertEquals("Unexpected reaction in myDrugReaction entity", reaction, myDrugReaction.getReaction());
        assertEquals("Unexpected androidInfo in myDrugReaction entity", reaction, myDrugReaction.getAndroidInfo());

    }

    @Test
    public void testEntityEmptyConstructor() {
        DrugReaction myDrugReaction = new DrugReaction();

        myDrugReaction.setDrug(drug);
        myDrugReaction.setReaction(reaction);

        assertEquals("Unexpected drug in myDrugReaction entity", drug, myDrugReaction.getDrug());
        assertEquals("Unexpected reaction in myDrugReaction entity", reaction, myDrugReaction.getReaction());
        assertEquals("Unexpected androidInfo in myDrugReaction entity", reaction, myDrugReaction.getAndroidInfo());
    }

}