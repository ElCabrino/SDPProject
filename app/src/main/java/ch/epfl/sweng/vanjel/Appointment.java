package ch.epfl.sweng.vanjel;

public class Appointment {
    private String day;
    private String hour;
    private Integer duration;
    private String doctorUid;
    private String patientUid;

    public Appointment(String day, String hour, Integer duration, String doctorUid, String patientUid) {
        this.day = day;
        this.hour = hour;
        this.duration = duration;
        this.doctorUid = doctorUid;
        this.patientUid = patientUid;
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
}