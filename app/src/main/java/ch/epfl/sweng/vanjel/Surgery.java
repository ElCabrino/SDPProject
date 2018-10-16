package ch.epfl.sweng.vanjel;

public class Surgery {
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
}