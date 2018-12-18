package ch.epfl.sweng.vanjel.registration;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.TestHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

@RunWith(AndroidJUnit4.class)
public class ChooseRegistrationTest {

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }

    @Rule
    public final IntentsTestRule<ChooseRegistration> iActivityRule =
            new IntentsTestRule<>(ChooseRegistration.class, true, false);
    @Test
    public void testCanOpenPatientRegistration() {
        setupNoExtras(ChooseRegistration.class, iActivityRule, false, false, false, false, false, false, false);
        onView(withId(R.id.patientButton)).perform(click());
        intended(hasComponent(Registration.class.getName()));
        intended(hasExtra("DoctorReg", false));
    }

    @Test
    public void testCanOpenDoctorRegistration() {
        setupNoExtras(ChooseRegistration.class, iActivityRule, false, false, false, false, false, false, false);
        onView(withId(R.id.doctorButton)).perform(click());
        intended(hasComponent(Registration.class.getName()));
        intended(hasExtra("DoctorReg", true));
    }
}