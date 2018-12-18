package ch.epfl.sweng.vanjel;

import java.util.HashMap;

import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.models.DoctorActivity;
import ch.epfl.sweng.vanjel.models.Gender;
import ch.epfl.sweng.vanjel.searchDoctor.FilteredDoctorAdapter;

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



//    @Test
//    public void getItemCountTest() {
//
//        gp.put("a", d1);
//        gp.put("b", d2);
//        fd = new FilteredDoctorAdapter(null, gp, false, new HashMap<String, Object>(), new HashMap<String, Doctor>());
//        assertEquals(2, fd.getItemCount());
//    }
}