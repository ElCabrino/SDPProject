package ch.epfl.sweng.vanjel;

public class Substance extends Info{
    private String substance;

    public Substance () {}

    public Substance(String substance) {
        this.substance = substance;
    }

    public String getSubstance() {
        return substance;
    }

    @Override
    String getAndroidInfo() {
        return getSubstance();
    }
}
