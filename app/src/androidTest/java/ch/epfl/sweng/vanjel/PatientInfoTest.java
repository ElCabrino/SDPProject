package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class PatientInfoTest {

    private String condition = "Good condition";
    private String smoking = "Often";
    private String drinking = "never";
    private String exercise = "everyday";
    private String id = "ABLlrLukjAaPzaf5GA03takkw5k2";
    private String al1 = "Bees";
    private String al2 = "Bs";

    private ArrayList<Allergy> allergies;

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class);

    @Before
    public void setUp() throws Exception
    {
        populateAllergies();
    }

    private void populateAllergies()
    {
        allergies = new ArrayList<Allergy>();
        allergies.add(new Allergy(id,al1));
        allergies.add(new Allergy(id,al2));
    }

    @Test
    public void testAddCondition(){
        onView(withId(R.id.ptPriorConditionsReg)).perform(replaceText(condition));
        onView(withId(R.id.buttonPriorConditions)).perform(scrollTo(), click());
    }

    @Test
    public void testAddSmoking(){
        onView(withId(R.id.ptSmokingReg)).perform(scrollTo(), replaceText(smoking));
        onView(withId(R.id.buttonSmoking)).perform(scrollTo(), click());
        //onView(allOf(withId(R.id.ptSmokingValue), withText(smoking))).check(matches(withText(smoking)));

    }

    @Test
    public void testAddAndRecoverAllergy(){
        onView(withId(R.id.buttonDrinking)).perform(scrollTo());
        for (Allergy allergy : allergies) {
            onView(withId(R.id.ptAllergyReg)).perform(replaceText(allergy.getAllergy()));
            onView(withId(R.id.buttonAllergy)).perform(click());
        }

        for ( int i = 0; i < allergies.size(); i++ ) {
           onView(allOf(withId(R.id.textViewAllergies), withText(allergies.get(i).getAllergy())))
                    .check(matches(withText(allergies.get(i).getAllergy())));

        }

    }



}
