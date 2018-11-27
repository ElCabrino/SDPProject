package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.chat.ChatActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasBackground;
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

    @Test
    public void chatButtonTest() throws Exception {
        //Map<String, String> extras = new HashMap<>();
        //extras.put("doctorUID", "doctorid1");
        //setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, false, false, extras);
        //TimeUnit.SECONDS.sleep(1);
        Intents.init();
        onView(withId(R.id.buttonChat)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(ChatActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void takeAppointmentButtonTest() throws Exception {
        //Intents.release();
        //Map<String, String> extras = new HashMap<>();
        //extras.put("doctorUID", "doctorid1");
        //setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, false, false, extras);
        //TimeUnit.SECONDS.sleep(1);
        Intents.init();
        onView(withId(R.id.buttonTakeAppointment)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(PatientCalendarActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void simpleClickFavoriteButton() {
        Intents.init();
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
        Intents.release();
    }

    @Test
    public void doubleClickFavoriteButton() {
        Intents.init();
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xFFD6D7D7));
        wipeLocalData();
        Intents.release();
    }

    @Test
    public void alreadyInFavorite() {
        Intents.init();
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        Intent i = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(i);
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
        Intents.release();
    }

    public void wipeLocalData(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
    }

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