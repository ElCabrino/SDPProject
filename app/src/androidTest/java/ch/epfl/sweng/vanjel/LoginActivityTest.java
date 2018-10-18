package ch.epfl.sweng.vanjel;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void testOpenChooseRegistration() {
        helper.signOutIfPossible();
        onView(withId(R.id.registrationLogin)).perform(click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    @Test
    public void successfulLogin(){
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("Users").child(database.getReference().getCurrentUser().getUid());
//
//        onView(withId(R.id.mailLogin)).perform(typeText(email)).perform(closeSoftKeyboard());
//        onView(withId(R.id.passwordLogin)).perform(typeText(password)).perform(closeSoftKeyboard());
//        onView(withId(R.id.buttonLogin)).perform(click());
//
//        // User data retrieved from Database
//        User user = dataSnapshot.getValue(User.class);
//
//        assertEquals("Check if corrected to correct account", email, user.getEmail());
//        intended(hasComponent(Profile.class.getName()));
        try {
            onView(withId(R.id.logoutButton)).perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }

        helper.signOutIfPossible();

        helper.enterEmail(email);

        helper.enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());

        // checker que t'es sur la page du profile
//        intended(hasComponent(Profile.class.getName()));



    }

    @Test
    public void emptyEmailLogin(){

        helper.signOutIfPossible();

        helper.enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());


//        intended(hasComponent(LoginActivity.class.getName()));

    }

    @Test
    public void emptyPasswordLogin() throws InterruptedException {

        helper.signOutIfPossible();
        helper.enterEmail(email);

        onView(withId(R.id.buttonLogin)).perform(click());

        TimeUnit.SECONDS.sleep(5);

//        intended(hasComponent(LoginActivity.class.getName()));


    }

    @Test
    public void wrongCredentialLogin(){

        String email = "impossible@impossible.ch";
        String password = "impossiblePassword";

        helper.signOutIfPossible();
        helper.enterEmail(email);
        helper.enterPassword(password);


//        intended(hasComponent(LoginActivity.class.getName()));

    }
}
