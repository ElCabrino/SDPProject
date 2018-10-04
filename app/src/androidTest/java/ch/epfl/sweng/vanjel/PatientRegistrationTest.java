package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class PatientRegistrationTest {

    @Rule
    public final ActivityTestRule<PatientRegistration> mActivityRule =
            new ActivityTestRule<>(PatientRegistration.class);



    @Test
    public void testCanGreetUsers() {
//        String email = "test@epfl.ch";
//        onView(withId(R.id.mailReg)).perform(typeText(email)).perform(closeSoftKeyboard());
//        onView(withId(R.id.passwordReg)).perform(typeText("123456")).perform(closeSoftKeyboard());
//        onView(withId(R.id.buttonReg)).perform(click());

//        assertEquals(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString()

    }



}


