package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.patientAppointment.PtPersonalAppointment;

import static org.junit.Assert.*;

public class PtPersonalAppointmentTest {

    private final String doctor = "doctorUID";
    private final String location = "Location";
    private final String date = "Mon 26 Nov 2018";
    private final String time = "17:00";
    private final String duration = "30";
    // 0 = appointment confirmed, 1 = pending
    private final Boolean pendingStatus = true;
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