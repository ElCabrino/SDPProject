package ch.epfl.sweng.vanjel;

public enum DoctorActivity {
    ENUM1("Generalist"),
    ENUM2("Dentist"),
    ENUM3("Physiotherapist"),
    ENUM4("Psychologist"),
    ENUM5("Orthopedist"),
    ENUM6("Ophthalmologist");

    private String friendlyName;

    DoctorActivity(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }
}