package ch.epfl.sweng.vanjel.login;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.mainMenu.MainMenu;
import ch.epfl.sweng.vanjel.registration.ChooseRegistration;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.Matchers.not;

/**

 author: Aslam CADER
 reviewer: Etienne CAQUOT
 **/

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private String email = "test@test.ch";
    private String password = "123456";
    private String wrongPassword = "1234";

    @Rule
    public final ActivityTestRule<LoginActivity> ActivityRule =
            new ActivityTestRule<>(LoginActivity.class, true, false);

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
    public void testOpenChooseRegistration() throws Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.registrationLogin)).perform(scrollTo(), click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    // Test ignored because of an issue when mocking addOnCompleteListener().
    @Ignore
    @Test
    public void successfulLogin() throws  Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.mailLogin)).perform(scrollTo(), replaceText(email));
        onView(withId(R.id.passwordLogin)).perform(scrollTo(), replaceText(password));
        FirebaseAuthCustomBackend.setNullUser(false);
        onView(withId(R.id.buttonLogin)).perform(scrollTo(), click());
        intended(hasComponent(MainMenu.class.getName()));

    }

    @Test
    public void emptyEmailLogin() throws Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.passwordLogin)).perform(scrollTo(), replaceText(password));
        onView(withId(R.id.buttonLogin)).perform(scrollTo(), click());
        onView(withId(R.id.mailLogin)).check(matches(hasErrorText("Required.")));

    }

    @Test
    public void emptyPasswordLogin() throws Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.mailLogin)).perform(scrollTo(), replaceText(email));
        onView(withId(R.id.buttonLogin)).perform(scrollTo(), click());
        onView(withId(R.id.passwordLogin)).check(matches(hasErrorText("Required.")));
    }

    @Test
    public void automaticallyLogin() throws Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,false,false,false,false,false,false, false);
        TimeUnit.SECONDS.sleep(1);
        intended(hasComponent(MainMenu.class.getName()));
    }

    // Test ignored because of an issue when mocking addOnCompleteListener().
    @Ignore
    @Test
    public void wrongCredentialLogin() throws Exception {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,true,false,false,false, true);
        TimeUnit.SECONDS.sleep(1);
        onView(ViewMatchers.withId(R.id.mailLogin)).perform(scrollTo(), replaceText(email));
        onView(withId(R.id.passwordLogin)).perform(scrollTo(), replaceText(wrongPassword));
        onView(withId(R.id.buttonLogin)).perform(scrollTo(), click());
        onView(withText("Authentication failed.")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
