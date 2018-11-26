package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

public class PatientPersonalAppointmentsTest {

    @Rule
    public final IntentsTestRule<PatientPersonalAppointments> ActivityRule =
            new IntentsTestRule<>(PatientPersonalAppointments.class);


    @Before
    public void init() throws InterruptedException{
        ActivityRule.finishActivity();
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(true);
        ActivityRule.launchActivity(new Intent());
        TimeUnit.SECONDS.sleep(2);
    }


    //TODO: mock tests
    @Test
    public void searchTextViewsTest() throws Exception {
        onView(withId(R.id.titleAppointments)).check(matches(withText("Your next appointments")));
    }

    @Test
    public void recoverListValue() throws InterruptedException {

        //onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.textViewAppointmentDoctorDate)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.textViewAppointmentTime)).perform(scrollTo(),closeSoftKeyboard());
        //TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewAppointmentLocation)).perform(scrollTo(),closeSoftKeyboard());

    }




}
