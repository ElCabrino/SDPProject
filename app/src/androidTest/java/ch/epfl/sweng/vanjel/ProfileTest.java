package ch.epfl.sweng.vanjel;

import android.support.test.espresso.action.ViewActions;
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

import ch.epfl.sweng.vanjel.chat.ChatListActivity;
import ch.epfl.sweng.vanjel.favoriteList.PatientFavoriteListActivity;
import ch.epfl.sweng.vanjel.favorite.LocalDatabaseService;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

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
            new ActivityTestRule<>(Profile.class, true, false);

    @BeforeClass
    public static void setUp() {
        Intents.init();
    }

    @AfterClass
    public static void restoreIntents() {
        Intents.release();
        restoreMockFlags();
    }

    @Test
    public void displayPatientProfileTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
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
    public void EditTextTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
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
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
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
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());
        onView(withText("User successfully updated.")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

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
        Map<String, String> extras = new HashMap<>();
        extras.put("doctor1Forward", "fn_dtest1");
        extras.put("patientForward","fn_ptest1");
        Map<String, Boolean> extrasBoolean = new HashMap<>();
        extrasBoolean.put("isForward",false);
        setupWithExtras(Profile.class, mActivityRule, false, true, false, false, false, false, extras, extrasBoolean);
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile search button")).perform(scrollTo(), click());
        intended(hasComponent(SearchDoctor.class.getName()));
    }

    @Test
    public void patientInfoButtonAsPatientTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.patientInfoButton)).perform(scrollTo(), click());
        intended(hasComponent(PatientInfo.class.getName()));
    }

    @Test
    public void patientInfoButtonAsDoctorTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(3);
        onView(withId(R.id.patientInfoButton)).perform(scrollTo(), click());
        onView(withText("You must be a patient to access this feature")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void setAvailabilityButton() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("set availability button")).perform(scrollTo(), click());
        intended(hasComponent(DoctorAvailabilityActivity.class.getName()));
    }

    @Test
    public void nearbyDoctorButtonTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.nearbyDoctorButton)).perform(scrollTo(), click());
        intended(hasComponent(NearbyDoctor.class.getName()));
    }

    @Test
    public void treatedPatientsButtonTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.treatedPatientsButton)).perform(scrollTo(), click());
        intended(hasComponent(TreatedPatients.class.getName()));
    }

    @Test
    public void favoriteListButtonTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.favoriteListButton)).perform(scrollTo(), click());
        intended(hasComponent(PatientFavoriteListActivity.class.getName()));
    }

    @Test
    public void displayDoctorProfileTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        // The app needs a few seconds to load the content
        TimeUnit.SECONDS.sleep(1);
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

    @Test
    public void nextAppointmentsAsPatientTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonNextAppointments)).perform(scrollTo(), click());
        intended(hasComponent(PatientPersonalAppointments.class.getName()));
    }

    @Test
    public void nextAppointmentsAsDoctorTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonNextAppointments)).perform(scrollTo(), click());
        intended(hasComponent(DoctorComingAppointments.class.getName()));

    }

    @Test
    public void requestsListTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.requestsListButton)).perform(scrollTo(), click());
        intended(hasComponent(DoctorAppointmentsList.class.getName()));
    }

    @Test
    public void chatAccessTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.chats)).perform(scrollTo(), click());
        intended(hasComponent(ChatListActivity.class.getName()));
    }

    @Test
    public void isPatientUserCancelledTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, true, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText("")));
    }

    @Test
    public void logOutTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.logoutButton)).perform(scrollTo(), click());
        //verifiy if local favorite are erased
        LocalDatabaseService l = new LocalDatabaseService(mActivityRule.getActivity().getApplicationContext());
        assertEquals(0, l.getAll().size());
    }

    @Test
    public void createValueEventListenerCancelled() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, false, true, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile last name")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile name")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile birthday")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile gender")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile email")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile street")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile street number")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile city")).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withContentDescription("profile country")).perform(ViewActions.scrollTo()).check(matches(withText("")));
    }

    @Test
    public void saveNewValuesFailureTest() throws Exception {
        setupNoExtras(Profile.class, mActivityRule, false, true, true, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile edit button")).perform(ViewActions.scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
        onView(withContentDescription("profile save button")).perform(ViewActions.scrollTo(), click());
        onView(withText("Failed to update user.")).inRoot(withDecorView(Matchers.not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}