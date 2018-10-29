package ch.epfl.sweng.vanjel;

public class PtPersonalAppointment {
    String doctor;
    String location;
    String date;
    String time;

    PtPersonalAppointment() {}

    public PtPersonalAppointment(String doctor, String location, String date, String time) {
        this.doctor = doctor;
        this.location = location;
        this.date = date;
        this.time = time;
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
}
