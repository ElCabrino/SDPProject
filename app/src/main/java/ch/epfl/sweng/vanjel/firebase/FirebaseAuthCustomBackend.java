package ch.epfl.sweng.vanjel.firebase;

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

/**
 * Since this class is mostly mock initialisation for the tests, it is ignore by code climate.
 */

/**
 * @author Luca JOSS
 * @reviewer Vincent CABRINI
 */
public class FirebaseAuthCustomBackend {

    private static boolean mockPatient = true;
    private static boolean nullUser = false;
    private static boolean shouldFail = false;


    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private static FirebaseUser mockUser;

    @Mock
    private Task<AuthResult> mockLoginTask;
    @Mock
    private Task<AuthResult> mockUserTask;

    public static void setMockPatient(boolean b) {
        mockPatient = b;
    }

    public static void setNullUser(boolean b) {
        nullUser = b;
    }

    public static void setShouldFail(boolean b) {
        shouldFail = b;
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
        initUserTask();
//        initListenerAuth();
        return mockAuth;
    }

    private void initAuthBehaviour() {
        if (!nullUser) {
            when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        } else {
            when(mockAuth.getCurrentUser()).thenReturn(null);
        }
        when(mockAuth.signInWithEmailAndPassword(any(String.class), any(String.class))).thenReturn(mockLoginTask);
        when(mockAuth.createUserWithEmailAndPassword(any(String.class), any(String.class))).thenReturn(mockLoginTask);
    }

    private void initMockPatient() {
        when(mockUser.getUid()).thenReturn("patientid1");
    }

    private void initMockDoctor() {
        when(mockUser.getUid()).thenReturn("doctorid1");
    }

    private void initUserTask() {
        when(mockUserTask.isSuccessful()).thenReturn(!shouldFail);
    }

    private void initListenerAuth() {
        doAnswer((new Answer<OnCompleteListener<AuthResult>>() {
            @Override
            public OnCompleteListener<AuthResult> answer(InvocationOnMock invocation) throws Throwable {
                OnCompleteListener<AuthResult> listener = (OnCompleteListener<AuthResult>) invocation.getArguments()[0];
                listener.onComplete(mockUserTask);
                return listener;
            }
        })).when(mockLoginTask).addOnCompleteListener(any(OnCompleteListener.class));

        doAnswer((new Answer<OnCompleteListener<AuthResult>>() {
            @Override
            public OnCompleteListener<AuthResult> answer(InvocationOnMock invocation) throws Throwable {
                OnCompleteListener<AuthResult> listener = (OnCompleteListener<AuthResult>) invocation.getArguments()[1];
                listener.onComplete(mockUserTask);
                return listener;
            }
        })).when(mockLoginTask).addOnCompleteListener(any(Activity.class), any(OnCompleteListener.class));
    }
}