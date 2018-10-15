package ch.epfl.sweng.vanjel;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.ListView;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class PatientInfoTest {

    String condition = "Good condition";
    String smoking = "Often";
    String drinking = "never";
    String exercise = "everyday";

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class);

    //@Test
    public void testAddCondition(){
        onView(withId(R.id.ptPriorConditionsReg)).perform(replaceText(condition));
        onView(withId(R.id.buttonPriorConditions)).perform(scrollTo(), click());
    }

    @Test
    public void testAddSmoking(){
        onView(withId(R.id.ptSmokingReg)).perform(replaceText(smoking));
        onView(withId(R.id.buttonSmoking)).perform(scrollTo(), click());
        //onView(allOf(withId(R.id.ptSmokingValue))).check(matches(withText(smoking)));

    }
}
