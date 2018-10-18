package ch.epfl.sweng.vanjel;

public class InfoString extends Info{

    String info;

    public InfoString() {}

    public InfoString(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    String getAndroidInfo(){
        return getInfo();
    }
}

