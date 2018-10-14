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

    private String expectedLastname = "Joss";
    private String expectedName = "Dr Luca";
    private String expectedBirtday = "10/17/1991";
    private String expectedGender = "Male";
    private String expectedEmail = "luca@doctor.ch";
    private String expectedStreet = "Ancienne-Poste";
    private String expectedStreetNumber = "7";
    private String expectedCity = "Bussigny";
    private String expectedCountry = "Switzerland";

    @Rule
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
;
        String password = "testluca";

        try {
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(click());
            TimeUnit.SECONDS.sleep(5);
            onView(withId(R.id.mailLogin)).perform(replaceText(expectedEmail));
            onView(withId(R.id.passwordLogin)).perform(replaceText(password));
            onView(withId(R.id.buttonLogin)).perform(click());
        } catch (NoMatchingViewException e) {
            try {
                onView(allOf(withId(R.id.buttonLogin), withText("Login"))).check(matches(isDisplayed()));
                onView(withId(R.id.mailLogin)).perform(replaceText(expectedEmail));
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
        checkDisplayed(R.id.lastnameProfile, expectedLastname);
        checkDisplayed(R.id.nameProfile, expectedName);
        checkDisplayed(R.id.birthdayProfile, expectedBirtday);
        checkDisplayed(R.id.genderProfile, expectedGender);
        checkDisplayed(R.id.emailProfile, expectedEmail);
        checkDisplayed(R.id.streetProfile, expectedStreet);
        checkDisplayed(R.id.numberStreetProfile, expectedStreetNumber);
        checkDisplayed(R.id.cityProfile, expectedCity);
        checkDisplayed(R.id.countryProfile, expectedCountry);
    }

    @Test
    public void testEditText() {
        String newLastName = "JossEdit";
        String newName = "Dr LucaEdit";
        String newStreet = "Nouvelle-Poste";
        String newNumberStreet = "12";
        String newCity = "BussignyEdit";

        pressButton(R.id.editButton, "Edit");
        onView(allOf(withId(R.id.lastnameProfile), isEnabled())).perform(replaceText(newLastName));
        onView(allOf(withId(R.id.nameProfile), isEnabled())).perform(replaceText(newName));
        onView(allOf(withId(R.id.streetProfile), isEnabled())).perform(replaceText(newStreet));
        onView(allOf(withId(R.id.numberStreetProfile), isEnabled())).perform(replaceText(newNumberStreet));
        onView(allOf(withId(R.id.cityProfile), isEnabled())).perform(replaceText(newCity));
        pressButton(R.id.saveButton, "Save");

        checkDisplayed(R.id.lastnameProfile, newLastName);
        checkDisplayed(R.id.nameProfile, newName);
        checkDisplayed(R.id.birthdayProfile, expectedBirtday);
        checkDisplayed(R.id.genderProfile, expectedGender);
        checkDisplayed(R.id.emailProfile, expectedEmail);
        checkDisplayed(R.id.streetProfile, newStreet);
        checkDisplayed(R.id.numberStreetProfile, newNumberStreet);
        checkDisplayed(R.id.cityProfile, newCity);
        checkDisplayed(R.id.countryProfile, expectedCountry);
        restoreEditTest();
    }

    private void restoreEditTest() {
        pressButton(R.id.editButton, "Edit");
        onView(withId(R.id.lastnameProfile)).perform(replaceText(expectedLastname));
        onView(withId(R.id.nameProfile)).perform(replaceText(expectedName));
        onView(withId(R.id.streetProfile)).perform(replaceText(expectedStreet));
        onView(withId(R.id.numberStreetProfile)).perform(replaceText(expectedStreetNumber));
        onView(withId(R.id.cityProfile)).perform(replaceText(expectedCity));
        pressButton(R.id.saveButton, "Save");
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
/*        pressButton(R.id.editButton, "Edit");
        checkVisibility(R.id.editButton, "Edit", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.saveButton, "Save", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.genderProfile, expectedGender, ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.birthdayProfile, expectedBirtday, ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.emailProfile, expectedEmail, ViewMatchers.Visibility.GONE);
        checkIsEnabled(R.id.lastnameProfile, expectedLastname);
        checkIsEnabled(R.id.nameProfile, expectedName);
        checkIsEnabled(R.id.streetProfile, expectedStreet);
        checkIsEnabled(R.id.numberStreetProfile, expectedStreetNumber);
        checkIsEnabled(R.id.cityProfile, expectedCity);
        checkIsEnabled(R.id.countryProfile, expectedCountry);*/
    }

    @Test
    public void saveButtonTest() {
/*        pressButton(R.id.editButton, "Edit");
        pressButton(R.id.saveButton, "Save");
        checkVisibility(R.id.editButton, "Edit", ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.saveButton, "Save", ViewMatchers.Visibility.GONE);
        checkVisibility(R.id.genderProfile, expectedGender, ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.birthdayProfile, expectedBirtday, ViewMatchers.Visibility.VISIBLE);
        checkVisibility(R.id.emailProfile, expectedEmail, ViewMatchers.Visibility.VISIBLE);
        checkIsNotEnabled(R.id.lastnameProfile, expectedLastname);
        checkIsNotEnabled(R.id.nameProfile, expectedName);
        checkIsNotEnabled(R.id.streetProfile, expectedStreet);
        checkIsNotEnabled(R.id.numberStreetProfile, expectedStreetNumber);
        checkIsNotEnabled(R.id.cityProfile, expectedCity);
        checkIsNotEnabled(R.id.countryProfile, expectedCountry);*/
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

    private void checkIsEnabled(int id, String text) {
        try {
            onView(allOf(withId(id), withText(text))).check(matches(isDisplayed())).check(matches(isEnabled()));
            assert(true);
        } catch (NoMatchingViewException e) {
            assert(false);
        }
    }

    private void checkIsNotEnabled(int id, String text) {
        try {
            onView(allOf(withId(id), withText(text))).check(matches(isDisplayed())).check(matches(not(isEnabled())));
            assert(true);
        } catch (NoMatchingViewException e) {
            assert(false);
        }
    }
}