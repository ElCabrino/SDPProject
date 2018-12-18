package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.profile.Profile;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;
import static org.hamcrest.core.IsNot.not;

/**
 author: Luca JOSS
 reviewer: Etienne CAQUOT
 **/
@RunWith(AndroidJUnit4.class)
public class ProfileTest {

    private final String p_expectedBirtday = "01/01/2001";
    private final String p_expectedGender = "Male";
    private final String p_expectedEmail = "patient1@test.ch";
    private final String p_expectedCountry = "country_ptest1";


    private static final Map<String, String> patient = new HashMap<>();
    private static final Map<String, String> doctor = new HashMap<>();

    @Rule
    public final ActivityTestRule<Profile> mActivityRule =
            new ActivityTestRule<>(Profile.class, true, false);

    @BeforeClass
    public static void setUp() {
        Intents.init();
        patient.put("userType","Patient");
        doctor.put("userType","Doctor");
    }

    @AfterClass
    public static void restoreIntents() {
        Intents.release();
        restoreMockFlags();
    }

    @Test
    public void displayPatientProfileTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, false, false, false, false, patient, new HashMap<String, Boolean>());
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        String p_expectedLastname = "ln_ptest1";
        onView(withContentDescription("profile last name")).check(matches(withText(p_expectedLastname)));
        String p_expectedName = "fn_ptest1";
        onView(withContentDescription("profile name")).check(matches(withText(p_expectedName)));
        onView(withContentDescription("profile birthday")).check(matches(withText(p_expectedBirtday)));
        onView(withContentDescription("profile gender")).check(matches(withText(p_expectedGender)));
        onView(withContentDescription("profile email")).check(matches(withText(p_expectedEmail)));
        String p_expectedStreet = "street_ptest1";
        onView(withContentDescription("profile street")).check(matches(withText(p_expectedStreet)));
        String p_expectedStreetNumber = "1";
        onView(withContentDescription("profile street number")).check(matches(withText(p_expectedStreetNumber)));
        String p_expectedCity = "city_ptest1";
        onView(withContentDescription("profile city")).check(matches(withText(p_expectedCity)));
        onView(withContentDescription("profile country")).check(matches(withText(p_expectedCountry)));
    }

    @Test
    public void EditTextTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, false, false, false, false, patient, new HashMap<String, Boolean>());
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        String newLastName = "JossEdit";
        String newName = "Dr LucaEdit";
        String newStreet = "Nouvelle-Poste";
        String newNumberStreet = "12";
        String newCity = "BussignyEdit";

        onView(withContentDescription("profile edit button")).perform(click());
        onView(withContentDescription("profile last name")).perform(replaceText(newLastName));
        onView(withContentDescription("profile name")).perform(replaceText(newName));
        onView(withContentDescription("profile street")).perform(replaceText(newStreet));
        onView(withContentDescription("profile street number")).perform(replaceText(newNumberStreet));
        onView(withContentDescription("profile city")).perform(replaceText(newCity));
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(click());

        onView(withContentDescription("profile last name")).check(matches(withText(newLastName)));
        onView(withContentDescription("profile name")).check(matches(withText(newName)));
        onView(withContentDescription("profile birthday")).check(matches(withText(p_expectedBirtday)));
        onView(withContentDescription("profile gender")).check(matches(withText(p_expectedGender)));
        onView(withContentDescription("profile email")).check(matches(withText(p_expectedEmail)));
        onView(withContentDescription("profile street")).check(matches(withText(newStreet)));
        onView(withContentDescription("profile street number")).check(matches(withText(newNumberStreet)));
        onView(withContentDescription("profile city")).check(matches(withText(newCity)));
        onView(withContentDescription("profile country")).check(matches(withText(p_expectedCountry)));
    }

    @Test
    public void editButtonTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, false, false, false, false, patient, new HashMap<String, Boolean>());
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile edit button")).perform(click());
        onView(withContentDescription("profile edit button")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile save button")).check(matches(isDisplayed())).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withContentDescription("profile gender")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile birthday")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withContentDescription("profile email")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withContentDescription("profile last name")).check(matches(isEnabled()));
        onView(withContentDescription("profile name")).check(matches(isEnabled()));
        onView(withContentDescription("profile street")).check(matches(isEnabled()));
        onView(withContentDescription("profile street number")).check(matches(isEnabled()));
        onView(withContentDescription("profile city")).check(matches(isEnabled()));
        onView(withContentDescription("profile country")).check(matches(isEnabled()));
    }

    @Test
    public void saveButtonTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, false, false, false, false, patient,new HashMap<String, Boolean>());
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile edit button")).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(click());
        onView(withText("User successfully updated.")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

        onView(withContentDescription("profile edit button")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile save button")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));

        onView(withContentDescription("profile gender")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile birthday")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withContentDescription("profile email")).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        onView(withContentDescription("profile last name")).check(matches(not(isEnabled())));
        onView(withContentDescription("profile name")).check(matches(not(isEnabled())));
        onView(withContentDescription("profile street")).check(matches(not(isEnabled())));
        onView(withContentDescription("profile street number")).check(matches(not(isEnabled())));
        onView(withContentDescription("profile city")).check(matches(not(isEnabled())));
        onView(withContentDescription("profile country")).check(matches(not(isEnabled())));
    }

    @Test
    public void displayDoctorProfileTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, false, false, false, false, false, doctor, new HashMap<String, Boolean>());
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        String d_expectedLastname = "ln_dtest1";
        onView(withContentDescription("profile last name")).check(matches(withText(d_expectedLastname)));
        String d_expectedName = "fn_dtest1";
        onView(withContentDescription("profile name")).check(matches(withText(d_expectedName)));
        String d_expectedBirtday = "11/11/2011";
        onView(withContentDescription("profile birthday")).check(matches(withText(d_expectedBirtday)));
        String d_expectedGender = "Male";
        onView(withContentDescription("profile gender")).check(matches(withText(d_expectedGender)));
        String d_expectedEmail = "doctor1@test.ch";
        onView(withContentDescription("profile email")).check(matches(withText(d_expectedEmail)));
        String d_expectedStreet = "street_dtest1";
        onView(withContentDescription("profile street")).check(matches(withText(d_expectedStreet)));
        String d_expectedStreetNumber = "11";
        onView(withContentDescription("profile street number")).check(matches(withText(d_expectedStreetNumber)));
        String d_expectedCity = "city_dtest1";
        onView(withContentDescription("profile city")).check(matches(withText(d_expectedCity)));
        String d_expectedCountry = "country_dtest1";
        onView(withContentDescription("profile country")).check(matches(withText(d_expectedCountry)));
    }

    @Test
    public void createValueEventListenerCancelled() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, false, true, false, false, patient, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile last name")).check(matches(withText("")));
        onView(withContentDescription("profile name")).check(matches(withText("")));
        onView(withContentDescription("profile birthday")).check(matches(withText("")));
        onView(withContentDescription("profile gender")).check(matches(withText("")));
        onView(withContentDescription("profile email")).check(matches(withText("")));
        onView(withContentDescription("profile street")).check(matches(withText("")));
        onView(withContentDescription("profile street number")).check(matches(withText("")));
        onView(withContentDescription("profile city")).check(matches(withText("")));
        onView(withContentDescription("profile country")).check(matches(withText("")));
    }

    @Test
    public void saveNewValuesFailureTest() throws Exception {
        setupWithExtras(Profile.class, mActivityRule, false, true, true, false, false, false, patient, new HashMap<String, Boolean>());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile edit button")).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(click());
        onView(withText("Failed to update user.")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}