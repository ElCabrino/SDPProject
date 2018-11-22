package ch.epfl.sweng.vanjel;

import android.content.Intent;
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
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**

 author: Aslam CADER
 reviewer:
 **/

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginHelper helper = new LoginHelper();

    private String email = "test@test.ch";
    private String password = "123456";

    @Rule
    public final ActivityTestRule<LoginActivity> ActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void init() {
        FirebaseAuthCustomBackend.setNullUser(true);
        ActivityRule.finishActivity();
        Intent i = new Intent();
        ActivityRule.launchActivity(i);
    }

    @After
    public void reset() {
        FirebaseAuthCustomBackend.setNullUser(false);
    }


    @Test
    public void testOpenChooseRegistration() {
        helper.signOutIfPossible();
        onView(withId(R.id.registrationLogin)).perform(click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    @Test
    public void successfulLogin(){
        helper.signOutIfPossible();

        helper.enterEmail(email);

        helper.enterPassword(password);

        FirebaseAuthCustomBackend.setNullUser(false);

        onView(withId(R.id.buttonLogin)).perform(click());
    }

    @Test
    public void emptyEmailLogin() throws InterruptedException{

        FirebaseAuthCustomBackend.setNullUser(true);

        TimeUnit.SECONDS.sleep(1);
        helper.signOutIfPossible();

        TimeUnit.SECONDS.sleep(5);

        helper.enterPassword(password);

        FirebaseAuthCustomBackend.setNullUser(false);

        onView(withId(R.id.buttonLogin)).perform(click());
    }

    @Test
    public void emptyPasswordLogin() throws InterruptedException {

        helper.signOutIfPossible();
        helper.enterEmail(email);

        FirebaseAuthCustomBackend.setNullUser(false);

        onView(withId(R.id.buttonLogin)).perform(click());

        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void wrongCredentialLogin(){

        String email = "impossible@impossible.ch";
        String password = "impossiblePassword";

        helper.signOutIfPossible();
        helper.enterEmail(email);
        helper.enterPassword(password);
    }
}
