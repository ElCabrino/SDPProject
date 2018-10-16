package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
/**
 * @author Aslam CADER
 * @reviewer
 */

@RunWith(AndroidJUnit4.class)
public class FilteredDoctorsTest {

    @Rule
    public ActivityTestRule<FilteredDoctors> mActivityRule = new ActivityTestRule<>(
            FilteredDoctors.class);


    // check that we are in the correct activity
    @Test
    public void testInterface() {


    }

}