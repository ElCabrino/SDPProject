package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class PatientPersonalAppointmentsTest {

    @Rule
    public ActivityTestRule<PatientPersonalAppointments> mActivityRule = new ActivityTestRule<>(
            PatientPersonalAppointments.class);


    //TODO: mock tests
    @Test
    public void searchTextViewsTest() throws Exception {
        onView(withId(R.id.titleAppointments)).check(matches(withText("Your next appointments")));
    }




}
