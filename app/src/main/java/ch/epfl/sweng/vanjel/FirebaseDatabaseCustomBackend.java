package ch.epfl.sweng.vanjel;

import android.util.Log;

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

/**
 * Since this class is mostly mock initialisation for the tests, it is ignore by code climate.
 */

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
    private DatabaseReference appointmentReqRef;
    @Mock
    private DatabaseReference chatRef;
    @Mock
    private DatabaseReference chatHistoriqueRef;
    @Mock
    private DatabaseReference appointmentReqKeyRef;
    @Mock
    private DatabaseReference durationAppointmentRef;
    @Mock
    private DatabaseReference patientConditionRef;
    @Mock
    private DatabaseReference patientRefRequest;


    @Mock
    private DataSnapshot docIdAppointmentSnapshot;
    @Mock
    private DataSnapshot timeDurationAppointmentSnapshot;
    @Mock
    private DataSnapshot patIdAppointmentSnapshot;
    @Mock
    private DataSnapshot patientIDSnapshot;
    @Mock
    private DataSnapshot doctorIDSnapshot;
    @Mock
    private DataSnapshot dateSnapshot;
    @Mock
    private DataSnapshot timeSnapshot;
    @Mock
    private DataSnapshot durationSnapshot;
    @Mock
    private DataSnapshot chatTextSnapshot;
    @Mock
    private DataSnapshot chatTimeSnapshot;
    @Mock
    private DataSnapshot chatSenderSnapshot;

    @Mock
    private DatabaseError patientError;
    @Mock
    private DatabaseError doctorError;
    @Mock
    private DatabaseError chatError;

    @Mock
    private DataSnapshot patient1Snapshot;
    @Mock
    private DataSnapshot doctor1Snapshot;
    @Mock
    private DataSnapshot doctorAvailabilitySnapshot;
    @Mock
    private DataSnapshot appointmentSnapshot;
    @Mock
    private DataSnapshot patientAppointmentSnapshot;
    @Mock
    private DataSnapshot chatSnapshot;
    @Mock
    private DataSnapshot dateAppointmentSnapshot;
    @Mock
    private DataSnapshot durationAppointmentSnapshot;
    @Mock
    private DataSnapshot conditionSnapshot;
    @Mock
    private DataSnapshot infoConditionSnapshot;
    @Mock
    private DataSnapshot docLastNameSnapshot;
    @Mock
    private DataSnapshot docStreetNumberSnapshot;
    @Mock
    private DataSnapshot docStreetSnapshot;
    @Mock
    private DataSnapshot docCitySnapshot;


    @Mock
    private Task<Void> updatePatientTask;
    @Mock
    private Task<Void> updateDoctorTask;
    @Mock
    private Task<Void> updateAvailabilityTask;
    @Mock
    private Task<Void> updateSuccessAvailabilityTask;
    @Mock
    private Task<Void> updateApt1Task;
    @Mock
    private Task<Void> chatTask;
    @Mock
    private Task<Void> appointmentRequestTask;
    @Mock
    private Task<Void> appointmentRequestTaskWithSuccess;
    @Mock
    private Task<Void> acceptChangeDuration;

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
        //initPatientAppointmentsSnapshots();
        initChatMock();
        //initPatientConditionsSnapshots();
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
        List<DataSnapshot> listPatient = new ArrayList<>();
        listPatient.add(patient1Snapshot);
        when(patient1Snapshot.getValue(Patient.class)).thenReturn(defPatient1);
        when(patient1Snapshot.hasChild("patientid1")).thenReturn(true);
        when(patient1Snapshot.hasChild("doctorid1")).thenReturn(false);
        when(patient1Snapshot.getChildren()).thenReturn(listPatient);
        when(patient1Snapshot.getKey()).thenReturn("patientid1");

        when(patient1Snapshot.getValue(String.class)).thenReturn("1");
        when(patient1Snapshot.getValue(InfoString.class)).thenReturn(new InfoString("Cats"));
        when(patient1Snapshot.getValue(Surgery.class)).thenReturn(new Surgery("THA", "2000"));
        when(patient1Snapshot.getValue(Drug.class)).thenReturn(new Drug("Enalapril", "10mg", "1"));
        when(patient1Snapshot.getValue(DrugReaction.class)).thenReturn(new DrugReaction("Carbamazepine", "TEN"));
    }

    private void initDoctorSnapshots() {
        List<DataSnapshot> listDoctor = new ArrayList<>();
        listDoctor.add(doctor1Snapshot);
        when(doctor1Snapshot.getValue(Doctor.class)).thenReturn(defDoctor1);
        when(doctor1Snapshot.hasChild("patientid1")).thenReturn(false);
        when(doctor1Snapshot.hasChild("doctorid1")).thenReturn(true);
        when(doctor1Snapshot.getChildren()).thenReturn(listDoctor);
        when(doctor1Snapshot.child("lastName")).thenReturn(docLastNameSnapshot);
        when(docLastNameSnapshot.getValue(String.class)).thenReturn(defDoctor1.getLastName());
        when(doctor1Snapshot.child("streetNumber")).thenReturn(docStreetNumberSnapshot);
        when(docStreetNumberSnapshot.getValue(String.class)).thenReturn(defDoctor1.getStreetNumber());
        when(doctor1Snapshot.child("street")).thenReturn(docStreetSnapshot);
        when(docStreetSnapshot.getValue(String.class)).thenReturn(defDoctor1.getStreet());
        when(doctor1Snapshot.child("city")).thenReturn(docCitySnapshot);
        when(docCitySnapshot.getValue(String.class)).thenReturn(defDoctor1.getCity());
        when(doctor1Snapshot.getKey()).thenReturn(doctor1ID);
        when(doctor1Snapshot.getChildren()).thenReturn(listDoctor);
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
        when(dateAppointmentSnapshot.getValue(String.class)).thenReturn("Tue Nov 20 2018");
        when(docIdAppointmentSnapshot.getValue(String.class)).thenReturn(doctor1ID);
        when(timeDurationAppointmentSnapshot.getValue(String.class)).thenReturn("10:00");
        when(patIdAppointmentSnapshot.getValue(String.class)).thenReturn(patient1ID);
        //when(durationAppointmentSnapshot.getValue(String.class)).thenReturn("10");
        //when(dateAppointmentSnapshot.getValue(String.class)).thenReturn("Monday");
        //when(docIdAppointmentSnapshot.getValue(String.class)).thenReturn("doctorid1");
        //when(timeDurationAppointmentSnapshot.getValue(String.class)).thenReturn("timApt");
        //when(patIdAppointmentSnapshot.getValue(String.class)).thenReturn("patApt");
        when(durationAppointmentSnapshot.getValue(String.class)).thenReturn("0");
        /*when(patientAppointmentSnapshot.child("patient")).thenReturn(patientIDSnapshot);
        when(patientIDSnapshot.getValue(String.class)).thenReturn(patient1ID);
        when(patientAppointmentSnapshot.child("doctor")).thenReturn(doctorIDSnapshot);
        when(doctorIDSnapshot.getValue(String.class)).thenReturn(doctor1ID);
        when(patientAppointmentSnapshot.child("date")).thenReturn(dateSnapshot);
        when(dateSnapshot.getValue(String.class)).thenReturn("Tue Nov 20 2018");
        when(patientAppointmentSnapshot.child("time")).thenReturn(timeSnapshot);
        when(timeSnapshot.getValue(String.class)).thenReturn("10:00");
        when(patientAppointmentSnapshot.child("duration")).thenReturn(durationSnapshot);
        when(durationSnapshot.getValue(String.class)).thenReturn("10");*/

        // mock for DoctorAppointmentList where he needs to accept or decline
        when(requestsRef.child(appointmentKey)).thenReturn(appointmentReqRef);
        when(appointmentReqRef.removeValue()).thenReturn(appointmentRequestTask);
        when(appointmentRequestTask.addOnSuccessListener(any(OnSuccessListener.class))).thenReturn(appointmentRequestTaskWithSuccess);
        when(appointmentRequestTaskWithSuccess.addOnFailureListener(any(OnFailureListener.class))).thenReturn(appointmentRequestTask);

        when(appointmentReqRef.child("duration")).thenReturn(durationAppointmentRef);
        when(durationAppointmentRef.setValue(any(String.class))).thenReturn(acceptChangeDuration);
        when(acceptChangeDuration.addOnSuccessListener(any(OnSuccessListener.class))).thenReturn(acceptChangeDuration);
        when(acceptChangeDuration.addOnFailureListener(any(OnFailureListener.class))).thenReturn(acceptChangeDuration);
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

    private void initChatMock() {
        List<DataSnapshot> listChat = new ArrayList<>();
        listChat.add(chatSnapshot);
        when(mockDB.getReference("Chat")).thenReturn(chatRef);
        when(chatRef.child(any(String.class))).thenReturn(chatHistoriqueRef);
        when(chatHistoriqueRef.updateChildren(any(Map.class))).thenReturn(chatTask);
        when(chatTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return chatTask;
            }
        });

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    Log.d("TESTRUNNING", "test1");
                    listener.onCancelled(chatError);
                } else {
                    Log.d("TESTRUNNING", "test2");
                    listener.onDataChange(chatSnapshot);
                }
                return listener;
            }
        }).when(chatRef).addValueEventListener(any(ValueEventListener.class));

        when(chatSnapshot.getChildren()).thenReturn(listChat);
        when(chatSnapshot.getKey()).thenReturn("doctorid1patientid1");
        when(chatSnapshot.child("text")).thenReturn(chatTextSnapshot);
        when(chatSnapshot.child("time")).thenReturn(chatTimeSnapshot);
        when(chatSnapshot.child("sender")).thenReturn(chatSenderSnapshot);

        when(chatTextSnapshot.getValue()).thenReturn("test message");
        when(chatTimeSnapshot.getValue()).thenReturn("07.30");
        when(chatSenderSnapshot.getValue()).thenReturn("doctorid1");
    }

    private void initPatientConditionsSnapshots() {
        List<DataSnapshot> listCond = new ArrayList<>();
        listCond.add(conditionSnapshot);

        when(conditionSnapshot.getChildren()).thenReturn(listCond);
        when(conditionSnapshot.child("Heart failure")).thenReturn(infoConditionSnapshot);
        when(infoConditionSnapshot.getValue()).thenReturn("Heart failure");

    }

    private void initPatientInfoMock() {
        //when(patient1DB.child("Condition")).thenReturn(patientConditionRef);
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
        //when(patientCategoryRef.getValue(any(GenericTypeIndicator.class)))).thenReturn(defDoctor1);

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

        /*doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation){
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                listener.onDataChange(conditionSnapshot);
                return listener;
            }
        }).when(patientConditionRef).addValueEventListener(any(ValueEventListener.class));*/

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

        // test
        /*doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(patientError);
                } else {
                    listener.onDataChange(patientAppointmentSnapshot);
                }
                return listener;
            }
        }).when(requestsRef).addValueEventListener(any(ValueEventListener.class));*/

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
                    listener.onDataChange(appointmentSnapshot);
                }
                return listener;
            }
        }).when(patientRef).addValueEventListener(any(ValueEventListener.class));

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
