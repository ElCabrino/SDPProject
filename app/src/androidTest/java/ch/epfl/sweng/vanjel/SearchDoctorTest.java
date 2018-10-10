package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SearchDoctorTest {


    @Rule
    public final IntentsTestRule<SearchDoctor> ActivityRule =
            new IntentsTestRule<>(SearchDoctor.class);

    @Before
    public void setUp() {

    }

    @Test
    public void getFieldStringsTest() {
        enterFirstName("Luca");
        enterLastName("Joss");
        enterSpecialisation("ORL");
        enterCity("Morges");

//        assertEquals("Luca", withId(R.id.firstNameSearch).toString().trim());
//        assertEquals("Joss", withId(R.id.lastNameSearch).toString().trim());
//        assertEquals("ORL", withId(R.id.specialisationSearch).toString().trim());
//        assertEquals("Morges", withId(R.id.citySearch).toString().trim());
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
}