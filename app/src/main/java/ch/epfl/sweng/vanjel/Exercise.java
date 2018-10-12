package ch.epfl.sweng.vanjel;

public class Exercise {

    private String id;
    private String amount;

    public Exercise() {}

    public Exercise(String id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getAmount() {
        return amount;
    }
}
