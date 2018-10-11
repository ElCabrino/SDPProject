package ch.epfl.sweng.vanjel;

public class Substance {
    private String id;
    private String substance;

    public Substance () {}

    public Substance(String id, String substance) {
        this.id = id;
        this.substance = substance;
    }

    public String getId() {
        return id;
    }

    public String getSubstance() {
        return substance;
    }
}
