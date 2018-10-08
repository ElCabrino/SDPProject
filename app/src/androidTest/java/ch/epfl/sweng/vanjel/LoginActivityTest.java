package ch.epfl.sweng.vanjel;

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
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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
        onView(withId(R.id.registrationLogin)).perform(click());
        intended(hasComponent(ChooseRegistration.class.getName()));
    }

    @Test
    public void testLogin(){
//        String email = "admin@test.ch";
//        String password = "123456";
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

    }



}