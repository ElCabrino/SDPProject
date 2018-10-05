package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static org.junit.Assert.assertEquals;

/**
 Test class for Patient Registration form

 author: Aslam CADER
 reviewer:
 **/

@RunWith(AndroidJUnit4.class)
public class PatientRegistrationTest {

    @Rule
    public final ActivityTestRule<PatientRegistration> mActivityRule =
            new ActivityTestRule<>(PatientRegistration.class);


    @Test
    public void testForm() {
        // Check if register without anything affect
        onView(withId(R.id.buttonPaReg)).perform(click());


        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), PatientRegistration.class.getName());
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

        onView(withId(R.id.mailPa)).perform(typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordPa)).perform(typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordPa)).perform(typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNamePa)).perform(typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNamePa)).perform(typeText(lastName)).perform(closeSoftKeyboard());
//        OnView(withId(R.id.birthdayPa)).perform(typeText(birthday)).perform(closeSoftKeyboard());
        onView(withId(R.id.streetPa)).perform(typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberPa)).perform(typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityPa)).perform(typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryPa)).perform(typeText(country)).perform(closeSoftKeyboard());
        onView(withId(R.id.postcodePa)).perform(typeText(postCode)).perform(closeSoftKeyboard());


        onView(withId(R.id.buttonPaReg)).perform(click());


        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), PatientRegistration.class.getName());


    }

//    @Test
//    public void testCanGreetUsers() {
////        String email = "test@epfl.ch";
////        onView(withId(R.id.mailReg)).perform(typeText(email)).perform(closeSoftKeyboard());
////        onView(withId(R.id.passwordReg)).perform(typeText("123456")).perform(closeSoftKeyboard());
////        onView(withId(R.id.buttonReg)).perform(click());
//
////        assertEquals(FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString()
//
//    }



}


