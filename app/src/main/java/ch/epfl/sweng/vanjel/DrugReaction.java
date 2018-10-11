package ch.epfl.sweng.vanjel;

public class DrugReaction {
    String id;
    String drug;
    String reaction;

    DrugReaction() {}

    public DrugReaction(String id, String drug, String reaction) {
        this.id = id;
        this.drug = drug;
        this.reaction = reaction;
    }

    public String getId() {
        return id;
    }

    public String getDrug() {
        return drug;
    }

    public String getReaction() {
        return reaction;
    }
}
