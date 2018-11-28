package ch.epfl.sweng.vanjel;

/**
 * @author Nicolas BRANDT
 * @reviewer
 */
public class Drug extends Info{

    private String drug, dosage, frequency;

    public Drug() {}

    public Drug(String drug, String dosage, String frequency) {
        this.drug = drug;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public String getDrug() {
        return drug;
    }

    public String getDosage() {
        return dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setDrug(String drug) { this.drug = drug; }

    public void setDosage(String dosage) { this.dosage = dosage; }

    public void setFrequency(String frequency) { this.frequency = frequency; }

    @Override
    String getAndroidInfo() {
        return getDrug();
    }

}
