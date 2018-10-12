package ch.epfl.sweng.vanjel;

public class Drinking {

    private String id;
    private String amount;

    public Drinking() {}

    public Drinking(String id, String amount) {
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
