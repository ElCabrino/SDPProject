package ch.epfl.sweng.vanjel;

public class Smoking {

    private String id;
    private String amount;

    public Smoking() {}

    public Smoking(String id, String amount) {
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
