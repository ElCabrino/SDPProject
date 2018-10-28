package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.junit.Assert.*;

public class NearbyDoctorTest {

    @BeforeClass
    public static void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }

    @Before
    public void setUp(){
        Intents.init();
    }

    @Rule
    public ActivityTestRule<NearbyDoctor> mActivityRule =
            new ActivityTestRule<>(NearbyDoctor.class);


    @Test
    public void onCreate() {
        // TODO: give authorization
//        intended(hasComponent(NearbyDoctor.class.getName()));
    }

    @After
    public void endUp() {
        Intents.release();
    }

}