package ch.epfl.sweng.vanjel.patientInfo;

/**
 * @author Vincent CABRINI
 * @reviewer Aslam CADER
 */
public class InfoString extends Info{

    private String info;

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

