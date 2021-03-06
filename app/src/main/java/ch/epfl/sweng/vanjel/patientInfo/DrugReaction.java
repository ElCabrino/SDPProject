package ch.epfl.sweng.vanjel.patientInfo;

/**
 * Class to represent a drug reaction.
 *
 * @author Nicolas BRANDT
 * @reviewer
 */
public class DrugReaction extends Info {

    private String drug, reaction;

    public DrugReaction() {}

    public DrugReaction(String drug, String reaction) {
        this.drug = drug;
        this.reaction = reaction;
    }

    public String getDrug() {
        return drug;
    }

    public String getReaction() {
        return reaction;
    }

    public void setDrug(String drug) { this.drug = drug; }

    public void setReaction(String reaction) { this.reaction = reaction; }


    @Override
    public String getAndroidInfo() {
        return getDrug();
    }
}
