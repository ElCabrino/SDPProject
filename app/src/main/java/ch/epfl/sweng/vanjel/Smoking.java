package ch.epfl.sweng.vanjel;

public class Smoking {

    private String drinkingAmount;

    public Smoking() {}

    public Smoking(String id, String amount) {
        this.drinkingAmount = amount;
    }

    public String getAmount() {
        return drinkingAmount;
    }
}
