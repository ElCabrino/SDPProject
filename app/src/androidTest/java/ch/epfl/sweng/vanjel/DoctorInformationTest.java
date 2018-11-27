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

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasBackground;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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

    @Test
    public void simpleClickFavoriteButton() {
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
    }

    @Test
    public void doubleClickFavoriteButton() {
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xFFD6D7D7));
    }

    @Test
    public void alreadyInFavorite() {
        onView(withId(R.id.addToFavoriteButton)).perform(click());
        Intent i = mActivityRule.getActivity().getIntent();
        mActivityRule.finishActivity();
        mActivityRule.launchActivity(i);
        withId(R.id.addToFavoriteButton).matches(hasBackground(0xDDDDBB33));
        //reset state
        wipeLocalData();
    }

    @After
    public void end(){
        Intents.release();
    }

    public void wipeLocalData(){
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        l.nuke();
    }

}