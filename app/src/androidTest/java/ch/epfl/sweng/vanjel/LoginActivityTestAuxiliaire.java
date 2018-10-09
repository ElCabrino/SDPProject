package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.ComponentNameMatchers.hasClassName;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 Test class for LoginActivity

 @author: Aslam CADER
 reviewer:
 **/

@RunWith(AndroidJUnit4.class)
public class LoginActivityTestAuxiliaire {

    @Rule
    public final IntentsTestRule<LoginActivity> activityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        TimeUnit.SECONDS.sleep(5);



        String email = "test@test.ch";
        String password = "123456";



        signOutIfPossible();

        enterEmail(email);

        enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());
    }


    // Check if already connected --> correct view
    @Test
    public void updateUITest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);

        intended(hasComponent(Profile.class.getName()));

        signOutIfPossible();

    }

    // check if disconnecting brings to correct activity
    @Test
    public void logoutTest() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        signOutIfPossible();

        TimeUnit.SECONDS.sleep(5);
        intended(hasComponent(LoginActivity.class.getName()));


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