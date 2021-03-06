package ch.epfl.sweng.vanjel.forwardRequest;

import android.support.annotation.NonNull;

/**
 * @author Aslam CADER
 * @author Etienne CAQUOT
 * @reviewer
 *
 * A class to represent a forwarded appointment
 */
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
    Forward() {}

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    String getDoctor1UID() {
        return doctor1UID;
    }

    void setDoctor1UID(String doctor1UID) {
        this.doctor1UID = doctor1UID;
    }

    String getDoctor2UID() {
        return doctor2UID;
    }

    void setDoctor2UID(String doctor2UID) {
        this.doctor2UID = doctor2UID;
    }

    String getDoctor2name() {
        return doctor2name;
    }

    void setDoctor2name(String doctor2name) {
        this.doctor2name = doctor2name;
    }

    String getDoctor1name() {
        return doctor1name;
    }

    void setDoctor1name(String doctor1name) {
        this.doctor1name = doctor1name;
    }


    @NonNull
    @Override
    public String toString(){
        return "[" + patient + ", " + doctor1UID + ", " + doctor2UID + ", " + doctor1name + ", " + doctor2name + "]";
    }

    public boolean equals(Forward f) {
        return f != null && f.getDoctor1UID().equals(doctor1UID) && f.getDoctor2UID().equals(doctor2UID) && f.getPatient().equals(patient);
    }
}
