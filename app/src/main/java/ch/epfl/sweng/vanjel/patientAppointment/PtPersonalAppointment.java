package ch.epfl.sweng.vanjel.patientAppointment;

/**
 * @author Nicolas BRANDT
 * @reviewer Aslam CADER
 */
public class PtPersonalAppointment {
    String doctor, location, date, time, duration;
    // 0 = appointment confirmed, 1 = pending
    Boolean pendingStatus;

    public PtPersonalAppointment() {}

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

    public void setDoctor(String doctor) { this.doctor = doctor; }

    public String getLocation() { return location;   }

    public void setLocation(String location) { this.location = location; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public String getTime() {
        return time;
    }

    public void setTime(String time) { this.time = time;}

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) { this.duration = duration; }

    public Boolean getPendingStatus() {return pendingStatus; }

    public void setPendingStatus(Boolean pendingStatus) { this.pendingStatus = pendingStatus; }
}
