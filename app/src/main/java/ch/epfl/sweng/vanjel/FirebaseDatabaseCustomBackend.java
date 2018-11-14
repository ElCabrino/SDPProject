package ch.epfl.sweng.vanjel;

import android.renderscript.Sampler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public final class FirebaseDatabaseCustomBackend {

    private final static String patient1ID = "patientid1";
    private final static Patient defPatient1 = new Patient("patient1@test.ch", "fn_ptest1", "ln_ptest1", "01/01/2001", "street_ptest1", "1", "city_ptest1", "country_ptest1", Gender.Male);

    private final static String doctor1ID = "doctorid1";
    private final static Doctor defDoctor1 = new Doctor("doctor1@test.ch", "fn_dtest1", "ln_dtest1", "11/11/2011", "street_dtest1", "11", "city_dtest1", "country_ptest1", Gender.Male, DoctorActivity.Dentist);

    private static boolean isCancelled = false;
    private static boolean shouldFail = false;

    @Mock
    private FirebaseDatabase mockDB;

    @Mock
    private DatabaseReference patientRef;
    @Mock
    private DatabaseReference doctorRef;
    @Mock
    private DatabaseReference patient1DB;
    @Mock
    private DatabaseReference doctor1DB;
    @Mock
    private DatabaseReference doctorAvailabilityRef;
    @Mock
    private DatabaseReference patientCategoryRef;
    @Mock
    private DatabaseReference patientSubCategoryRef;

    @Mock
    private DatabaseError patientError;
    @Mock
    private DatabaseError doctorError;

    @Mock
    private DataSnapshot patient1Snapshot;
    @Mock
    private DataSnapshot doctor1Snapshot;

    @Mock
    private Task<Void> updatePatientTask;
    @Mock
    private Task<Void> updateDoctorTask;
    @Mock
    private Task<Void> updateAvailabilityTask;
    @Mock
    private Task<Void> updateSuccessAvailabilityTask;
    @Mock
    private Task<Void> setValueInfoPatientTask;

    private FirebaseDatabaseCustomBackend() {}

    public static void setIsCancelled(boolean b) {
        isCancelled = b;
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

    public static FirebaseDatabase getInstance() {
        if (isTestRunning()) {
            return new FirebaseDatabaseCustomBackend().initMocks();
        } else {
            return FirebaseDatabase.getInstance();
        }
    }

    private FirebaseDatabase initMocks() {
        MockitoAnnotations.initMocks(this);
        initDatabase();
        initRefDatabase();
        initPatientSnapshots();
        initDoctorSnapshots();
        initDBListeners();
        initDoctorAvailabilityValidate();
        initPatientInfoMock();
        //initProfileListener();
        return mockDB;
    }

    private void initDatabase() {
        when(mockDB.getReference("Patient")).thenReturn(patientRef);
        when(mockDB.getReference("Doctor")).thenReturn(doctorRef);
    }

    private void initRefDatabase() {
        when(patientRef.child(patient1ID)).thenReturn(patient1DB);
        when(doctorRef.child(doctor1ID)).thenReturn(doctor1DB);

        when(patient1DB.updateChildren(any(Map.class))).thenReturn(updatePatientTask);
        when(doctor1DB.updateChildren(any(Map.class))).thenReturn(updateDoctorTask);
    }

    private void initPatientSnapshots() {
        when(patient1Snapshot.getValue(Patient.class)).thenReturn(defPatient1);
        when(patient1Snapshot.hasChild("patientid1")).thenReturn(true);
        when(patient1Snapshot.hasChild("doctorid1")).thenReturn(false);
    }

    private void initDoctorSnapshots() {
        when(doctor1Snapshot.getValue(Doctor.class)).thenReturn(defDoctor1);
        when(doctor1Snapshot.hasChild("patientid1")).thenReturn(false);
        when(doctor1Snapshot.hasChild("doctorid1")).thenReturn(true);
    }

    private void initDoctorAvailabilityValidate() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        for (String d: days) {
            when(doctorRef.child("doctorid1/Availability/" + d)).thenReturn(doctorAvailabilityRef);
            when(doctorRef.child("patientid1/Availability/" + d)).thenReturn(doctorAvailabilityRef);
        }

        when(doctorAvailabilityRef.updateChildren(any(Map.class))).thenReturn(updateAvailabilityTask);


        //listener on success
        doAnswer(new Answer<OnSuccessListener>() {

            @Override
            public OnSuccessListener answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(updateAvailabilityTask).addOnSuccessListener(any(OnSuccessListener.class));

        when(updateAvailabilityTask.addOnSuccessListener(any(OnSuccessListener.class))).thenReturn(updateSuccessAvailabilityTask);

        //listener on failure
        doAnswer(new Answer<OnFailureListener>() {

            @Override
            public OnFailureListener answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(updateSuccessAvailabilityTask).addOnFailureListener(any(OnFailureListener.class));
    }

    private void initPatientInfoMock() {
        when(patient1DB.child("Condition")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Surgery")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Allergy")).thenReturn(patientCategoryRef);
        when(patient1DB.child("DrugReaction")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Drug")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Substance")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Exercise")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Smoking")).thenReturn(patientCategoryRef);
        when(patient1DB.child("Drinking")).thenReturn(patientCategoryRef);
        when(patientCategoryRef.child(any(String.class))).thenReturn(patientSubCategoryRef);

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(patientError);
                } else {
                    listener.onDataChange(patient1Snapshot);
                }
                return listener;
            }
        }).when(patientCategoryRef).addValueEventListener(any(ValueEventListener.class));
    }

    private void initProfileListener() {
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                return listener;
            }
        }).when(patient1DB).addValueEventListener(any(ValueEventListener.class));
    }

    private void initDBListeners() {
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(patientError);
                } else {
                    listener.onDataChange(patient1Snapshot);
                }
                return listener;
            }
        }).when(patientRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(patientError);
                } else {
                    listener.onDataChange(patient1Snapshot);
                }
                return listener;
            }
        }).when(patient1DB).addValueEventListener(any(ValueEventListener.class));

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(doctorError);
                } else {
                    listener.onDataChange(doctor1Snapshot);
                }
                return listener;
            }
        }).when(doctorRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(doctorError);
                } else {
                    listener.onDataChange(doctor1Snapshot);
                }
                return listener;
            }
        }).when(doctor1DB).addValueEventListener(any(ValueEventListener.class));

        when(updatePatientTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return updatePatientTask;
            }
        });

        when(updatePatientTask.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(new IllegalStateException("Patient update failed."));
                }
                return updatePatientTask;
            }
        });

        when(updateDoctorTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return updateDoctorTask;
            }
        });

        when(updateDoctorTask.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(new IllegalStateException("Patient update failed."));
                }
                return updateDoctorTask;
            }
        });
    }
}
