package ch.epfl.sweng.vanjel;

import com.google.firebase.database.FirebaseDatabase;

import org.mockito.Mock;

public final class DataBaseBackend {

    // Objects in the database
    public final static Patient defaultPatient = new Patient("patient@test.com",
            "Default","Patient","01/01/1970","DefaultStreet",
            "1","DefaultCity","DefaultCountry",Gender.Other);
    public final static Doctor defaultDoctor = new Doctor("doctor@test.com","Default",
            "Doctor","01/01/1970","DefaultStreet","1","DefaultCity",
            "DefaultCountry", Gender.Other, DoctorActivity.Generalist);

    //Objects that can be added to the database
    private static Patient addedPatient;
    private static Doctor addedDoctor;

    //Strings to determine the right IDs to use
    //Users
    public final static String DOCTOR_KEY = "Doctor";
    public final static String PATIENT_KEY = "Patient";
    //Patient Medical Info
    public final static String ALLERGY_KEY = "Allergy";
    public final static String CONDITION_KEY = "Condition";
    public final static String DRUG_KEY = "Drug";
    public final static String SMOKING_KEY = "Smoking";
    public final static String SUBSTANCE_KEY = "Substance";
    public final static String SURGERY_KEY = "Surgery";


    //All mocked objects
    @Mock
    private FirebaseDatabase mockDB;
}
