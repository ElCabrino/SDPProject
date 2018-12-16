package ch.epfl.sweng.vanjel.patientInfo;

/**
 * Base class for patient informations.
 *
 * @author Vincent CABRINI
 * @reviewer Nicolas Brandt
 */
public abstract class Info {
    /**
     * Method to return the string used for firebase.
     *
     * @return the string used for firebase storing and retrieving.
     */
    abstract String getAndroidInfo();
}
