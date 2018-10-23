package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DoctorInformationTest {

    @Rule
    public final IntentsTestRule<SearchDoctor> mActivityRule =
            new IntentsTestRule<>(SearchDoctor.class);

    @Before
    public void setUp() throws Exception{
        String lastName = "Smith";
        onView(withId(R.id.lastNameSearch)).perform(typeText(lastName)).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonSearch)).perform(scrollTo(), click());

        TimeUnit.SECONDS.sleep(5); // wait for data retrieving

        intended(hasComponent(FilteredDoctors.class.getName()));

        onView(withId(R.id.lastName)).check(matches(withText(lastName))).perform(click());


        TimeUnit.SECONDS.sleep(5); // wait for data retrieving




    }

    @Test
    public void onCreateTest(){

        intended(hasComponent(DoctorInformation.class.getName()));

    }

}