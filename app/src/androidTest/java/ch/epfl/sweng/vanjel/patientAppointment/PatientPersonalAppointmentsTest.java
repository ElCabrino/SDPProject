package ch.epfl.sweng.vanjel.patientAppointment;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.CoreMatchers.anything;

public class PatientPersonalAppointmentsTest {

    private final String ap1DateString = "Tue Jan 16 2019";
    private final String ap2DateString = "Tue Feb 06 2019";
    private final String ap3DateString = "Tue Mar 06 2019";
    private final String ap4DateString = "Tue Apr 03 2019";
    private final String ap5DateString = "Tue May 15 2019";
    private final String ap6DateString = "Tue Jun 12 2019";
    private final String ap7DateString = "Tue Jul 10 2019";
    private final String ap8DateString = "Tue Aug 14 2019";
    private final String ap9DateString = "Tue Sep 11 2019";
    private final String ap10DateString = "Tue Oct 16 2019";
    private final String ap11DateString = "Tue Nov 20 2019";
    private final String ap12DateString = "Tue Dec 18 2019";
    private final String apTimeString = "10:00";
    private final String apTimeString2 = "14:00";
    private final String docNameString = "ln_dtest1";
    private final String docAddressString = "street_dtest1, 11 - city_dtest1";

    @Rule
    public final IntentsTestRule<PatientPersonalAppointments> ActivityRule =
            new IntentsTestRule<>(PatientPersonalAppointments.class, true, false);


    //TODO: mock tests
    @Test
    public void searchTextViewsTest() throws Exception {
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.titlePatAppointment)).check(matches(withText("Next Appointments")));
    }

    @Test
    public void recoverJanAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap1DateString);
    }

    @Test
    public void recoverFebAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(2);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap2DateString);
    }

    @Test
    public void recoverMarAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(3);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap3DateString);
    }

    @Test
    public void recoverAprAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(4);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap4DateString);
    }

    @Test
    public void recoverMayAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(5);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap5DateString);
    }

    @Test
    public void recoverJunAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(6);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap6DateString);
    }

    @Test
    public void recoverJulAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(7);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap7DateString);
    }

    @Test
    public void recoverAugAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(8);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap8DateString);
    }

    @Test
    public void recoverSepAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(9);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap9DateString);
    }

    @Test
    public void recoverOctAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(10);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap10DateString);
    }

    @Test
    public void recoverNovAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(11);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap11DateString);
    }

    @Test
    public void recoverDecAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(12);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        checkAppointmentValues(ap12DateString);
    }

    @Test
    public void populateDocMapCancelledTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, true, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewAppointmentDoctorDate)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentTime)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentLocation)).check(doesNotExist());
        onView(withId(R.id.ptNoAppointements)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void onStartCancelledTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, true, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewAppointmentDoctorDate)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentTime)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentLocation)).check(doesNotExist());
    }

    private void checkAppointmentValues(String date) {
        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(0).onChildView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(date+" - Dr."+docNameString+" (pending)")));
        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(0).onChildView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(0).onChildView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));

        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(1).onChildView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(date+" - Dr."+docNameString+" (pending)")));
        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(1).onChildView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString2+", 0 minutes")));
        onData(anything()).inAdapterView(withId(R.id.ptPersonalAppointmentsListView)).atPosition(1).onChildView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}
