package ch.epfl.sweng.vanjel;

import android.app.Activity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class FirebaseAuthCustomBackend {

    private static boolean mockPatient = true;
    private static boolean nullUser = false;

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private static FirebaseUser mockUser;

    @Mock
    private Task<AuthResult> mockRegistrationTask;
    @Mock
    private Task<AuthResult> mockCompleteTask;
    @Mock
    private Task<AuthResult> mockLoginTask;

    public static void setMockPatient(boolean b) {
        mockPatient = b;
    }

    static void setNullUser(boolean b) {
        nullUser = b;
        getInstance();
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
        if (!nullUser) {
            when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        } else {
            when(mockAuth.getCurrentUser()).thenReturn(null);
        }
        when(mockAuth.signInWithEmailAndPassword(any(String.class), any(String.class))).thenReturn(mockLoginTask);
    }

    public static void initMockPatient() {
        when(mockUser.getUid()).thenReturn("patientid1");
    }

    public static void initMockDoctor() {
        when(mockUser.getUid()).thenReturn("doctorid1");
    }

    private void initListenerAuth() {
        doAnswer((new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                return listener;
            }
        })).when(mockLoginTask).addOnCompleteListener(any(OnCompleteListener.class));
    }
}