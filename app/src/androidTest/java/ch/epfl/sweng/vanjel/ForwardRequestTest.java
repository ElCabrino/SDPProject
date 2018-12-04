package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.forwardRequest.ForwardRequest;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.Matchers.not;

/**
 * @author Etienne CAQUOT
 */
public class ForwardRequestTest {
    @Rule
    public final ActivityTestRule<ForwardRequest> mActivityRule =
            new ActivityTestRule<>(ForwardRequest.class,true,false);
    @Test
    public void TestForwardedIsDisplayed() throws Exception {
        setupNoExtras(ForwardRequest.class, mActivityRule, false, true, false, false, false, false);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.forwardCardView)).check(matches(hasChildCount(1)));
    }

    @Test
    public void clickOnSeeDoctor() throws Exception {
        Intents.init();
        setupNoExtras(ForwardRequest.class, mActivityRule, false, true, false, false, false,false);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.seeForwadedDoctor)).perform(click());
        intended(hasComponent(DoctorInformation.class.getName()));
        Intents.release();
    }

    @Test
    public void clickOnDelete() throws Exception {
        setupNoExtras(ForwardRequest.class, mActivityRule, false, true, false, false, false,false);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.deleteForwardRequest)).perform(click());
        onView(withText("Deleted forwarded doctor")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}
