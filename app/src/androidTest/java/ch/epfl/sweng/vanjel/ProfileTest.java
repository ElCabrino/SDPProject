package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 author: Luca JOSS
 reviewer:
 **/
@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    private String p_expectedLastname = "ln_ptest1";
    private String p_expectedName = "fn_ptest1";
    private String p_expectedBirtday = "01/01/2001";
    private String p_expectedGender = "Male";
    private String p_expectedEmail = "patient1@test.ch";
    private String p_expectedStreet = "street_ptest1";
    private String p_expectedStreetNumber = "1";
    private String p_expectedCity = "city_ptest1";
    private String p_expectedCountry = "country_ptest1";

    private String d_expectedLastname = "ln_dtest1";
    private String d_expectedName = "fn_dtest1";
    private String d_expectedBirtday = "11/11/2011";
    private String d_expectedGender = "Male";
    private String d_expectedEmail = "doctor1@test.ch";
    private String d_expectedStreet = "street_dtest1";
    private String d_expectedStreetNumber = "11";
    private String d_expectedCity = "city_dtest1";
    private String d_expectedCountry = "country_dtest1";

    @Rule
    public ActivityTestRule<Profile> mActivityRule =
            new ActivityTestRule<>(Profile.class);

    @Test
    public void displayPatientProfileTest() throws Exception {
        runAsPatient();
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(5);
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedLastname)));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedName)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedBirtday)));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedGender)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedEmail)));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedStreet)));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedStreetNumber)));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedCity)));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedCountry)));
    }

    @Test
    public void testEditText() throws Exception {
        runAsPatient();
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(3);
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
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());

        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText(newLastName)));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText(newName)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedBirtday)));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedGender)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedEmail)));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText(newStreet)));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText(newNumberStreet)));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText(newCity)));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText(p_expectedCountry)));
    }

    @Test
    public void editButtonTest() throws Exception {
        runAsPatient();
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(3);
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
    public void saveButtonTest() throws Exception {
        runAsPatient();
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(3);
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
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

    @Test
    public void searchDoctorButtonTest() throws Exception {
        runAsPatient();
        Intents.init();
        TimeUnit.SECONDS.sleep(3);
        onView(withContentDescription("profile search button")).perform(scrollTo(), click());
        intended(hasComponent(SearchDoctor.class.getName()));
        Intents.release();
    }

    @Test
    public void patientInfoButtonAsPatientTest() throws Exception {
        runAsPatient();
        Intents.init();
        TimeUnit.SECONDS.sleep(3);
        onView(withContentDescription("patient info button")).perform(click());
        intended(hasComponent(PatientInfo.class.getName()));
        Intents.release();
    }

    @Test
    public void patientInfoButtonAsDoctorTest() throws Exception {
        runAsDoctor();
        TimeUnit.SECONDS.sleep(3);
        onView(withContentDescription("patient info button")).perform(click());
        onView(withText("You must be a patient to access this feature")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void setAvailabilityButton() throws Exception {
        runAsDoctor();
        Intents.init();
        TimeUnit.SECONDS.sleep(3);
        onView(withContentDescription("set availability button")).perform(scrollTo(), click());
        intended(hasComponent(DoctorAvailabilityActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void displayDoctorProfileTest() throws Exception {
        runAsDoctor();
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(5);
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedLastname)));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedName)));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedBirtday)));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedGender)));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedEmail)));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedStreet)));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedStreetNumber)));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedCity)));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText(d_expectedCountry)));
    }

    // Set mock Firebase to connect as a Doctor
    private void runAsDoctor() {
        FirebaseAuthCustomBackend.setMockPatient(false);
        mActivityRule.finishActivity();
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
    }

    // Set mock Firebase to connect as a Patient
    private void runAsPatient() {
        FirebaseAuthCustomBackend.setMockPatient(true);
        mActivityRule.finishActivity();
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
    }
}