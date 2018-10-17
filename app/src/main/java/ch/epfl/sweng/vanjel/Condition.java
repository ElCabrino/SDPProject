package ch.epfl.sweng.vanjel;

public class Condition extends Info {

    String condition;

    public Condition() {}

    public Condition(String conditionCondition) {
        this.condition = conditionCondition;
    }

    public String getCondition() {
        return condition;
    }

    String getAndroidInfo() {return getCondition();}
}
