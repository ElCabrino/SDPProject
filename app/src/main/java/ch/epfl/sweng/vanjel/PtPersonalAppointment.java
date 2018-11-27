package ch.epfl.sweng.vanjel;

public class PtPersonalAppointment {
    String doctor;
    String location;
    String date;
    String time;
    String duration;
    // 0 = appointment confirmed, 1 = pending
    Boolean pendingStatus;

    PtPersonalAppointment() {}

    public PtPersonalAppointment(String doctor, String location, String date, String time, String duration,Boolean pendingStatus) {
        this.doctor = doctor;
        this.location = location;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.pendingStatus = pendingStatus;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public Boolean getPendingStatus() {return pendingStatus; }
}
