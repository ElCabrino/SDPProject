package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SearchDoctorTest {

    String firstName = "Luca";
    String lastName = "Joss";
    String specialisation = "ORL";
    String city = "Morges";

    @Rule
    public final IntentsTestRule<SearchDoctor> ActivityRule =
            new IntentsTestRule<>(SearchDoctor.class);

    @Test
    public void getFieldStringsTest() {
        fillFields();

        onView(withId(R.id.firstNameSearch)).perform(scrollTo()).check(matches(withText(firstName)));
        onView(withId(R.id.lastNameSearch)).perform(scrollTo()).check(matches(withText(lastName)));
        onView(withId(R.id.specialisationSearch)).perform(scrollTo()).check(matches(withText(specialisation)));
        onView(withId(R.id.citySearch)).perform(scrollTo()).check(matches(withText(city)));
    }

    private void enterFirstName(String firstName) {
        onView(withId(R.id.firstNameSearch)).perform(replaceText(firstName));
    }

    private void enterLastName(String lastName) {
        onView(withId(R.id.lastNameSearch)).perform(replaceText(lastName));
    }

    private void enterSpecialisation(String spec) {
        onView(withId(R.id.specialisationSearch)).perform(replaceText(spec));
    }

    private void enterCity(String city) {
        onView(withId(R.id.citySearch)).perform(replaceText(city));
    }

    private void fillFields() {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterSpecialisation(specialisation);
        enterCity(city);
    }
}