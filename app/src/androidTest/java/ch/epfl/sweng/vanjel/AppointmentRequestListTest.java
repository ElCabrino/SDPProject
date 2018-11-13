package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AppointmentRequestListTest {

    LoginHelper helper = new LoginHelper();

    @Rule
    public final IntentsTestRule<Profile> ActivityRule =
            new IntentsTestRule<>(Profile.class);

    @Before
    public void setUp() {
        helper.signOutIfPossible();
        helper.enterEmail("doctor@doctor.ch");
        helper.enterPassword("123456");
    }

    @Test
    public void testRequestButton() {
        onView(withId(R.id.appointmentRequestsButton)).perform(click());
    }

}
