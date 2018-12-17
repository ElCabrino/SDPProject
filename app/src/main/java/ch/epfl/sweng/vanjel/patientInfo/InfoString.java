package ch.epfl.sweng.vanjel.patientInfo;

/**
 * Class to represent medical informations that require only a single information.
 *
 * @author Vincent CABRINI
 * @reviewer Aslam CADER
 */
public class InfoString extends Info{

    String info;

    public InfoString() {}

    public InfoString(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) { this.info = info; }

    public String getAndroidInfo(){
        return getInfo();
    }
}

