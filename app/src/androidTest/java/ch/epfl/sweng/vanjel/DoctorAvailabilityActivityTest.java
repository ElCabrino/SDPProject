package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.widget.ToggleButton;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class DoctorAvailabilityActivityTest {


    @Rule
    public ActivityTestRule<DoctorAvailabilityActivity> mActivityRule =
            new ActivityTestRule<>(DoctorAvailabilityActivity.class);

    @Test
    public void canSelectAvailabilityTest() throws Exception {
        int[] toCheck = {
                0,
                5,
                18,
                23,
                38,
                46,
                51,
                68,
                72,
                85,
                98,
                101,
                114,
                124,
                130
        };
        TimeUnit.SECONDS.sleep(2);
        for (int i: toCheck) {
            onView(withId(TimeAvailability.times[i])).perform(scrollTo(), click());
        }
        for (int j: toCheck) {
            onView(withId(TimeAvailability.times[j])).perform(scrollTo()).check(matches(isChecked()));
        }
    }
}