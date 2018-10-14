package ch.epfl.sweng.vanjel;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.util.Log;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;

public class ProfileTest {


    @Rule
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

        String email = "luca@doctor.ch";
        String password = "testluca";

        try {
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(click());
            TimeUnit.SECONDS.sleep(5);
            onView(withId(R.id.mailLogin)).perform(replaceText(email));
            onView(withId(R.id.passwordLogin)).perform(replaceText(password));
            onView(withId(R.id.buttonLogin)).perform(click());
        } catch (NoMatchingViewException e) {
            try {
                onView(allOf(withId(R.id.buttonLogin), withText("Login"))).check(matches(isDisplayed()));
                onView(withId(R.id.mailLogin)).perform(replaceText(email));
                onView(withId(R.id.passwordLogin)).perform(replaceText(password));
                    onView(withId(R.id.buttonLogin)).perform(click());
            } catch (NoMatchingViewException f) {
                Log.d("TESTOUT", "exception : "+f);
            }
        }

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void outputTest() {
        checkDisplayed(R.id.lastnameProfile, "Joss");
        checkDisplayed(R.id.nameProfile, "Dr Luca");
        checkDisplayed(R.id.birthdayProfile, "10/17/1991");
        checkDisplayed(R.id.genderProfile, "Male");
        checkDisplayed(R.id.emailProfile, "luca@doctor.ch");
        checkDisplayed(R.id.streetProfile, "Ancienne-Poste");
        checkDisplayed(R.id.numberStreetProfile, "7");
        checkDisplayed(R.id.cityProfile, "Bussigny");
        checkDisplayed(R.id.countryProfile, "Switzerland");
    }

    private void checkDisplayed(int id, String text) {
        try {
            onView(allOf(withId(id), withText(text))).check(matches(isDisplayed()));
            assert(true);
        } catch (NoMatchingViewException e) {
            assert(false);
        }
    }

    @Test
    public void editButtonTest() {
        pressButton(R.id.editButton, "Edit");
        checkVisibility(R.id.editButton, "Edit", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.saveButton, "Save", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.genderProfile, "Male", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.birthdayProfile, "10/17/1991", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.emailProfile, "luca@doctor.ch", ViewMatchers.Visibility.GONE);
/*        onView(withId(R.id.nameProfile)).check(matches(isEnabled()));
        onView(withId(R.id.lastnameProfile)).check(matches(isEnabled()));
        onView(withId(R.id.streetProfile)).check(matches(isEnabled()));
        onView(withId(R.id.numberStreetProfile)).check(matches(isEnabled()));
        onView(withId(R.id.cityProfile)).check(matches(isEnabled()));
        onView(withId(R.id.countryProfile)).check(matches(isEnabled()));*/
    }

    @Test
    public void saveButtonTest() {
        pressButton(R.id.editButton, "Edit");
        pressButton(R.id.saveButton, "Save");
        checkVisibility(R.id.editButton, "Edit", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.saveButton, "Save", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.genderProfile, "Male", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.birthdayProfile, "10/17/1991", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.emailProfile, "luca@doctor.ch", ViewMatchers.Visibility.VISIBLE);
/*        onView(withId(R.id.nameProfile)).check(matches(not(isEnabled())));
        onView(withId(R.id.lastnameProfile)).check(matches(not(isEnabled())));
        onView(withId(R.id.streetProfile)).check(matches(not(isEnabled())));
        onView(withId(R.id.numberStreetProfile)).check(matches(not(isEnabled())));
        onView(withId(R.id.cityProfile)).check(matches(not(isEnabled())));
        onView(withId(R.id.countryProfile)).check(matches(not(isEnabled())));*/
    }

    private void pressButton(int id, String text) {
        try {
            onView(allOf(withId(id), withText(text))).check(matches(isDisplayed()));
            onView(allOf(withId(id), withText(text))).perform(click());
        } catch (NoMatchingViewException e) {
            assert(false);
        }
    }

    private void checkVisibility(int id, String text, ViewMatchers.Visibility visib) {
        try {
            onView(allOf(withId(id), withText(text))).check(matches(isDisplayed())).check(matches(withEffectiveVisibility(visib)));
            assert(true);
        } catch (NoMatchingViewException e) {
            assert(false);
        }
    }

}