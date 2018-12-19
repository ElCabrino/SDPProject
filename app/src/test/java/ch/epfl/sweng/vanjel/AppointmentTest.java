package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.appointment.Appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AppointmentTest {

    private final String day = "Monday";
    private final String hour = "03:30";
    private final String doctorUid = "dId";
    private final String patientUid = "pId";

    //    @Test
//    public void getterTestConstructor1() {
//        Appointment a = new Appointment(day, hour, duration, doctorUid, patientUid);
//        assertEquals(day, a.getDay());
//        assertEquals(hour, a.getHour());
//        assertEquals(duration, a.getDuration());
//        assertEquals(doctorUid, a.getDoctorUid());
//        assertEquals(patientUid, a.getPatientUid());
//    }

    @Test
    public void getterTestConstructor2() {
        Appointment a = new Appointment(day, hour, doctorUid, patientUid);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertNull(a.getDuration());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
    }

    @Test
    public void setterTest() {
        Appointment a = new Appointment(day, hour, doctorUid, patientUid);
        String day2 = "Tuesday";
        a.setDay(day2);
        String hour2 = "03:00";
        a.setHour(hour2);
        Integer duration2 = 10;
        a.setDuration(duration2);
        String doctorUid2 = "dId2";
        a.setDoctorUid(doctorUid2);
        String patientUid2 = "pId2";
        a.setPatientUid(patientUid2);
        assertEquals(day2, a.getDay());
        assertEquals(hour2, a.getHour());
        assertEquals(duration2, a.getDuration());
        assertEquals(doctorUid2, a.getDoctorUid());
        assertEquals(patientUid2, a.getPatientUid());
    }

    @Test
    public void getterTestConstructor3() {
        String appointmentID = "aptID";
        Appointment a = new Appointment(day, hour, doctorUid, patientUid, appointmentID);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
        assertEquals(appointmentID, a.getAppointmentID());
        a.setAppointmentID("aptID2");
        assertEquals("aptID2", a.getAppointmentID());
    }

    @Test
    public void getterTestConstructor4() {
        Integer duration = 0;
        Appointment a = new Appointment(day, hour, duration, doctorUid, patientUid);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
        assertEquals(duration, a.getDuration());
    }




}
