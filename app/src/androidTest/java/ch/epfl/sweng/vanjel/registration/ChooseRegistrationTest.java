package ch.epfl.sweng.vanjel.registration;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ChooseRegistrationTest {
    @Rule
    public final IntentsTestRule<ChooseRegistration> iActivityRule =
            new IntentsTestRule<>(ChooseRegistration.class);
    @Test
    public void testCanOpenPatientRegistration() {
        onView(ViewMatchers.withId(R.id.patientButton)).perform(click());
        intended(hasComponent(Registration.class.getName()));
    }

    @Test
    public void testCanOpenDoctorRegistration() {
        onView(withId(R.id.doctorButton)).perform(click());
        intended(hasComponent(Registration.class.getName()));
    }
}