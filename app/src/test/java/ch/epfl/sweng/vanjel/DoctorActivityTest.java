package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.models.DoctorActivity;

import static org.junit.Assert.assertEquals;

public class DoctorActivityTest {

    @Test
    public void testToString() {

        DoctorActivity generalist = DoctorActivity.Generalist;
        DoctorActivity dentist = DoctorActivity.Dentist;
        DoctorActivity physiotherapist = DoctorActivity.Physiotherapist;
        DoctorActivity psychologist = DoctorActivity.Psychologist;
        DoctorActivity orthopedist = DoctorActivity.Orthopedist;
        DoctorActivity ophthalmologist = DoctorActivity.Ophthalmologist;

        assertEquals("Unexpected DoctorActivity generalist toString", "Generalist", generalist.toString());
        assertEquals("Unexpected DoctorActivity dentist toString", "Dentist", dentist.toString());
        assertEquals("Unexpected DoctorActivity physiotherapist toString", "Physiotherapist", physiotherapist.toString());
        assertEquals("Unexpected DoctorActivity psychologist toString", "Psychologist", psychologist.toString());
        assertEquals("Unexpected DoctorActivity orthopedist toString", "Orthopedist", orthopedist.toString());
        assertEquals("Unexpected DoctorActivity ophthalmologist toString", "Ophthalmologist", ophthalmologist.toString());
    }
}
