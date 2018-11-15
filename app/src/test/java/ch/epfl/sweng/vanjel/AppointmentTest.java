package ch.epfl.sweng.vanjel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppointmentTest {

    private String day = "Monday";
    private String hour = "03:30";
    private Integer duration = 0;
    private String doctorUid = "dId";
    private String patientUid = "pId";

    @Test
    public void setterGetterTestConstructor1() {
        Appointment a = new Appointment(day, hour, duration, doctorUid, patientUid);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertEquals(duration, a.getDuration());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
    }

    @Test
    public void setterGetterTestConstructor2() {
        Appointment a = new Appointment(day, hour, doctorUid, patientUid);
        assertEquals(day, a.getDay());
        assertEquals(hour, a.getHour());
        assertEquals(null, a.getDuration());
        assertEquals(doctorUid, a.getDoctorUid());
        assertEquals(patientUid, a.getPatientUid());
    }



}
