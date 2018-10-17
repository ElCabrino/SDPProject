package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import static android.support.test.espresso.Espresso.onView;
/**
 * @author Aslam CADER
 * @reviewer
 */

@RunWith(AndroidJUnit4.class)
public class FilteredDoctorsTest {

    @Rule
    public ActivityTestRule<SearchDoctor> mActivityRule = new ActivityTestRule<>(
            SearchDoctor.class);

    @Before
    public void setUp(){
        onView(withId(R.id.buttonSearch)).perform(click());
    }


    // check that we are in the correct activity
    @Test
    public void testInterface() {


    }

}