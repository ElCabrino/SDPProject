package ch.epfl.sweng.vanjel;

import org.junit.Test;

import static org.junit.Assert.*;

public class PtPersonalAppointmentTest {

    String doctor = "doctorUID";
    String location = "Location";
    String date = "Mon 26 Nov 2018";
    String time = "17:00";
    String duration = "30";
    // 0 = appointment confirmed, 1 = pending
    Boolean pendingStatus = true;
    @Test
    public void testEntityEmptyConstructor() {
        PtPersonalAppointment appointment = new PtPersonalAppointment();

        appointment.setDoctor(doctor);
        appointment.setLocation(location);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDuration(duration);
        appointment.setPendingStatus(pendingStatus);

        assertEquals(appointment.getDoctor(), doctor);
        assertEquals(appointment.getLocation(), location);
        assertEquals(appointment.getDate(), date);
        assertEquals(appointment.getTime(), time);
        assertEquals(appointment.getDuration(), duration);
        assertEquals(appointment.getPendingStatus(), pendingStatus);
    }
}