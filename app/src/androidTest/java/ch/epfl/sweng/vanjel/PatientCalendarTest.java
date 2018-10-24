package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PatientCalendarTest {
    @Rule
    public final IntentsTestRule<PatientCalendarActivity> ActivityRule =
            new IntentsTestRule<>(PatientCalendarActivity.class);

    @Test
    public void testCalendarButton(){
        onView(withId(R.id.buttonSelectSchedule)).perform(click());
    }
}
