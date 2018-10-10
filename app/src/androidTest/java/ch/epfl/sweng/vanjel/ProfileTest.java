package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class ProfileTest {


    @Rule
    public final IntentsTestRule<LoginActivity> ActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {

//        String email = "test@test.ch";
//        String password = "123456";

//        signOutIfPossible();

//        enterEmail(email);

//        enterPassword(password);

//        onView(withId(R.id.buttonLogin)).perform(click());

 //       TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void onClickTest() {
    }

    @Test
    public void getUserTest() {
    }
}