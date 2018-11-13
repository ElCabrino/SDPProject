package ch.epfl.sweng.vanjel;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class FirebaseAuthCustomBackend {

    static boolean mockPatient = true;

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @Mock
    private Task<AuthResult> mockRegistrationTask;
    @Mock
    Task<AuthResult> mockCompleteTask;

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
        initListenerAuth();
        return mockAuth;
    }

    private void initAuthBehaviour() {
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockAuth.createUserWithEmailAndPassword(any(String.class), any(String.class))).thenReturn(mockRegistrationTask);
    }

    public void initMockPatient() {
        when(mockUser.getUid()).thenReturn("patientid1");
    }

    public void initMockDoctor() {
        when(mockUser.getUid()).thenReturn("doctorid1");
    }

    private void initListenerAuth() {
        when(mockRegistrationTask.addOnCompleteListener(any(Activity.class), any(OnCompleteListener.class))).thenReturn(mockCompleteTask);
    }
}