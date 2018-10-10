package ch.epfl.sweng.vanjel;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 author: Aslam CADER
 reviewer:
 **/
@RunWith(AndroidJUnit4.class)
public class ProfileTest {


    @Rule
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

        String email = "test@test.ch";
        String password = "123456";

        signOutIfPossible();

        enterEmail(email);

        enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());

        TimeUnit.SECONDS.sleep(5);
    }


    @Test
    public void testLogOut(){

//        onView(withId(R.id.logoutButton)).perform(click());
//
//        intended(hasComponent(LoginActivity.class.getName()));
    }

    private void enterEmail(String email) {
        onView(withId(R.id.mailLogin)).perform(replaceText(email));
    }

    private void enterPassword(String password) {
        onView(withId(R.id.passwordLogin)).perform(replaceText(password));
    }

    private void signOutIfPossible() {
        try {
            onView(withId(R.id.logoutButton)).perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }

    }

}