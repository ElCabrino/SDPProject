package ch.epfl.sweng.vanjel;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class FilteredDoctorAdapterTest {

    Doctor d1 = new Doctor(
            "user@test.ch",
            "John",
            "Smith",
            "27/09/2018",
            "Best avenue",
            "42",
            "Gaillard",
            "EPFL Land",
            Gender.Male,
            DoctorActivity.Dentist);

    Doctor d2 = new Doctor(
            "user2@test.ch",
            "Bob",
            "Ron",
            "22/03/2012",
            "Best avenue2",
            "41",
            "Gaillardo",
            "UNIL Land",
            Gender.Male,
            DoctorActivity.Dentist);

    HashMap<String, Doctor> gp = new HashMap<>();
    FilteredDoctorAdapter fd;

    @Test
    public void getItemCountTest() {
        gp.put("a", d1);
        gp.put("b", d2);
        fd = new FilteredDoctorAdapter(null, gp);
        assertEquals(2, fd.getItemCount());
    }
}