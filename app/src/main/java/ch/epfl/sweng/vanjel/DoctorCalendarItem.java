package ch.epfl.sweng.vanjel;
/**
 * Nicolas?? Etienne??
 * @author
 * @reviewer
 */
public class DoctorCalendarItem {

    private String date;
    private Patient patient;

    public DoctorCalendarItem(String dt, Patient pt) {
        this.date = dt;
        this.patient = pt;
    }


    public String getDate() {
        return date;
    }

    public Patient getPatient() {
        return patient;
    }
}
