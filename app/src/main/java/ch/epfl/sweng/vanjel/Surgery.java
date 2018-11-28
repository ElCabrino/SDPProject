package ch.epfl.sweng.vanjel;
/**
 * @author Nicolas BRANDT
 * @reviewer Vincent CABRINI
 */
public class Surgery extends Info{
    String type;
    String year;

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
    String getAndroidInfo() {
        return getType();
    }
}