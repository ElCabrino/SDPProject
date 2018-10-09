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
 Test class for User class

 author: Aslam CADER
 reviewer:
 **/

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void testOpenChooseRegistration() {
        signOutIfPossible();
        onView(withId(R.id.registrationLogin)).perform(click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    @Test
    public void successfulLogin(){
          String email = "admin@test.ch";
          String password = "123456";
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
        signOutIfPossible();

        enterEmail(email);

        enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());

        //TODO: checker que t'es sur la page du login

    }

    @Test
    public void emptyEmailLogin(){
        String email = "";
        String password = "123456";

        signOutIfPossible();

        enterEmail(email);

        enterPassword(password);

        onView(withId(R.id.buttonLogin)).perform(click());

    }

    @Test
    public void emptyPasswordLogin(){

    }

    @Test
    public void wrongCredentialLogin(){

    }

    private void enterEmail(String email) {
        onView(withId(R.id.mailLogin)).perform(replaceText(email));
    }

    private void enterPassword(String password) {
        onView(withId(R.id.mailLogin)).perform(replaceText(password));
    }

    private void signOutIfPossible() {
        try {
            onView(withId(R.id.logoutButton)).perform(click());
        } catch (NoMatchingViewException e) {
            // Ignore
        }

    }
}