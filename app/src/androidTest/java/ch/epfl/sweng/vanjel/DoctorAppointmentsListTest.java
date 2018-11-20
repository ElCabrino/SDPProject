package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class DoctorAppointmentsListTest {

    @Rule
    public final IntentsTestRule<DoctorAppointmentsList> ActivityRule =
            new IntentsTestRule<>(DoctorAppointmentsList.class);

    @Before
    public void init() throws InterruptedException{
        ActivityRule.finishActivity();
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(false);
        ActivityRule.launchActivity(new Intent());
        TimeUnit.SECONDS.sleep(2);
    }



    @Test
    public void testDisplayPage() throws InterruptedException{
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testAcceptAppointment(){
//        TimeUnit.SECONDS.sleep(10);
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        // id taken in stacktrace
        onView(withId(R.id.durationChosenByDoctor)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(16908313)).check(matches(withText("Confirm")));
    }

    @Test
    public void testAcceptCancelAppointment() throws InterruptedException{
        // click accept button but changes his mind and click cancel
        onView(withId(R.id.acceptAppointmentButton)).perform(click());
        TimeUnit.SECONDS.sleep(2);
        // id taken in stacktrace
        onView(withId(16908314)).check(matches(withText("Cancel"))).perform(click());

    }


    @Test
    public void testDeclineAppointment(){
        onView(withId(R.id.declineAppointmentButton)).perform(click());
    }

}
