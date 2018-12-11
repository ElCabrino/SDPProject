package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.searchDoctor.FilteredDoctors;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;

/**
 * @author Aslam CADER
 * @reviewer
 */

@RunWith(AndroidJUnit4.class)
public class FilteredDoctorsTest {

    @Rule
    public final ActivityTestRule<FilteredDoctors> mActivityRule =
            new ActivityTestRule<>(FilteredDoctors.class, true, false);

    // check that we are in the correct activity
    @Test
    public void testInterface() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("lastName", "ln_dtest1");
        extras.put("firstName", "fn_dtest1");
        extras.put("specialisation", "Dentist");
        extras.put("city", "city_dtest1");
        setupWithExtras(FilteredDoctors.class, mActivityRule, false, true, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.firstName)).check(matches(withText("fn_dtest1")));
        onView(withId(R.id.lastName)).check(matches(withText("ln_dtest1")));
        onView(withId(R.id.activity)).check(matches(withText("Dentist")));
        onView(withId(R.id.city)).check(matches(withText("city_dtest1")));
        onView(withId(R.id.street)).check(matches(withText("street_dtest1")));
        onView(withId(R.id.streetNumber)).check(matches(withText("11")));
        onView(withId(R.id.country)).check(matches(withText("country_dtest1")));
    }
}