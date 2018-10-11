package ch.epfl.sweng.vanjel;

public class Allergy {
    String id;
    String allergy;

    public Allergy() {}

    public Allergy(String id, String allergy) {
        this.id = id;
        this.allergy = allergy;
    }

    public String getId() {
        return id;
    }

    public String getAllergy() {
        return allergy;
    }
}
