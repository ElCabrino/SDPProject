package ch.epfl.sweng.vanjel;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
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
        onView(withId(R.id.buttonDocReg)).perform(click());


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

        onView(withId(R.id.mailDoc)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordDoc)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordDoc)).perform(typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNameDoc)).perform(typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNameDoc)).perform(typeText(lastName)).perform(closeSoftKeyboard());
//        OnView(withId(R.id.birthdayDoc)).perform(typeText(birthday)).perform(closeSoftKeyboard());
        onView(withId(R.id.streetDoc)).perform(typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberDoc)).perform(typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityDoc)).perform(typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryDoc)).perform(typeText(country)).perform(closeSoftKeyboard());
        onView(withId(R.id.postcodeDoc)).perform(typeText(postCode)).perform(closeSoftKeyboard());


        onView(withId(R.id.buttonDocReg)).perform(click());


        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), DoctorRegistration.class.getName());


    }

//    @Test
//    public void testCanGreetUsers() {
//
//    }
}
