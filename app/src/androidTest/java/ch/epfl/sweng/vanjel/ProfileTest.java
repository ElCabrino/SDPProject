package ch.epfl.sweng.vanjel;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;
import android.view.WindowManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
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

    @Rule
    public ActivityTestRule<Profile> mActivityRule =
            new ActivityTestRule<>(Profile.class);

    @Before
    public void unlockScreen() throws Exception {


        String password = "testluca";

        try {
            TimeUnit.SECONDS.sleep(5);
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(ViewActions.scrollTo()).check(matches(isDisplayed()));
            onView(allOf(withId(R.id.logoutButton), withText("Logout"))).perform(ViewActions.scrollTo(), click());
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
        TimeUnit.SECONDS.sleep(5);
        final Profile activity = mActivityRule.getActivity();
        Runnable wakeUpDevice = new Runnable() {
            public void run() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        };
        activity.runOnUiThread(wakeUpDevice);
    }

    @Test
    public void outputTest() {
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText(expectedLastname)));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText(expectedName)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText(expectedBirtday)));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText(expectedGender)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText(expectedEmail)));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText(expectedStreet)));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText(expectedStreetNumber)));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText(expectedCity)));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText(expectedCountry)));
    }

    @Test
    public void testEditText() {
        String newLastName = "JossEdit";
        String newName = "Dr LucaEdit";
        String newStreet = "Nouvelle-Poste";
        String newNumberStreet = "12";
        String newCity = "BussignyEdit";

        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo(), replaceText(newLastName));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo(), replaceText(newName));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo(), replaceText(newStreet));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo(), replaceText(newNumberStreet));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo(), replaceText(newCity));
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());

        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText(newLastName)));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText(newName)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText(expectedBirtday)));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText(expectedGender)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText(expectedEmail)));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText(newStreet)));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText(newNumberStreet)));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText(newCity)));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText(expectedCountry)));

        restoreEditTest();
    }

    private void restoreEditTest() {
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo(), replaceText(expectedLastname));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo(), replaceText(expectedName));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo(), replaceText(expectedStreet));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo(), replaceText(expectedStreetNumber));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo(), replaceText(expectedCity));
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());
    }

    @Test
    public void editButtonTest() {
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        onView(withContentDescription("profile edit button")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo()).check(matches(isDisplayed())).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withContentDescription("profile gender")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile birthday")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile email")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(isEnabled()));
    }

    @Test
    public void saveButtonTest() {
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());

        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile save button")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(not(isEnabled())));
    }
}