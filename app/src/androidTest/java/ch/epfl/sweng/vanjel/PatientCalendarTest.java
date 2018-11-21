package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PatientCalendarTest {

    private String d = "Mon Oct 29 2018";

    @BeforeClass
    public static void beforeClass(){
        FirebaseAuthCustomBackend.setMockPatient(true);
        FirebaseAuthCustomBackend.setNullUser(false);
    }

    @Rule
    public final IntentsTestRule<PatientCalendarActivity> ActivityRule =
            new IntentsTestRule<PatientCalendarActivity>(PatientCalendarActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, PatientCalendarActivity.class);
                    result.putExtra("doctorUID", "doctorid1");
                    result.putExtra("date", d);
                    return result;
                }
            };

    @Test
    public void testCalendarButton(){
        onView(withId(R.id.buttonSelectSchedule)).perform(click());
    }
}
