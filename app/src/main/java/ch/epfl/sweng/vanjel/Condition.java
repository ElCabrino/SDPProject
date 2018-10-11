package ch.epfl.sweng.vanjel;

public class Condition {
    String id;
    String condition;

    public Condition() {}

    public Condition(String conditionId, String conditionCondition) {
        this.id = conditionId;
        this.condition = conditionCondition;
    }

    public String getId() {
        return id;
    }

    public String getCondition() {
        return condition;
    }
}
