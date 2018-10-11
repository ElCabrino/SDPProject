package ch.epfl.sweng.vanjel;

public class Surgery {
    String id;
    String type;
    String year;

    public Surgery() {}

    public Surgery(String id, String type, String year) {
        this.id = id;
        this.type = type;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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