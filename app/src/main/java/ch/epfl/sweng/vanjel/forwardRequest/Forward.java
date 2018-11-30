package ch.epfl.sweng.vanjel.forwardRequest;

public class Forward {

    // The doctor1 send the patient to the doctor2
    private String patient, doctor1UID, doctor2UID, doctor2name, doctor1name;

    public Forward(String patient, String doctor1UID, String doctor2UID, String doctor1name, String doctor2name){
        this.patient = patient;
        this.doctor1UID = doctor1UID;
        this.doctor2UID = doctor2UID;
        this.doctor1name = doctor1name;
        this.doctor2name = doctor2name;
    }

    // for Firebase
    public Forward() {}

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getDoctor1UID() {
        return doctor1UID;
    }

    public void setDoctor1UID(String doctor1UID) {
        this.doctor1UID = doctor1UID;
    }

    public String getDoctor2UID() {
        return doctor2UID;
    }

    public void setDoctor2UID(String doctor2UID) {
        this.doctor2UID = doctor2UID;
    }

    public String getDoctor2name() {
        return doctor2name;
    }

    public void setDoctor2name(String doctor2name) {
        this.doctor2name = doctor2name;
    }

    public String getDoctor1name() {
        return doctor1name;
    }

    public void setDoctor1name(String doctor1name) {
        this.doctor1name = doctor1name;
    }


    @Override
    public String toString(){
        return "[" + patient + ", " + doctor1UID + ", " + doctor2UID + ", " + doctor1name + ", " + doctor2name + "]";
    }

}
