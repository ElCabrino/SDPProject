package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.IsNot.not;

public class DoctorAvailabilityActivityTest {

    @BeforeClass
    public static void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

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

    @Rule
    public ActivityTestRule<DoctorAvailabilityActivity> mActivityRule =
            new ActivityTestRule<>(DoctorAvailabilityActivity.class);

    @Test
    public void canSelectAvailabilityTest() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        clickTestSlots();
        for (int j: toCheck) {
            onView(withId(TimeAvailability.times[j])).perform(scrollTo()).check(matches(isChecked()));
        }
    }

    @Test
    public void validateTest() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        for (int i=0;i<TimeAvailability.getIdLength();i++) {
            onView(withId(TimeAvailability.times[i])).perform(scrollTo()).check(matches(not(isChecked())));
        }
        clickTestSlots();
        onView(withId(R.id.valid)).perform(scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
        Intent intent = new Intent(Intent.ACTION_PICK);
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(intent);
        TimeUnit.SECONDS.sleep(2);
        for (int k: toCheck) {
            onView(withId(TimeAvailability.times[k])).perform(scrollTo()).check(matches(isChecked()));
        }
        clickTestSlots();
        onView(withId(R.id.valid)).perform(scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
    }

    private void clickTestSlots() {
        for (int i: toCheck) {
            onView(withId(TimeAvailability.times[i])).perform(scrollTo(), click());
        }
    }
}