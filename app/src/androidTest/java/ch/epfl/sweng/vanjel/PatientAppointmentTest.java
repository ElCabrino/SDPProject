package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PatientAppointmentTest {
    @Rule
    public final IntentsTestRule<PatientAppointmentActivity> ActivityRule =
            new IntentsTestRule<>(PatientAppointmentActivity.class);

    @Test
    public void testAppointment(){
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView((withId(R.id.buttonAppointment))).perform(scrollTo(), click());
    }
}
