package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DoctorInformationTest {

    @Rule
    public final ActivityTestRule<DoctorInformation> mActivityRule =
            new ActivityTestRule<DoctorInformation>(DoctorInformation.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, DoctorInformation.class);
                    result.putExtra("doctorUID", "doctorid1");
                    return result;
                }
            };

/*    @Before
    public void setUp() throws Exception{

        Intents.init();

        String lastName = "Smith";
        onView(withId(R.id.lastNameSearch)).perform(typeText(lastName)).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonSearch)).perform(scrollTo(), click());

        TimeUnit.SECONDS.sleep(5); // wait for data retrieving

        intended(hasComponent(FilteredDoctors.class.getName()));

        //onView(withContentDescription("doctor information last name")).check(matches(withText(lastName))).perform(click());


        TimeUnit.SECONDS.sleep(5); // wait for data retrieving




    }*/

    @Before
    public void setUp() {
        Intents.init();

    }

    @Test
    public void onCreateTest() {
//        TimeUnit.SECONDS.sleep(10);
//        intended(hasComponent(DoctorInformation.class.getName()));

    }

    @Test
    public void takeAppointmentButtonTest() throws Exception{
        onView(withId(R.id.buttonTakeAppointment)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(PatientCalendarActivity.class.getName()));
    }

    @After
    public void end(){
        Intents.release();
    }

}