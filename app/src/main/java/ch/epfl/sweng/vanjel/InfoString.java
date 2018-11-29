package ch.epfl.sweng.vanjel;

/**
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

    String getAndroidInfo(){
        return getInfo();
    }
}

