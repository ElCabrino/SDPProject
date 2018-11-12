package ch.epfl.sweng.vanjel;

import com.google.firebase.database.FirebaseDatabase;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public final class FirebaseCustomBackend {

    private final static Patient defPatient1 = new Patient("patient1@test.ch", "fn_ptest1", "ln_ptest1", "01/01/2001", "street_ptest1", "1", "city_ptest1", "country_ptest1", Gender.Male);
    private final static Patient defPatient2 = new Patient("patient2@test.ch", "fn_ptest2", "ln_ptest2", "02/02/2002", "street_ptest2", "2", "city_ptest2", "country_ptest2", Gender.Female);

    private final static Doctor defDoctor1 = new Doctor("doctor1@test.ch", "fn_dtest1", "ln_dtest1", "11/11/2011", "street_dtest1", "11", "city_dtest1", "country_ptest1", Gender.Male, DoctorActivity.Dentist);
    private final static Doctor defDoctor2 = new Doctor("doctor2@test.ch", "fn_dtest2", "ln_dtest2", "12/12/2012", "street_dtest2", "12", "city_dtest2", "country_ptest2", Gender.Female, DoctorActivity.Generalist);

    @Mock
    private FirebaseDatabase mockDB;

    private FirebaseCustomBackend() {}

    private static boolean isTestRunning() {
        boolean res;
        try {
            Class.forName("ch.epfl.sweng.vanjel.androidTest.TestIndicator");
            res = true;
        } catch (final Exception e) {
            res = false;
        }
        return res;
    }

    public static FirebaseDatabase getInstance() {
        if (isTestRunning()) {
            return new FirebaseCustomBackend().initMocks();
        } else {
            return FirebaseDatabase.getInstance();
        }
    }

    private FirebaseDatabase initMocks() {
        MockitoAnnotations.initMocks(this);
        return mockDB;
    }
}
