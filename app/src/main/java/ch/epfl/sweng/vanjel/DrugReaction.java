package ch.epfl.sweng.vanjel;

public class DrugReaction extends Info{
    String drug;
    String reaction;

    DrugReaction() {}

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
    String getAndroidInfo() {
        return getReaction();
    }
}
