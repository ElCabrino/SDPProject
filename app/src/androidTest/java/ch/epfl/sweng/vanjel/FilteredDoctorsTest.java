package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static android.support.test.espresso.Espresso.onView;

/**
 * @author Aslam CADER
 * @reviewer
 */

@RunWith(AndroidJUnit4.class)
public class FilteredDoctorsTest {

    @Rule
    public final ActivityTestRule<FilteredDoctors> mActivityRule =
            new ActivityTestRule<FilteredDoctors>(FilteredDoctors.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, FilteredDoctors.class);
            result.putExtra("lastName", "ln_dtest1");
            result.putExtra("firstName", "fn_dtest1");
            result.putExtra("specialisation", "Dentist");
            result.putExtra("city", "city_dtest1");
            return result;
        }
    };

    @Before
    public void initIntents() {
        Intents.init();
    }

    @After
    public void releaseIntents() {
        Intents.release();
    }

    // check that we are in the correct activity
    @Test
    public void testInterface() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.firstName)).check(matches(withText("fn_dtest1")));
        onView(withId(R.id.lastName)).check(matches(withText("ln_dtest1")));
        onView(withId(R.id.activity)).check(matches(withText("Dentist")));
        onView(withId(R.id.city)).check(matches(withText("city_dtest1")));
        onView(withId(R.id.street)).check(matches(withText("street_dtest1")));
        onView(withId(R.id.streetNumber)).check(matches(withText("11")));
        onView(withId(R.id.country)).check(matches(withText("country_ptest1")));
    }
}