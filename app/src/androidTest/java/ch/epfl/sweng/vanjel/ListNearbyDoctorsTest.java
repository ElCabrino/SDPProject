package ch.epfl.sweng.vanjel;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.assertViewWithTextIsVisible;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.denyCurrentPermission;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.denyCurrentPermissionPermanently;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.grantPermission;
import static ch.epfl.sweng.vanjel.utils.UiAutomatorUtils.openPermissions;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ListNearbyDoctorsTest {

    @BeforeClass
    public static void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    @Rule
    public IntentsTestRule<ListNearbyDoctors> intentsTestRule = new IntentsTestRule<>(ListNearbyDoctors.class);

    private UiDevice device;

    @Before
    public void setUp() {
        this.device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void a_shouldDisplayPermissionRequestDialogAtStartup() throws Exception {
        assertViewWithTextIsVisible(device, "ALLOW");
        assertViewWithTextIsVisible(device, "DENY");

        // cleanup for the next test
        denyCurrentPermission(device);
    }

    @Test
    public void b_shouldDisplayShortRationaleIfPermissionWasDenied() throws Exception {
        denyCurrentPermission(device);

        onView(withText(R.string.permission_denied_rationale_short)).check(matches(isDisplayed()));
        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));
    }

    @Test
    public void c_shouldDisplayLongRationaleIfPermissionWasDeniedPermanently() throws Exception {
        denyCurrentPermissionPermanently(device);

        onView(withText(R.string.permission_denied_rationale_long)).check(matches(isDisplayed()));
        onView(withText(R.string.grant_permission)).check(matches(isDisplayed()));

        // will grant the permission for the next test
        onView(withText(R.string.grant_permission)).perform(click());
        openPermissions(device);
        grantPermission(device, "Location");
    }

    @Test
    public void doctorsAreDisplayedWhenPermissionIsGranted() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        onView(withId(R.id.listNearbyDoctors)).check(matches(hasMinimumChildCount(1)));
    }

}
