package ch.epfl.sweng.vanjel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class FirebaseAuthCustomBackend {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

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
        initMockUser();
        return mockAuth;
    }

    private void initAuthBehaviour() {
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
    }

    private void initMockUser() {
        when(mockUser.getUid()).thenReturn("patientid1");
    }
}
