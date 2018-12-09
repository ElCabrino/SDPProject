package ch.epfl.sweng.vanjel.model;

/**
 * @author Vincent CABRINI
 * @reviewer Luca JOSS
 */
public enum Gender {
    /**
     * Gender of the person
     */
    Male,
    Female,
    Other;//pour être politiquement correct :p

    @Override
    public String toString() {
        if (this == Gender.Male) {
            return "Male";
        }
        else if (this == Gender.Female) {
            return "Female";
        }
        else {
            return "Other";
        }
    }
}
