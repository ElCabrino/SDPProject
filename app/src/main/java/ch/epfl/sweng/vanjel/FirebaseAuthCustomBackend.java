package ch.epfl.sweng.vanjel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class FirebaseAuthCustomBackend {

    static boolean mockPatient = true;

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    public static void setMockPatient(boolean b) {
        mockPatient = b;
    }

    private static boolean isTestRunning() {
        boolean res;
        try {
            Class.forName("android.support.test.espresso.Espresso");
            res = true;
        } catch (final Exception e) {
            res = false;
        }
        return res;
    }

    public static FirebaseAuth getInstance() {
        if (isTestRunning()) {
            return new FirebaseAuthCustomBackend().initMocks();
        } else {
            return FirebaseAuth.getInstance();
        }
    }

    private FirebaseAuth initMocks() {
        MockitoAnnotations.initMocks(this);
        initAuthBehaviour();
        if (mockPatient) {
            initMockPatient();
        } else {
            initMockDoctor();
        }
        return mockAuth;
    }

    private void initAuthBehaviour() {
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
    }

    public void initMockPatient() {
        when(mockUser.getUid()).thenReturn("patientid1");
    }

    public void initMockDoctor() {
        when(mockUser.getUid()).thenReturn("doctorid1");
    }
}
