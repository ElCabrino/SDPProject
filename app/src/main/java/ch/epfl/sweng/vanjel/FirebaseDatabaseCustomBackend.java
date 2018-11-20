package ch.epfl.sweng.vanjel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public final class FirebaseDatabaseCustomBackend {

    private final static String patient1ID = "patientid1";
    private final static Patient defPatient1 = new Patient("patient1@test.ch", "fn_ptest1", "ln_ptest1", "01/01/2001", "street_ptest1", "1", "city_ptest1", "country_ptest1", Gender.Male);

    private final static String doctor1ID = "doctorid1";
    private final static Doctor defDoctor1 = new Doctor("doctor1@test.ch", "fn_dtest1", "ln_dtest1", "11/11/2011", "street_dtest1", "11", "city_dtest1", "country_dtest1", Gender.Male, DoctorActivity.Dentist);

    private final static HashMap<String, String> av;
    static {
        av = new HashMap<>();
        av.put("availability", "8:30-11:00 / 12:30-15:00");
    }

    private final static String appointmentKey = "aptKey";

    private static boolean isCancelled = false;
    private static boolean shouldFail = false;

    @Mock
    private FirebaseDatabase mockDB;

    @Mock
    private DatabaseReference DBRef;
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
    private DatabaseReference requestsRef;
    @Mock
    private DatabaseReference apt1Ref;
    @Mock
    private DatabaseReference appointmentReqRef;
    @Mock
    private DatabaseReference appointmentReqKeyRef;
    @Mock
    private DatabaseReference durationAppointmentRef;

    @Mock
    private DataSnapshot docIdAppointmentSnapshot;
    @Mock
    private DataSnapshot timeDurationAppointmentSnapshot;
    @Mock
    private DataSnapshot patIdAppointmentSnapshot;

    @Mock
    private DatabaseError patientError;
    @Mock
    private DatabaseError doctorError;

    @Mock
    private DataSnapshot patient1Snapshot;
    @Mock
    private DataSnapshot doctor1Snapshot;
    @Mock
    private DataSnapshot doctorAvailabilitySnapshot;
    @Mock
    private DataSnapshot appointmentSnapshot;
    @Mock
    private DataSnapshot dateAppointmentSnapshot;
    @Mock
    private DataSnapshot durationAppointmentSnapshot;

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
    @Mock
    private Task<Void> updateApt1Task;
    @Mock
    private Task<Void> appointmentRequestTask;
    @Mock
    private Task<Void> appointmentRequestTaskWithSuccess;
    @Mock
    private Task<Void> acceptChangeDuration;


    @Mock
    private OnFailureListener onFailureListener;
    @Mock
    private OnSuccessListener onSuccessListener;


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
        initDoctorAvailabilitySnapshots();
        initDBListeners();
        initDoctorAvailabilityValidate();
        initPatientInfoMock();
        initAppointmentRequestsListMock();
        //initProfileListener();
        return mockDB;
    }

    private void initDatabase() {
        when(mockDB.getReference()).thenReturn(DBRef);
        when(mockDB.getReference("Patient")).thenReturn(patientRef);
        when(mockDB.getReference("Doctor")).thenReturn(doctorRef);
        when(mockDB.getReference("Doctor/doctorid1/Availability")).thenReturn(doctorRef);
        when(mockDB.getReference("Requests")).thenReturn(requestsRef);
    }

    private void initRefDatabase() {
        when(patientRef.child(patient1ID)).thenReturn(patient1DB);
        when(doctorRef.child(doctor1ID)).thenReturn(doctor1DB);

        when(DBRef.child("Doctor")).thenReturn(doctorRef);

        when(patient1DB.updateChildren(any(Map.class))).thenReturn(updatePatientTask);
        when(doctor1DB.updateChildren(any(Map.class))).thenReturn(updateDoctorTask);
        when(requestsRef.push()).thenReturn(requestsRef);
        when(requestsRef.updateChildren(any(Map.class))).thenReturn(updateApt1Task);
    }

    private void initPatientSnapshots() {
        when(patient1Snapshot.getValue(Patient.class)).thenReturn(defPatient1);
        when(patient1Snapshot.hasChild("patientid1")).thenReturn(true);
        when(patient1Snapshot.hasChild("doctorid1")).thenReturn(false);
    }

    private void initDoctorSnapshots() {
        List<DataSnapshot> listDoc = new ArrayList<>();
        listDoc.add(doctor1Snapshot);
        when(doctor1Snapshot.getValue(Doctor.class)).thenReturn(defDoctor1);
        when(doctor1Snapshot.hasChild("patientid1")).thenReturn(false);
        when(doctor1Snapshot.hasChild("doctorid1")).thenReturn(true);
        when(doctor1Snapshot.getChildren()).thenReturn(listDoc);
    }

    //mock for the method DoctorAppointmentList.getAppointmentValueListener()
    private void initDoctorAvailabilitySnapshots() {
        List<DataSnapshot> listApp = new ArrayList<>();
        listApp.add(appointmentSnapshot);



        when(doctorAvailabilitySnapshot.getValue(any(GenericTypeIndicator.class))).thenReturn(av);
        when(appointmentSnapshot.getChildren()).thenReturn(listApp);
        when(appointmentSnapshot.getKey()).thenReturn(appointmentKey);
        when(appointmentSnapshot.child("date")).thenReturn(dateAppointmentSnapshot);
        when(appointmentSnapshot.child("doctor")).thenReturn(docIdAppointmentSnapshot);
        when(appointmentSnapshot.child("time")).thenReturn(timeDurationAppointmentSnapshot);
        when(appointmentSnapshot.child("patient")).thenReturn(patIdAppointmentSnapshot);
        when(appointmentSnapshot.child("duration")).thenReturn(durationAppointmentSnapshot);
        when(dateAppointmentSnapshot.getValue()).thenReturn("Monday");
        when(docIdAppointmentSnapshot.getValue()).thenReturn("doctorid1");
        when(timeDurationAppointmentSnapshot.getValue()).thenReturn("timApt");
        when(patIdAppointmentSnapshot.getValue()).thenReturn("patApt");
        when(durationAppointmentSnapshot.getValue()).thenReturn("0");

        // mock for DoctorAppointmentList where he needs to accept or decline
        when(requestsRef.child(appointmentKey)).thenReturn(appointmentReqRef);
        when(appointmentReqRef.removeValue()).thenReturn(appointmentRequestTask);
        when(appointmentRequestTask.addOnSuccessListener(any(OnSuccessListener.class))).thenReturn(appointmentRequestTaskWithSuccess);
        when(appointmentRequestTaskWithSuccess.addOnFailureListener(any(OnFailureListener.class))).thenReturn(appointmentRequestTask);

        when(appointmentReqRef.child("duration")).thenReturn(durationAppointmentRef);
        when(durationAppointmentRef.setValue(any(String.class))).thenReturn(acceptChangeDuration);
        when(acceptChangeDuration.addOnSuccessListener(any(OnSuccessListener.class))).thenReturn(acceptChangeDuration);
        when(acceptChangeDuration.addOnFailureListener(any(OnFailureListener.class))).thenReturn(acceptChangeDuration);


//        FirebaseDatabaseCustomBackend.getInstance().getReference("Requests").child(appointmentID).child("duration").setValue(duration).addOnSuccessListener(new OnSuccessListener<Void>() {


                                                                                                                                                            }

    //Initialize listener for event on 'Requests' child
    //makes the listener work on 'appointmentSnapshot'
    private void initAppointmentRequestsListMock() {
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation){
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                listener.onDataChange(appointmentSnapshot);
                return listener;
            }
        }).when(requestsRef).addValueEventListener(any(ValueEventListener.class));
    }

    private void initDoctorAvailabilityValidate() {
        List<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        days.add("Saturday");
        days.add("Sunday");
        for (String d: days) {
            when(doctorRef.child(d)).thenReturn(doctorAvailabilityRef);
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
        }).when(doctorRef).addValueEventListener(any(ValueEventListener.class));

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

        when(updateApt1Task.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return updateApt1Task;
            }
        });

        when(updateApt1Task.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(new IllegalStateException("Appointment request failed."));
                }
                return updateApt1Task;
            }
        });

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(doctorError);
                } else {
                    listener.onDataChange(doctorAvailabilitySnapshot);
                }
                return listener;
            }
        }).when(doctorAvailabilityRef).addValueEventListener(any(ValueEventListener.class));
    }
}
