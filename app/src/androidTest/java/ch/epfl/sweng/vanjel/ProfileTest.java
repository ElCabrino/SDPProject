package ch.epfl.sweng.vanjel;

import static android.support.test.espresso.action.ViewActions.scrollTo;
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
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(scrollTo()).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(scrollTo(), click());
            TimeUnit.SECONDS.sleep(3);
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
        TimeUnit.SECONDS.sleep(15);
    }

    @Test
    public void outputTest() {
        onView(withId(R.id.lastnameProfile)).perform(scrollTo()).check(matches(withText(expectedLastname)));
        onView(withId(R.id.nameProfile)).perform(scrollTo()).check(matches(withText(expectedName)));
        onView(withId(R.id.birthdayProfile)).perform(scrollTo()).check(matches(withText(expectedBirtday)));
        onView(withId(R.id.genderProfile)).perform(scrollTo()).check(matches(withText(expectedGender)));
        onView(withId(R.id.emailProfile)).perform(scrollTo()).check(matches(withText(expectedEmail)));
        onView(withId(R.id.streetProfile)).perform(scrollTo()).check(matches(withText(expectedStreet)));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo()).check(matches(withText(expectedStreetNumber)));
        onView(withId(R.id.cityProfile)).perform(scrollTo()).check(matches(withText(expectedCity)));
        onView(withId(R.id.countryProfile)).perform(scrollTo()).check(matches(withText(expectedCountry)));
    }

    @Test
    public void testEditText() {
        String newLastName = "JossEdit";
        String newName = "Dr LucaEdit";
        String newStreet = "Nouvelle-Poste";
        String newNumberStreet = "12";
        String newCity = "BussignyEdit";

        onView(withId(R.id.editButton)).perform(scrollTo(), click());
        onView(withId(R.id.lastnameProfile)).perform(scrollTo(), replaceText(newLastName));
        onView(withId(R.id.nameProfile)).perform(scrollTo(), replaceText(newName));
        onView(withId(R.id.streetProfile)).perform(scrollTo(), replaceText(newStreet));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo(), replaceText(newNumberStreet));
        onView(withId(R.id.cityProfile)).perform(scrollTo(), replaceText(newCity));
        onView(withId(R.id.saveButton)).perform(scrollTo(), click());

        onView(withId(R.id.lastnameProfile)).perform(scrollTo()).check(matches(withText(newLastName)));
        onView(withId(R.id.nameProfile)).perform(scrollTo()).check(matches(withText(newName)));
        onView(withId(R.id.birthdayProfile)).perform(scrollTo()).check(matches(withText(expectedBirtday)));
        onView(withId(R.id.genderProfile)).perform(scrollTo()).check(matches(withText(expectedGender)));
        onView(withId(R.id.emailProfile)).perform(scrollTo()).check(matches(withText(expectedEmail)));
        onView(withId(R.id.streetProfile)).perform(scrollTo()).check(matches(withText(newStreet)));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo()).check(matches(withText(newNumberStreet)));
        onView(withId(R.id.cityProfile)).perform(scrollTo()).check(matches(withText(newCity)));
        onView(withId(R.id.countryProfile)).perform(scrollTo()).check(matches(withText(expectedCountry)));

        restoreEditTest();
    }

    private void restoreEditTest() {
        onView(withId(R.id.editButton)).perform(scrollTo(), click());
        onView(withId(R.id.lastnameProfile)).perform(scrollTo(), replaceText(expectedLastname));
        onView(withId(R.id.nameProfile)).perform(scrollTo(), replaceText(expectedName));
        onView(withId(R.id.streetProfile)).perform(scrollTo(), replaceText(expectedStreet));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo(), replaceText(expectedStreetNumber));
        onView(withId(R.id.cityProfile)).perform(scrollTo(), replaceText(expectedCity));
        onView(withId(R.id.saveButton)).perform(scrollTo(), click());
    }

    @Test
    public void editButtonTest() {
        onView(withId(R.id.editButton)).perform(scrollTo(), click());
        onView(withId(R.id.editButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.saveButton)).perform(scrollTo()).check(matches(isDisplayed())).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.genderProfile)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.birthdayProfile)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.emailProfile)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.lastnameProfile)).perform(scrollTo()).check(matches(isEnabled()));
        onView(withId(R.id.nameProfile)).perform(scrollTo()).check(matches(isEnabled()));
        onView(withId(R.id.streetProfile)).perform(scrollTo()).check(matches(isEnabled()));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo()).check(matches(isEnabled()));
        onView(withId(R.id.cityProfile)).perform(scrollTo()).check(matches(isEnabled()));
        onView(withId(R.id.countryProfile)).perform(scrollTo()).check(matches(isEnabled()));
    }

    @Test
    public void saveButtonTest() {
        onView(withId(R.id.editButton)).perform(scrollTo(), click());
        onView(withId(R.id.saveButton)).perform(scrollTo(), click());

        onView(withId(R.id.editButton)).perform(scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.saveButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withId(R.id.genderProfile)).perform(scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.birthdayProfile)).perform(scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.emailProfile)).perform(scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withId(R.id.lastnameProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
        onView(withId(R.id.nameProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
        onView(withId(R.id.streetProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
        onView(withId(R.id.numberStreetProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
        onView(withId(R.id.cityProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
        onView(withId(R.id.countryProfile)).perform(scrollTo()).check(matches(not(isEnabled())));
    }
}