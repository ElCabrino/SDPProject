package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.searchDoctor.SearchDoctor;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SearchDoctorTest {

    private final String firstName = "Luca";
    private final String lastName = "Joss";
    private final String specialisation = "ORL";
    private final String city = "Morges";

    @Rule
    public final ActivityTestRule<SearchDoctor> ActivityRule =
            new ActivityTestRule<SearchDoctor>(SearchDoctor.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
                    Intent result = new Intent(targetContext, SearchDoctor.class);
                    result.putExtra("isForward", false);
                    return result;
                }
            };

    @Before
    public void initIntents() {
        Intents.init();
    }

    @After
    public void releaseIntents() {
        Intents.release();
    }

    @Test
    public void getFieldStringsTest() {
        fillFields();

        onView(withId(R.id.firstNameSearch)).check(matches(withText(firstName)));
        onView(withId(R.id.lastNameSearch)).check(matches(withText(lastName)));
        onView(withId(R.id.specialisationSearch)).check(matches(withText(specialisation)));
        onView(withId(R.id.citySearch)).check(matches(withText(city)));
    }

    @Test
    public void searchDoctorTest() {
        Bundle b = new Bundle();
        b.putString("lastName", lastName);
        b.putString("firstName", firstName);
        b.putString("specialisation", specialisation);
        b.putString("city", city);

        fillFields();
        onView(withId(R.id.buttonSearch)).perform(click());
        intended(hasExtra("lastName", lastName));
        intended(hasExtra("firstName", firstName));
        intended(hasExtra("specialisation", specialisation));
        intended(hasExtra("city", city));
    }

    private void enterFirstName(String firstName) {
        onView(withId(R.id.firstNameSearch)).perform(replaceText(firstName));
    }

    private void enterLastName(String lastName) {
        onView(withId(R.id.lastNameSearch)).perform(replaceText(lastName));
    }

    private void enterSpecialisation(String spec) {
        onView(withId(R.id.specialisationSearch)).perform(replaceText(spec));
    }

    private void enterCity(String city) {
        onView(withId(R.id.citySearch)).perform(replaceText(city));
    }

    private void fillFields() {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterSpecialisation(specialisation);
        enterCity(city);
    }
}