package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegistrationActivityTest {

    @Rule
    public final ActivityTestRule<RegistrationActivity> mActivityRule =
            new ActivityTestRule<>(RegistrationActivity.class);



    @Test
    public void testCanGreetUsers() {
        String email = "test@epfl.ch";
        onView(withId(R.id.mailReg)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordReg)).perform(typeText("123456")).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonReg)).perform(click());

//        assertEquals(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString()

    }



}


