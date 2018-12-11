package ch.epfl.sweng.vanjel;

import org.junit.Test;

import java.util.Calendar;

import ch.epfl.sweng.vanjel.doctorCalendar.DoctorCalendarItem;
import ch.epfl.sweng.vanjel.models.Gender;
import ch.epfl.sweng.vanjel.models.Patient;

import static org.junit.Assert.assertEquals;

public class DoctorCalendarItemTest {
    @Test
    public void DocCalItemTest() {
        String date = Calendar.getInstance().getTime().toString();
        Patient Patient = new Patient("patient@test.com","PatientName",
                "PatientSurname","01/01/1970","Av de Lausanne","1","Lausanne",
                "Switzerland",Gender.Other);
        DoctorCalendarItem item = new DoctorCalendarItem(date,Patient);

        assertEquals("date does not match",item.getDate(),date);
        assertEquals("patient does not match",item.getPatient(),Patient);
    }
}
