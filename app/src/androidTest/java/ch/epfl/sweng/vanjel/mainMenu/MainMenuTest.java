package ch.epfl.sweng.vanjel.mainMenu;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.chat.ChatListActivity;
import ch.epfl.sweng.vanjel.doctorAppointment.DoctorAppointmentsList;
import ch.epfl.sweng.vanjel.doctorAppointment.DoctorComingAppointments;
import ch.epfl.sweng.vanjel.doctorAvailability.DoctorAvailabilityActivity;
import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.favoriteList.PatientFavoriteListActivity;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.login.LoginActivity;
import ch.epfl.sweng.vanjel.nearbyDoctor.NearbyDoctor;
import ch.epfl.sweng.vanjel.patientAppointment.PatientPersonalAppointments;
import ch.epfl.sweng.vanjel.patientInfo.PatientInfo;
import ch.epfl.sweng.vanjel.searchDoctor.SearchDoctor;
import ch.epfl.sweng.vanjel.treatedPatient.TreatedPatientsActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.junit.Assert.assertEquals;

public class MainMenuTest {

    @Rule
    public ActivityTestRule<MainMenu> mActivityRule =
            new ActivityTestRule<>(MainMenu.class, true, false);

    @BeforeClass
    public static void setUp() {
        Intents.init();
    }

    @AfterClass
    public static void restoreIntents() {
        Intents.release();
        restoreMockFlags();
    }

    @Test
    public void searchDoctorButtonTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.searchMainMenu)).perform(click());
        intended(hasComponent(SearchDoctor.class.getName()));
    }

    @Test
    public void patientInfoButtonAsPatientTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.infosMainMenu)).perform(click());
        intended(hasComponent(PatientInfo.class.getName()));
    }

    @Test
    public void setAvailabilityButton() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.setAvailMainMenu)).perform(click());
        intended(hasComponent(DoctorAvailabilityActivity.class.getName()));
    }

    @Test
    public void nearbyDoctorButtonTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.nearbyMainMenu)).perform(click());
        intended(hasComponent(NearbyDoctor.class.getName()));
    }

    @Test
    public void treatedPatientsButtonTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.treatedMainMenu)).perform(click());
        intended(hasComponent(TreatedPatientsActivity.class.getName()));
    }

    @Test
    public void favoriteListButtonTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.favoriteMainMenu)).perform(click());
        intended(hasComponent(PatientFavoriteListActivity.class.getName()));
    }

    @Test
    public void nextAppointmentsAsPatientTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.nextAppointMainMenu)).perform(click());
        intended(hasComponent(PatientPersonalAppointments.class.getName()));
    }

    @Test
    public void nextAppointmentsAsDoctorTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.nextAppointMainMenu)).perform(click());
        intended(hasComponent(DoctorComingAppointments.class.getName()));

    }

    @Test
    public void requestsListTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.requestsMainMenu)).perform(click());
        intended(hasComponent(DoctorAppointmentsList.class.getName()));
    }

    @Test
    public void chatAccessTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.chatMainMenu)).perform(click());
        intended(hasComponent(ChatListActivity.class.getName()));
    }

    @Test
    public void logOutTest() throws Exception {
        setupNoExtras(MainMenu.class, mActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        FirebaseAuthCustomBackend.setNullUser(true);
        onView(withId(R.id.logoutMainMenu)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
        //verifiy if local favorite are erased
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        assertEquals(0, l.getAll().size());
    }
}