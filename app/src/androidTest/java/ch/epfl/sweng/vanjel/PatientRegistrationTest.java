package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.registration.Registration;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

/**
 Test class for Patient Registration form

 author: Aslam CADER
 reviewer:
 **/

@RunWith(AndroidJUnit4.class)
public class PatientRegistrationTest {

    @Rule
    public final ActivityTestRule<Registration> mActivityRule =
            new ActivityTestRule<>(Registration.class);

    private final String email = "test@test.ch";

    @Test
    public void testEmptyForm(){
        // Check if register without anything affect
        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());

        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());
    }

    @Test
    public void testForm() {
        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());
        String password = "123456";
        onView(withId(R.id.passwordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
        // not the same
        String confirmedPassword = "12345";
        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText(confirmedPassword)).perform(closeSoftKeyboard());
        String firstName = "John";
        onView(withId(R.id.firstNameReg)).perform(scrollTo(), typeText(firstName)).perform(closeSoftKeyboard());
        String lastName = "Smith";
        onView(withId(R.id.lastNameReg)).perform(scrollTo(), typeText(lastName)).perform(closeSoftKeyboard());
        String birthday = "09/10/1997";
        onView(withId(R.id.birthdayReg)).perform(scrollTo(), typeText(birthday)).perform(closeSoftKeyboard());
        String street = "Best avenue";
        onView(withId(R.id.streetReg)).perform(scrollTo(), typeText(street)).perform(closeSoftKeyboard());
        String streetNumber = "42";
        onView(withId(R.id.numberReg)).perform(scrollTo(), typeText(streetNumber)).perform(closeSoftKeyboard());
        String city = "Gaillard";
        onView(withId(R.id.cityReg)).perform(scrollTo(), typeText(city)).perform(closeSoftKeyboard());
        String country = "EPFL Land";
        onView(withId(R.id.countryReg)).perform(scrollTo(), typeText(country)).perform(closeSoftKeyboard());
        String postCode = "1212";
        onView(withId(R.id.postcodeReg)).perform(scrollTo(), typeText(postCode)).perform(closeSoftKeyboard());

        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());

        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());

        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText("6")).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());
    }

    @Test
    public void notValidFormTest(){
        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());

        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());
    }
}


