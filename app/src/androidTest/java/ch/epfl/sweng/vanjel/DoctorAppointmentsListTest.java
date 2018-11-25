package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class DoctorAppointmentsListTest {

    @Rule
    public final IntentsTestRule<DoctorAppointmentsList> ActivityRule =
            new IntentsTestRule<>(DoctorAppointmentsList.class);

    @Test
    public void acceptAppointmentTest() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        // id taken in stacktrace
        onView(withId(R.id.durationChosenByDoctor)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(16908313)).check(matches(withText("Confirm")));
    }

    @Test
    public void acceptCancelAppointmentTest() throws InterruptedException{
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        // click accept button but changes his mind and click cancel
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        TimeUnit.SECONDS.sleep(2);
        // id taken in stacktrace
        onView(withId(16908314)).check(matches(withText("Cancel"))).perform(click());
    }

    @Test
    public void declineAppointmentTest() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.declineAppointmentButton)).perform(click());
        onView(withText("Appointment declined")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void declineAppointmentFailedTest() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, true, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.declineAppointmentButton)).perform(click());
        onView(withText("An error occurred when declining the appointment")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void modifyDurationTest() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        // Avoid Toast overlap
        TimeUnit.SECONDS.sleep(3);
        // id taken in stacktrace
        onView(withId(R.id.durationChosenByDoctor)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(16908313)).check(matches(withText("Confirm"))).perform(click());
        onView(withText("A notification has been sent to the patient")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void modifyDurationFailedTest() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, true, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        // Avoid Toast overlap
        TimeUnit.SECONDS.sleep(3);
        // id taken in stacktrace
        onView(withId(R.id.durationChosenByDoctor)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(16908313)).check(matches(withText("Confirm"))).perform(click());
        onView(withText("An error occurred when notifying the patient")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void getAppointmentsCancelled() throws Exception {
        setupNoExtras(DoctorAppointmentsList.class, ActivityRule, false, false, true, true, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.acceptAppointmentButton)).check(doesNotExist());
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }

}
