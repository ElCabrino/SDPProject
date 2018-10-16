package ch.epfl.sweng.vanjel;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

//@RunWith(AndroidJUnit4.class)
public class
PatientInfoTest {

    private LoginHelper helper = new LoginHelper();

    private String condition = "Good condition";
    private String smoking = "Often";
    private String drinking = "never";
    private String exercise = "everyday";
    private String amount = "18";
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
        onView(withId(R.id.ptGeneralInfos)).perform(scrollTo());
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
    public void testAddAndRecoverAllergy() throws InterruptedException {
        onView(withId(R.id.buttonAllergy)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (Allergy allergy : allergies) {
            onView(withId(R.id.ptAllergyReg)).perform(setTextInTextView(allergy.getAllergy()));
            onView(withId(R.id.buttonAllergy)).perform(click());
        }

        for ( int i = 0; i < allergies.size(); i++ ) {
           onView(allOf(withId(R.id.textViewAllergies), withText(allergies.get(i).getAllergy())))
                    .check(matches(withText(allergies.get(i).getAllergy())));

        }

    }

    //@Test
    public void testAddAndRecoverSmoking() {
        onView(withId(R.id.buttonGenInfoPtReg)).perform(scrollTo());
        onView(withId(R.id.ptSmokingReg)).perform(typeText(amount)).perform(closeSoftKeyboard());
        onView(withId(R.id.buttonSmoking)).perform(click());
        onView(withId(R.id.ptSmokingValue)).check(matches(withText(amount)));
    }

    public static ViewAction setTextInTextView(final String value){
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

}
