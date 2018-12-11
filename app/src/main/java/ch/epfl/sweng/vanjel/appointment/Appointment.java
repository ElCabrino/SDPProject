package ch.epfl.sweng.vanjel.appointment;

/**
 * Due to unreasonable code climate duplicate issue with Appointment class
 * This class is ignored by code climate.
 */

/**
 * @author Vincent CABRINI
 * @reviewer Luca Joss
 */
public class Appointment {
    private String day;
    private String hour;
    private Integer duration;
    private String doctorUid;
    private String patientUid;
    private String appointmentID;

    public Appointment(String day, String hour, String doctorUid, String patientUid, String appointmentID) {
        this.day = day;
        this.hour = hour;
        this.doctorUid = doctorUid;
        this.patientUid = patientUid;
        this.appointmentID = appointmentID;
    }

    public Appointment(String day, String hour, Integer duration, String doctorUid, String patientUid) {
        this.patientUid = patientUid;
        this.duration = duration;
        this.day = day;
        this.hour = hour;
        this.doctorUid = doctorUid;
    }

    public Appointment(String day, String hour, String doctorUid, String patientUid) {
        this.day = day;
        this.hour = hour;
        this.doctorUid = doctorUid;
        this.patientUid = patientUid;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getDoctorUid() {
        return doctorUid;
    }

    public void setDoctorUid(String doctorUid) {
        this.doctorUid = doctorUid;
    }

    public String getPatientUid() {
        return patientUid;
    }

    public void setPatientUid(String patientUid) {
        this.patientUid = patientUid;
    }

    public String getAppointmentID() { return appointmentID; }

    public void setAppointmentID(String appointmentID) { this.appointmentID = appointmentID; }
}
