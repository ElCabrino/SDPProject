package ch.epfl.sweng.vanjel.searchDoctor;

import android.os.Bundle;
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

import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;

@RunWith(AndroidJUnit4.class)
public class SearchDoctorTest {

    String firstName = "Luca";
    String lastName = "Joss";
    String specialisation = "ORL";
    String city = "Morges";

    static Map<String, Boolean> extras = new HashMap<>();

    @Rule
    public final ActivityTestRule<SearchDoctor> ActivityRule =
            new ActivityTestRule<>(SearchDoctor.class, true, false);

    @BeforeClass
    public static void initIntents() {
        Intents.init();
        extras.put("isForward", false);
    }

    @AfterClass
    public static void releaseIntents() {
        restoreMockFlags();
        Intents.release();
    }

    @Test
    public void getFieldStringsTest() {
        setupWithExtras(SearchDoctor.class, ActivityRule, false, true, false, false, false, false, false, new HashMap<String, String>(), extras);
        fillFields();

        onView(ViewMatchers.withId(R.id.firstNameSearch)).perform(scrollTo()).check(matches(withText(firstName)));
        onView(withId(R.id.lastNameSearch)).perform(scrollTo()).check(matches(withText(lastName)));
        onView(withId(R.id.specialisationSearch)).perform(scrollTo()).check(matches(withText(specialisation)));
        onView(withId(R.id.citySearch)).perform(scrollTo()).check(matches(withText(city)));
    }

    @Test
    public void searchDoctorTest() {
        setupWithExtras(SearchDoctor.class, ActivityRule, false, true, false, false, false, false, false, new HashMap<String, String>(), extras);
        Bundle b = new Bundle();
        b.putString("lastName", lastName);
        b.putString("firstName", firstName);
        b.putString("specialisation", specialisation);
        b.putString("city", city);

        fillFields();
        onView(withId(R.id.buttonSearch)).perform(scrollTo(), click());
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