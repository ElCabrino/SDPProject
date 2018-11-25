package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.Matchers.not;

public class DoctorAvailabilityActivityTest {

    private int[] toCheck = {
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

    private int[] avIndex = {
            1,
            2,
            3,
            4,
            5,
            9,
            10,
            11,
            12,
            13
    };

    @Rule
    public ActivityTestRule<DoctorAvailabilityActivity> mActivityRule =
            new ActivityTestRule<>(DoctorAvailabilityActivity.class, true, false);

    @Test
    public void checkOldAvailabilityTest() throws Exception {
        setupNoExtras(DoctorAvailabilityActivity.class, mActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        for (int j: avIndex) {
            onView(withId(TimeAvailability.times[j])).perform(scrollTo()).check(matches(isChecked()));
        }
    }

    @Test
    public void checkOldAvailabilityCancelledTest() throws Exception {
        setupNoExtras(DoctorAvailabilityActivity.class, mActivityRule, false, false, false, true, false);
        TimeUnit.SECONDS.sleep(1);
        for (int i=0;i<TimeAvailability.getIdLength();i++) {
            onView(withId(TimeAvailability.times[i])).perform(scrollTo()).check(matches(not(isChecked())));
        }
    }

    @Test
    public void canSelectAvailabilityTest() throws Exception {
        setupNoExtras(DoctorAvailabilityActivity.class, mActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        for (int i: avIndex) {
            onView(withId(TimeAvailability.times[i+TimeAvailability.MONDAY])).perform(scrollTo(), click());
            onView(withId(TimeAvailability.times[i+TimeAvailability.TUESDAY])).perform(scrollTo(), click());
            onView(withId(TimeAvailability.times[i+TimeAvailability.WEDNESDAY])).perform(scrollTo(), click());
            onView(withId(TimeAvailability.times[i+TimeAvailability.THURSDAY])).perform(scrollTo(), click());
            onView(withId(TimeAvailability.times[i+TimeAvailability.FRIDAY])).perform(scrollTo(), click());
            onView(withId(TimeAvailability.times[i+TimeAvailability.SATURDAY])).perform(scrollTo(), click());
        }
        clickTestSlots();
        for (int j: toCheck) {
            onView(withId(TimeAvailability.times[j])).perform(scrollTo()).check(matches(isChecked()));
        }
    }

    @Test
    public void validateTest() throws Exception {
        setupNoExtras(DoctorAvailabilityActivity.class, mActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.valid)).perform(scrollTo(), click());
        onView(withText("Doctor availability successfully updated.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

    @Test
    public void validateFailedTest() throws Exception {
        setupNoExtras(DoctorAvailabilityActivity.class, mActivityRule, false, false, true, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.valid)).perform(scrollTo(), click());
        onView(withText("Failed to update Doctor availability.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

    private void clickTestSlots() {
        for (int i: toCheck) {
            onView(withId(TimeAvailability.times[i])).perform(scrollTo(), click());
        }
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}