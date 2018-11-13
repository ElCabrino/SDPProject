package ch.epfl.sweng.vanjel;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
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
        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());

        assertEquals("Unexpected Activity before adding elements", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());
//        intended(hasComponent(PatientRegistration.class.getName()));
    }

    @Test
    public void testForm() {
        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());
        onView(withId(R.id.passwordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText(confirmedPassword)).perform(closeSoftKeyboard());
        onView(withId(R.id.firstNameReg)).perform(scrollTo(), typeText(firstName)).perform(closeSoftKeyboard());
        onView(withId(R.id.lastNameReg)).perform(scrollTo(), typeText(lastName)).perform(closeSoftKeyboard());
        onView(withId(R.id.birthdayReg)).perform(scrollTo(), typeText(birthday)).perform(closeSoftKeyboard());
//        onView(withId(R.id.birthdayReg)).perform(scrollTo(), click());
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, month , day));
//        onView(withId(android.R.id.button1)).perform(click());
//        onView(withId(R.id.birthdayReg)).check(matches(withText(day + "/" + month + "/" + year)));
        onView(withId(R.id.streetReg)).perform(scrollTo(), typeText(street)).perform(closeSoftKeyboard());
        onView(withId(R.id.numberReg)).perform(scrollTo(), typeText(streetNumber)).perform(closeSoftKeyboard());
        onView(withId(R.id.cityReg)).perform(scrollTo(), typeText(city)).perform(closeSoftKeyboard());
        onView(withId(R.id.countryReg)).perform(scrollTo(), typeText(country)).perform(closeSoftKeyboard());
        onView(withId(R.id.postcodeReg)).perform(scrollTo(), typeText(postCode)).perform(closeSoftKeyboard());
//

        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());


        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());

//        assertEquals("Unexpected Activity after putting data", mActivityRule.getActivity().getClass().getName(), Registration.class.getName());
        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText("6")).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());
    }

    @Test
    public void notValidFormTest(){

//        String email = "test.ch";
//        String password = "123456";
//        String confirmedPassword = "123455"; // not the same
//        String firstName = "John";
//        String lastName = "Smith";
//        String street = "Best avenue";
//        String streetNumber = "42";
//        String city = "Gaillard";
//        String country = "EPFL Land";
//        String postCode = "1212";

        onView(withId(R.id.mailReg)).perform(scrollTo(), typeText(email)).perform(closeSoftKeyboard());

        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());

    }

//    @Test
//    public void correctPasswordTest() {
//        String password = "123456";
//        onView(withId(R.id.passwordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
//        // same password
//        onView(withId(R.id.confirmPasswordReg)).perform(scrollTo(), typeText(password)).perform(closeSoftKeyboard());
//        onView(withId(R.id.buttonReg)).perform(scrollTo(), click());
//    }

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


