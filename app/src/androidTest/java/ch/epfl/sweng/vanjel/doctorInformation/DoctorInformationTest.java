package ch.epfl.sweng.vanjel.doctorInformation;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.chat.ChatActivity;
import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;
import ch.epfl.sweng.vanjel.patientAppointment.PatientCalendarActivity;

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

    static Map<String, String> extras = new HashMap<>();

    @BeforeClass
    public static void setup() {
        Intents.init();

        extras.put("doctorUID", "doctorid1");
    }

    @AfterClass
    public static void restore() {
        Intents.release();
        restoreMockFlags();
    }

    @Rule
    public final ActivityTestRule<DoctorInformation> mActivityRule =
            new ActivityTestRule<>(DoctorInformation.class, true, false);

    @Test
    public void chatButtonTest() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, true, false, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(5);
        onView(ViewMatchers.withId(R.id.buttonChat)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(ChatActivity.class.getName()));
    }

    @Test
    public void takeAppointmentButtonTest() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, true, false, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.buttonTakeAppointment)).perform(click());
        TimeUnit.SECONDS.sleep(5); // wait to change page
        intended(hasComponent(PatientCalendarActivity.class.getName()));
    }

    @Test
    public void simpleClickFavoriteButton() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, true, false, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
    }

    @Test
    public void doubleClickFavoriteButton() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, true, false, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xFFD6D7D7));
        wipeLocalData();
    }

    @Test
    public void alreadyInFavorite() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, true, false, false, false, false, false, extras, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        Intent i = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(i);
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
    }

    public void wipeLocalData(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
    }

    @Test
    public void getDocWithUIDCancelledTest() throws Exception {
        setupWithExtras(DoctorInformation.class, mActivityRule, false, false, false, true, false, false, false, extras, new HashMap<String, Boolean>());
        onView(withText(R.string.database_error)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}