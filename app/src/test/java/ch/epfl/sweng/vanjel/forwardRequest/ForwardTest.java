package ch.epfl.sweng.vanjel.forwardRequest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Forward class
 *
 * @author Aslam CADER
 * @author Etienne CAQUOT
 * @reviewer
 *
 */
public class ForwardTest {

    String patient = "patient";
    String doctor1UID = "doctor1UID";
    String doctor2UID = "doctor2UID";
    String doctor1name = "doctor1name";
    String doctor2name = "doctor2name";

    @Test
    public void testEntity(){

        Forward forward = new Forward(patient, doctor1UID, doctor2UID, doctor1name, doctor2name);

        assertEquals("Unexpected patient in entity", patient, forward.getPatient());
        assertEquals("Unexpected doctor1UID in entity", doctor1UID, forward.getDoctor1UID());
        assertEquals("Unexpected doctor2UID in entity", doctor2UID, forward.getDoctor2UID());
        assertEquals("Unexpected doctor1name in entity", doctor1name, forward.getDoctor1name());
        assertEquals("Unexpected doctor2name in entity", doctor2name, forward.getDoctor2name());


    }

    @Test
    public void testEntityEmptyConstructor(){

        Forward forward = new Forward();

        forward.setPatient(patient);
        forward.setDoctor1UID(doctor1UID);
        forward.setDoctor2UID(doctor2UID);
        forward.setDoctor1name(doctor1name);
        forward.setDoctor2name(doctor2name);

        assertEquals("Unexpected patient in entity", patient, forward.getPatient());
        assertEquals("Unexpected doctor1UID in entity", doctor1UID, forward.getDoctor1UID());
        assertEquals("Unexpected doctor2UID in entity", doctor2UID, forward.getDoctor2UID());
        assertEquals("Unexpected doctor1name in entity", doctor1name, forward.getDoctor1name());
        assertEquals("Unexpected doctor2name in entity", doctor2name, forward.getDoctor2name());

    }

    @Test
    public void testNotEqualsForNull(){
        Forward forward = new Forward(patient, doctor1UID, doctor2UID, doctor1name, doctor2name);
        assertFalse(forward.equals(null));
    }

    @Test
    public void testEqualsWorkForSameForward(){
        Forward forward = new Forward(patient, doctor1UID, doctor2UID, doctor1name, doctor2name);
        assertTrue(forward.equals(new Forward(patient,doctor1UID,doctor2UID,doctor1name,doctor2name)));
    }

    @Test
    public void testNotEqualsForDifferentForward(){
        Forward forward = new Forward(patient, doctor1UID, doctor2UID, doctor1name, doctor2name);
        assertFalse(forward.equals(new Forward(patient,doctor1UID,"not same uid",doctor1name,doctor2name)));
    }
}