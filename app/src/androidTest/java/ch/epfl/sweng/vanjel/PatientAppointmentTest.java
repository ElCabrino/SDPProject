package ch.epfl.sweng.vanjel;

import android.graphics.drawable.ColorDrawable;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PatientAppointmentTest {
    @Rule
    public final IntentsTestRule<PatientAppointmentActivity> ActivityRule =
            new IntentsTestRule<>(PatientAppointmentActivity.class);

    @Test
    public void testAppointmentHighlight(){
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0800)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF303F9F)));
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0800)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF3F51B5)));
    }

    @Test
    public void testDoubleSelection() {
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0900)).perform(scrollTo(), click());
        onView(withText("You've already picked a time slot")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    public static Matcher<View> withBackgroundColor(final int backgroundColor) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                int color = ((ColorDrawable) view.getBackground().getCurrent()).getColor();
                return color == backgroundColor;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with background color value: " + backgroundColor);
            }
        };
    }
}
