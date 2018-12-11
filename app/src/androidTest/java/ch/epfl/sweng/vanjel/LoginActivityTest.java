package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.login.LoginActivity;
import ch.epfl.sweng.vanjel.mainMenu.MainMenu;
import ch.epfl.sweng.vanjel.registration.ChooseRegistration;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

/**

 author: Aslam CADER
 reviewer: Etienne CAQUOT
 **/

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginHelper helper = new LoginHelper();

    private String email = "test@test.ch";
    private String password = "123456";

    @Rule
    public final ActivityTestRule<LoginActivity> ActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

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
    public void testOpenChooseRegistration() {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false);
        onView(withId(R.id.registrationLogin)).perform(click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    @Test
    public void successfulLogin(){
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false);
        helper.enterEmail(email);
        helper.enterPassword(password);
        onView(withId(R.id.buttonLogin)).perform(click());
        intended(hasComponent(MainMenu.class.getName()));

    }

    @Test
    public void emptyEmailLogin() {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false);
        helper.enterPassword(password);
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withId(R.id.mailLogin)).check(matches(hasErrorText("Required.")));

    }

    @Test
    public void emptyPasswordLogin() {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,false,false,false,false);
        helper.enterEmail(email);
        onView(withId(R.id.buttonLogin)).perform(click());
        onView(withId(R.id.passwordLogin)).check(matches(hasErrorText("Required.")));
    }

    @Test
    public void wrongCredentialLogin() {
        setupNoExtras(LoginActivity.class,ActivityRule,true,false,true,false,false,false);
        helper.enterEmail(email);
        helper.enterPassword(password);
        onView(withId(R.id.buttonLogin)).perform(click());
       // onView(withText("Authentication failed.")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }
}
