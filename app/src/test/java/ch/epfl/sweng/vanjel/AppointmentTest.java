package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.appointment.Appointment;

import static org.junit.Assert.assertEquals;

public class AppointmentTest {

    private String day = "Monday";
    private String hour = "03:30";
    private Integer duration = 0;
    private String doctorUid = "dId";
    private String patientUid = "pId";

    private String day2 = "Tuesday";
    private String hour2 = "03:00";
    private Integer duration2 = 10;
    private String doctorUid2 = "dId2";
    private String patientUid2 = "pId2";
    private String appointmentID = "aptID";

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
        assertEquals(null, a.getDuration());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
    }

    @Test
    public void setterTest() {
        Appointment a = new Appointment(day, hour, doctorUid, patientUid);
        a.setDay(day2);
        a.setHour(hour2);
        a.setDuration(duration2);
        a.setDoctorUid(doctorUid2);
        a.setPatientUid(patientUid2);
        assertEquals(day2, a.getDay());
        assertEquals(hour2, a.getHour());
        assertEquals(duration2, a.getDuration());
        assertEquals(doctorUid2, a.getDoctorUid());
        assertEquals(patientUid2, a.getPatientUid());
    }

    @Test
    public void getterTestConstructor3() {
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
        Appointment a = new Appointment(day, hour, duration, doctorUid, patientUid);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
        assertEquals(duration, a.getDuration());
    }




}
