package ch.epfl.sweng.vanjel.forwardRequest;

public class Forward {

    // The doctor1 send the patient to the doctor2
    private String patient, doctor1, doctor2;

    public Forward(String patient, String doctor1, String doctor2){
        this.patient = patient;
        this.doctor1 = doctor1;
        this.doctor2 = doctor2;
    }

    // for Firebase
    public Forward() {}


    public String getPatient() { return this.patient; }

    public String getDoctor1() { return this.doctor1; }

    public String getDoctor2() { return this.doctor2; }

    public void setPatient(String patient) { this.patient = patient;}

    public void setDoctor1(String doctor1) { this.doctor1 = doctor1; }

    public void setDoctor2(String doctor2) { this.doctor2 = doctor2; }


    @Override
    public String toString(){
        return "[" + patient + ", " + doctor1 + ", " + doctor2 + "]";
    }

}
