package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.core.AllOf.allOf;

public class PatientPersonalAppointmentsTest {

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
    private final String docNameString = "ln_dtest1";
    private final String docAddressString = "11 street_dtest1 city_dtest1";


    @Rule
    public final IntentsTestRule<PatientPersonalAppointments> ActivityRule =
            new IntentsTestRule<>(PatientPersonalAppointments.class, true, false);


    //TODO: mock tests
    @Test
    public void searchTextViewsTest() throws Exception {
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.titleAppointments)).check(matches(withText("Your next appointments")));
    }

    @Test
    public void recoverJanAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap1DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverFebAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(2);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap2DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverMarAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(3);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap3DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverAprAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(4);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap4DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverMayAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(5);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap5DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverJunAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(6);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap6DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverJulAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(7);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap7DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverAugAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(8);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap8DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverSepAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(9);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap9DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverOctAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(10);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap10DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverNovAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(11);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap11DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void recoverDecAppointmentTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(12);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);

        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap12DateString+" - Dr."+docNameString+" (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(docAddressString)));
    }

    @Test
    public void populateDocMapCancelledTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, true, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(ap1DateString+" - Dr. (pending)")));
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText(apTimeString+", 0 minutes")));
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("")));
    }

    @Test
    public void onStartCancelledTest() throws Exception {
        FirebaseDatabaseCustomBackend.setDateFlag(1);
        setupNoExtras(PatientPersonalAppointments.class, ActivityRule, false, true, false, false, false, true);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewAppointmentDoctorDate)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentTime)).check(doesNotExist());
        onView(withId(R.id.textViewAppointmentLocation)).check(doesNotExist());
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}
