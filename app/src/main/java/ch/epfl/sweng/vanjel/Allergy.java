package ch.epfl.sweng.vanjel;

public class Allergy extends Info{

    String allergy;

    public Allergy() {}

    public Allergy(String allergy) {
        this.allergy = allergy;
    }

    public String getAllergy() {
        return allergy;
    }

    String getAndroidInfo(){
        return getAllergy();
    }
}
