package ch.epfl.sweng.vanjel;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PatientInfoTest {

    String condition = "Good condition";

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class);

    @Test
    public void testAddCondition(){
        onView(withId(R.id.ptPriorConditionsReg)).perform(replaceText(condition));
        onView(withId(R.id.buttonPriorConditions)).perform(click());

    }
}
