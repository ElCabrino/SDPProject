package ch.epfl.sweng.vanjel.registration;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.epfl.sweng.vanjel.R;
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

    private String email = "test@test.ch";
    private String password = "123456";
    private String confirmedPassword = "12345"; // not the same
    private String firstName = "John";
    private String lastName = "Smith";
    private String street = "Best avenue";
    private String streetNumber = "42";
    private String city = "Gaillard";
    private String country = "EPFL Land";
    private String postCode = "1212";

    private String birthday = "09/10/1997";

    @Test
    public void testEmptyForm(){
        // Check if register without anything affect
        onView(ViewMatchers.withId(R.id.buttonReg)).perform(scrollTo(), click());

        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());
    }

    @Test
    public void testForm() {
        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNameReg)).perform(scrollTo(), typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNameReg)).perform(scrollTo(), typeText(lastName)).perform(closeSoftKeyboard());
        onView(withId(R.id.birthdayReg)).perform(scrollTo(), typeText(birthday)).perform(closeSoftKeyboard());
        onView(withId(R.id.streetReg)).perform(scrollTo(), typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberReg)).perform(scrollTo(), typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityReg)).perform(scrollTo(), typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryReg)).perform(scrollTo(), typeText(country)).perform(closeSoftKeyboard());
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


