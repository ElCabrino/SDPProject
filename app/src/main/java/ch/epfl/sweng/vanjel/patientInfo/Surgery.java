package ch.epfl.sweng.vanjel.patientInfo;
/**
 * Class to represent a surgery.
 *
 * @author Nicolas BRANDT
 * @reviewer Vincent CABRINI
 */
public class Surgery extends Info{
    private String type;
    private String year;

    public Surgery() {}

    public Surgery(String type, String year) {
        this.type = type;
        this.year = year;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String getAndroidInfo() {
        return getType();
    }
}