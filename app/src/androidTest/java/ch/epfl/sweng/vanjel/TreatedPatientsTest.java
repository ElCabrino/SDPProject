package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

public class TreatedPatientsTest {

    @Rule
    public final ActivityTestRule<TreatedPatients> mActivityRule =
            new ActivityTestRule<>(TreatedPatients.class, true, false);

    @Test
    public void displayTreatedPatientsTest() throws Exception {
        setupNoExtras(TreatedPatients.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.firstName)).check(matches(withText("fn_ptest1")));
        onView(withId(R.id.lastName)).check(matches(withText("ln_ptest1")));
        onView(withId(R.id.street)).check(matches(withText("street_ptest1")));
        onView(withId(R.id.streetNumber)).check(matches(withText("1")));
        onView(withId(R.id.city)).check(matches(withText("city_ptest1")));
        onView(withId(R.id.country)).check(matches(withText("country_ptest1")));
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }

}