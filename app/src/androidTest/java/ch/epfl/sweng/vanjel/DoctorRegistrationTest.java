package ch.epfl.sweng.vanjel;


import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DoctorRegistrationTest {

    @Rule
    public final ActivityTestRule<DoctorRegistration> mActivityRule =
            new ActivityTestRule<>(DoctorRegistration.class);


    @Test
    public void testForm() {
        // Check if register without anything affect
//        onView(withId(R.id.buttonDocReg)).perform(ViewActions.scrollTo()).check(ViewAssertions‌​.matches(isDisplayed()));
        onView(withId(R.id.buttonDocReg)).perform(scrollTo(), click());


        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), DoctorRegistration.class.getName());
//        intended(hasComponent(PatientRegistration.class.getName()));


        String email = "test@test.ch";
        String password = "123456";
        String confirmedPassword = "123455"; // not the same
        String firstName = "John";
        String lastName = "Smith";
        String birthday = "27/09/2018";
        String street = "Best avenue";
        String streetNumber = "42";
        String city = "Gaillard";
        String country = "EPFL Land";
        String postCode = "1212";

        onView(withId(R.id.mailDoc)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordDoc)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordDoc)).perform(scrollTo(), typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNameDoc)).perform(scrollTo(), typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNameDoc)).perform(scrollTo(), typeText(lastName)).perform(closeSoftKeyboard());
//        OnView(withId(R.id.birthdayDoc)).perform(scrollTo(), typeText(birthday)).perform(closeSoftKeyboard());
        onView(withId(R.id.streetDoc)).perform(scrollTo(), typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberDoc)).perform(scrollTo(), typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityDoc)).perform(scrollTo(), typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryDoc)).perform(scrollTo(), typeText(country)).perform(closeSoftKeyboard());
        onView(withId(R.id.postcodeDoc)).perform(scrollTo(), typeText(postCode)).perform(closeSoftKeyboard());


        onView(withId(R.id.buttonDocReg)).perform(scrollTo(), click());


        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), DoctorRegistration.class.getName());


    }

//    @Test
//    public void testCanGreetUsers() {
//
//    }
}
