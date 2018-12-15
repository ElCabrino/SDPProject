package ch.epfl.sweng.vanjel;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.doctorCalendar.DoctorCalendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

@RunWith(AndroidJUnit4.class)
public class DoctorCalendarTest {

    @Rule
    public ActivityTestRule<DoctorCalendar> mActivityRule = new ActivityTestRule<>(
            DoctorCalendar.class, true, false);

    @Test
    public void TestInterface() {
        setupNoExtras(DoctorCalendar.class, mActivityRule, false, false, false, false, false, false);
        onView(withId(R.id.add)).perform(click());
        onView(withId(R.id.calender_recyclerView)).check(matches(hasChildCount(1)));
        onView(withId(R.id.calender_recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on specific button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View button = view.findViewById(R.id.delete);
                // Maybe check for null
                button.performClick();
            }
        }));
        onView(withId(R.id.calender_recyclerView)).check(matches(hasDescendant(withId(R.id.layout))));
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}


