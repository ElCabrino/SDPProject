package ch.epfl.sweng.vanjel.firebase;

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

import ch.epfl.sweng.vanjel.model.Doctor;
import ch.epfl.sweng.vanjel.model.DoctorActivity;
import ch.epfl.sweng.vanjel.model.Gender;
import ch.epfl.sweng.vanjel.model.Patient;
import ch.epfl.sweng.vanjel.forwardRequest.Forward;
import ch.epfl.sweng.vanjel.patientInfo.Drug;
import ch.epfl.sweng.vanjel.patientInfo.DrugReaction;
import ch.epfl.sweng.vanjel.patientInfo.InfoString;
import ch.epfl.sweng.vanjel.patientInfo.Surgery;

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

    private final String ap1DateString = "Tue Jan 16 2018";
    private final String ap2DateString = "Tue Feb 06 2018";
    private final String ap3DateString = "Tue Mar 06 2018";
    private final String ap4DateString = "Tue Apr 03 2018";
    private final String ap5DateString = "Tue May 15 2018";
    private final String ap6DateString = "Tue Jun 12 2018";
    private final String ap7DateString = "Tue Jul 10 2018";
    private final String ap8DateString = "Tue Aug 14 2018";
    private final String ap9DateString = "Tue Sep 11 2018";
    private final String ap10DateString = "Tue Oct 16 2018";
    private final String ap11DateString = "Tue Nov 20 2018";
    private final String ap12DateString = "Tue Dev 18 2018";
    private final String apTimeString = "10:00";

    private static boolean isCancelled = false;
    private static boolean isCancelledSecond = false;
    private static boolean isCancelledThird = false;
    private static boolean shouldFail = false;
    private static int dateFlag = 1;

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
    private DatabaseReference durationAppointmentRef;
    @Mock
    private DatabaseReference treatedPatientsRef;
    @Mock
    private DatabaseReference patientConditionRef;
    @Mock
    private DatabaseReference patientRefRequest;
    @Mock
    private DatabaseReference forwardRef;
    @Mock
    private DatabaseReference forwardDataRef;

    @Mock
    private DataSnapshot docIdAppointmentSnapshot;
    @Mock
    private DataSnapshot timeDurationAppointmentSnapshot;
    @Mock
    private DataSnapshot patIdAppointmentSnapshot;
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
    private DatabaseError appointmentError;
    @Mock
    private DatabaseError forwardError;

    @Mock
    private DataSnapshot patient1Snapshot;
    @Mock
    private DataSnapshot doctor1Snapshot;
    @Mock
    private DataSnapshot doctorAvailabilitySnapshot;
    @Mock
    private DataSnapshot appointmentSnapshot;
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
    private DataSnapshot treatedPatientsDatasnapshot;
    @Mock
    private DataSnapshot forwardSnapshot;

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
    private Task<Void> acceptChangeDuration;
    @Mock
    private Task<Void> forwardDeleteTask;

    private FirebaseDatabaseCustomBackend() {}

    public static void setIsCancelled(boolean b) {
        isCancelled = b;
    }

    public static void setIsCancelledSecond(boolean b) {
        isCancelledSecond = b;
    }

    public static void setIsCancelledThird(boolean b) {
        isCancelledThird = b;
    }

    public static void setShouldFail(boolean b) {
        shouldFail = b;
    }

    public static void setDateFlag(int i) {
        if (i > 0 && i < 13) {
            dateFlag = i;
        } else {
            dateFlag = 1;
        }
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
        initChatMock();
        initForwardMock();
        initTreatedPatientsMock();
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
        when(patient1Snapshot.getKey()).thenReturn(patient1ID);

        when(patient1Snapshot.exists()).thenReturn(true);

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

    private void initTreatedPatientsMock() {
        List<DataSnapshot> treatedRefs = new ArrayList<>();
        treatedRefs.add(treatedPatientsDatasnapshot);
        when(doctor1DB.child("TreatedPatients")).thenReturn(treatedPatientsRef);
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation){
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(patientError);
                } else {
                    listener.onDataChange(treatedPatientsDatasnapshot);
                }
                return listener;
            }
        }).when(treatedPatientsRef).addValueEventListener(any(ValueEventListener.class));
        when(treatedPatientsDatasnapshot.getChildren()).thenReturn(treatedRefs);
        when(treatedPatientsDatasnapshot.getKey()).thenReturn(patient1ID);
    }

    //mock for the method DoctorAppointmentList.getAppointmentValueListener()
    private void initDoctorAvailabilitySnapshots() {
        List<DataSnapshot> listApp = new ArrayList<>();
        listApp.add(appointmentSnapshot);


        when(doctorAvailabilitySnapshot.getValue(any(GenericTypeIndicator.class))).thenReturn(av);
        when(doctorAvailabilitySnapshot.getValue()).thenReturn(av);
        when(appointmentSnapshot.getChildren()).thenReturn(listApp);
        when(appointmentSnapshot.getKey()).thenReturn(appointmentKey);
        when(appointmentSnapshot.child("date")).thenReturn(dateAppointmentSnapshot);
        when(appointmentSnapshot.child("doctor")).thenReturn(docIdAppointmentSnapshot);
        when(appointmentSnapshot.child("time")).thenReturn(timeDurationAppointmentSnapshot);
        when(appointmentSnapshot.child("patient")).thenReturn(patIdAppointmentSnapshot);
        when(appointmentSnapshot.child("duration")).thenReturn(durationAppointmentSnapshot);
        when(dateAppointmentSnapshot.getValue(String.class)).thenReturn(getDateFromFlag());
        when(docIdAppointmentSnapshot.getValue(String.class)).thenReturn(doctor1ID);
        when(timeDurationAppointmentSnapshot.getValue(String.class)).thenReturn(apTimeString);
        when(patIdAppointmentSnapshot.getValue(String.class)).thenReturn(patient1ID);
        when(durationAppointmentSnapshot.getValue(String.class)).thenReturn("0");

        // mock for DoctorAppointmentList where he needs to accept or decline
        when(requestsRef.child(appointmentKey)).thenReturn(appointmentReqRef);
        when(appointmentReqRef.removeValue()).thenReturn(appointmentRequestTask);
        when(appointmentRequestTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return appointmentRequestTask;
            }
        });
        when(appointmentRequestTask.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(null);
                }
                return appointmentRequestTask;
            }
        });

        when(appointmentReqRef.child("duration")).thenReturn(durationAppointmentRef);
        when(durationAppointmentRef.setValue(any(String.class))).thenReturn(acceptChangeDuration);
        when(acceptChangeDuration.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return acceptChangeDuration;
            }
        });
        when(acceptChangeDuration.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(null);
                }
                return acceptChangeDuration;
            }
        });
    }

    private String getDateFromFlag() {
        String res = "";
        switch(dateFlag) {
            case 1:
                res = ap1DateString;
                break;
            case 2:
                res = ap2DateString;
                break;
            case 3:
                res = ap3DateString;
                break;
            case 4:
                res = ap4DateString;
                break;
            case 5:
                res = ap5DateString;
                break;
            case 6:
                res = ap6DateString;
                break;
            case 7:
                res = ap7DateString;
                break;
            case 8:
                res = ap8DateString;
                break;
            case 9:
                res = ap9DateString;
                break;
            case 10:
                res = ap10DateString;
                break;
            case 11:
                res = ap11DateString;
                break;
            case 12:
                res = ap12DateString;
                break;
        }
        return res;
    }

    //Initialize listener for event on 'Requests' child
    //makes the listener work on 'appointmentSnapshot'
    private void initAppointmentRequestsListMock() {
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation){
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelledThird) {
                    listener.onCancelled(appointmentError);
                } else {
                    listener.onDataChange(appointmentSnapshot);
                }
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

        when(updateAvailabilityTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return updateSuccessAvailabilityTask;
            }
        });

        when(updateSuccessAvailabilityTask.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(null);
                }
                return updateSuccessAvailabilityTask;
            }
        });
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
        when(chatTask.addOnFailureListener(any(OnFailureListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnFailureListener listener = (OnFailureListener) invocation.getArguments()[0];
                if (shouldFail) {
                    listener.onFailure(null);
                    }
                    return chatTask;
                }
        });

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelled) {
                    listener.onCancelled(chatError);
                } else {
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

    private void initForwardMock() {
        List<DataSnapshot> listForward = new ArrayList<>();
        listForward.add(forwardSnapshot);

        when(mockDB.getReference("Forwards")).thenReturn(forwardRef);
        when(DBRef.child("Forwards")).thenReturn(forwardRef);

        when(forwardRef.child(any(String.class))).thenReturn(forwardDataRef);
        when(forwardDataRef.removeValue()).thenReturn(forwardDeleteTask);
        when(forwardDeleteTask.addOnSuccessListener(any(OnSuccessListener.class))).thenAnswer(new Answer<Task<Void>>() {
            @Override
            public Task<Void> answer(InvocationOnMock invocation) throws Throwable {
                OnSuccessListener<Void> listener = (OnSuccessListener<Void>) invocation.getArguments()[0];
                if (!shouldFail) {
                    listener.onSuccess(null);
                }
                return forwardDeleteTask;
            }
        });

        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelledSecond) {
                    listener.onCancelled(forwardError);
                } else {
                    listener.onDataChange(forwardSnapshot);
                }
                return listener;
            }
        }).when(forwardRef).addValueEventListener(any(ValueEventListener.class));

        when(forwardSnapshot.getChildren()).thenReturn(listForward);
        when(forwardSnapshot.getValue(Forward.class)).thenReturn(new Forward(patient1ID, doctor1ID, doctor1ID, defDoctor1.toString(), defDoctor1.toString()));
        when(forwardSnapshot.getKey()).thenReturn("forwardUID");
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

    }

    private void initDBListeners() {
        doAnswer(new Answer<ValueEventListener>() {
            @Override
            public ValueEventListener answer(InvocationOnMock invocation) throws Throwable {
                ValueEventListener listener = (ValueEventListener) invocation.getArguments()[0];
                if (isCancelledSecond) {
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
                if (isCancelledSecond) {
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
