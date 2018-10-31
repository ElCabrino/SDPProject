package ch.epfl.sweng.vanjel;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.assertViewWithTextIsVisible;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.denyCurrentPermission;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.denyCurrentPermissionPermanently;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.grantPermission;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.openPermissions;
import static org.junit.Assert.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NearbyDoctorTest {

    @BeforeClass
    public static void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    @Rule
    public ActivityTestRule<NearbyDoctor> mActivityRule =
            new ActivityTestRule<>(NearbyDoctor.class);

    private UiDevice device;

    @Before
    public void setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }
    

//    @Test
//    public void a_shouldDisplayPermissionRequestDialogAtStartup() throws Exception {
//        assertViewWithTextIsVisible(device, "ALLOW");
//        assertViewWithTextIsVisible(device, "DENY");
//
//        // cleanup for the next test
//        denyCurrentPermission(device);
//    }
//
//    @Test
//    public void b_shouldDisplayShortRationaleIfPermissionWasDenied() throws Exception {
//        denyCurrentPermission(device);
//
//        onView(withText(R.string.permission_denied_rationale_short)).check(matches(isDisplayed()));
//        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));
//    }
//
//    @Test
//    public void c_shouldDisplayLongRationaleIfPermissionWasDeniedPermanently() throws Exception {
//        denyCurrentPermissionPermanently(device);
//
//        onView(withText(R.string.permission_denied_rationale_long)).check(matches(isDisplayed()));
//        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));
//
//        // will grant the permission for the next test
//        onView(withText(R.string.grant_permission)).perform(click());
//        openPermissions(device);
//        grantPermission(device, "Location");
//    }

    @Test
    public void d_shouldDoSomethingIfPermissionWasGranted() {
        onView(withId(R.id.mapViewNearbyDoctor)).check(matches(isDisplayed()));
    }

}