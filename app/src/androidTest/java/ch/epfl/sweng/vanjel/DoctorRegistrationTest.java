package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DoctorRegistrationTest {

    @Rule
    public final ActivityTestRule<Registration> mActivityRule =
            new ActivityTestRule<>(Registration.class, true, false);


    @Test
    public void testForm() throws Exception {
        setupNoExtras(Registration.class, mActivityRule, false, false, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        // Check if register without anything affect
        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());


        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());

        String email = "test@test.ch";
        String password = "123456";
        String confirmedPassword = "123455"; // not the same
        String firstName = "John";
        String lastName = "Smith";
        String street = "Best avenue";
        String streetNumber = "42";
        String city = "Gaillard";
        String country = "EPFL Land";
        String postCode = "1212";

        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNameReg)).perform(scrollTo(), typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNameReg)).perform(scrollTo(), typeText(lastName)).perform(closeSoftKeyboard());
        onView(withId(R.id.streetReg)).perform(scrollTo(), typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberReg)).perform(scrollTo(), typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityReg)).perform(scrollTo(), typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryReg)).perform(scrollTo(), typeText(country)).perform(closeSoftKeyboard());
        onView(withId(R.id.postcodeReg)).perform(scrollTo(), typeText(postCode)).perform(closeSoftKeyboard());

        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());

        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}
