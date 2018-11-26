package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.chat.ChatActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class DoctorInformationTest {

    @Rule
    public final ActivityTestRule<DoctorInformation> mActivityRule =
            new ActivityTestRule<>(DoctorInformation.class, true, false);

    @Test
    public void chatButtonTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("doctorUID", "doctorid1");
        setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, false, false, extras);
        TimeUnit.SECONDS.sleep(1);
        Intents.init();
        onView(withId(R.id.buttonChat)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(ChatActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void takeAppointmentButtonTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("doctorUID", "doctorid1");
        setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, false, false, extras);
        TimeUnit.SECONDS.sleep(1);
        Intents.init();
        onView(withId(R.id.buttonTakeAppointment)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(PatientCalendarActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void getDocWithUIDCancelledTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("doctorUID", "doctorid1");
        TimeUnit.SECONDS.sleep(3);
        setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, true, false, extras);
        onView(withText(R.string.database_error)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void restore(){
        restoreMockFlags();
    }
}