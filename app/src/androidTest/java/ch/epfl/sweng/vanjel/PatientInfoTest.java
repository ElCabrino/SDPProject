package ch.epfl.sweng.vanjel;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private String surgery1 = "foot surgery";
    private String surgery1Year = "2000";
    private String surgery2 = "arm surgery";
    private String surgery2Year = "2005";

    private ArrayList<InfoString> allergies;
    private ArrayList<Surgery> surgeries;
    private ArrayList<DrugReaction> drugReactions;

    @BeforeClass
    public static void loginPatientInfoUser() throws InterruptedException {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("PatientInfo@test.ch", "123456");
        }

        TimeUnit.SECONDS.sleep(10);

    }

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class);


    @Before
    public void setUp() throws Exception
    {
        populateAllergies();
        populateSurgeries();
        populateDrugs();

    }

    private void populateAllergies()
    {
        allergies = new ArrayList<InfoString>();
        allergies.add(new InfoString(al1));
        allergies.add(new InfoString(al2));
    }

    private void populateSurgeries()
    {
        surgeries = new ArrayList<Surgery>();
        surgeries.add(new Surgery(surgery1, surgery1Year));
        surgeries.add(new Surgery(surgery2, surgery2Year));
    }

    private void populateDrugs()
    {
        drugReactions = new ArrayList<DrugReaction>();
        drugReactions.add(new DrugReaction("paracetamol", "skin reaction"));
        drugReactions.add(new DrugReaction("tramadol", "indigestion"));
    }

    @Test
    public void testAddCondition(){
        onView(withId(R.id.ptGeneralInfos)).perform(scrollTo());
        onView(withId(R.id.ptPriorConditionsReg)).perform(setTextInTextView(condition), closeSoftKeyboard());
        onView(withId(R.id.buttonPriorConditions)).perform(scrollTo(), click());
    }

    @Test
    public void testAddSmoking(){
        onView(withId(R.id.ptSmokingReg)).perform(scrollTo(), setTextInTextView(smoking), closeSoftKeyboard());
        onView(withId(R.id.buttonSmoking)).perform(scrollTo(), click());
        onView(allOf(withId(R.id.ptSmokingValue), withText(smoking))).check(matches(withText(smoking)));

    }

    //@Test
    public void testAddAndRecoverAllergy() throws InterruptedException {
        onView(withId(R.id.buttonAllergy)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (InfoString allergy : allergies) {
            onView(withId(R.id.ptAllergyReg)).perform(setTextInTextView(allergy.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonAllergy)).perform(click());
        }
    }

    @Test
    public void testAddAndRecoverDrugReaction() throws InterruptedException {
        onView(withId(R.id.buttonDrugRegimen)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (DrugReaction reaction: drugReactions) {
            onView(withId(R.id.ptDrugReactionDrugReg)).perform(setTextInTextView(reaction.getDrug()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugReactionReactionReg)).perform(setTextInTextView(reaction.getReaction()), closeSoftKeyboard());
            onView(withId(R.id.buttonDrugReaction)).perform(click());
        }
    }

    @Test
    public void testAddAndRecoverSurgery() throws InterruptedException {
        onView(withId(R.id.buttonSurgery)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(4);
        for (Surgery surgery: surgeries) {
            onView(withId(R.id.ptSurgeryYearReg)).perform(setTextInTextView(surgery.getYear()), closeSoftKeyboard());
            onView(withId(R.id.ptSurgeryReg)).perform(setTextInTextView(surgery.getType()), closeSoftKeyboard());
            onView(withId(R.id.buttonSurgery)).perform(click());
        }

    }

    @Test
    public void testAddAndRecoverSmoking() {
        addAndRecoverSingleValue(R.id.buttonSmoking, R.id.ptSmokingReg, R.id.ptSmokingValue, amount);
    }

    @Test
    public void testAddAndRecoverDrinking() {
        addAndRecoverSingleValue(R.id.buttonDrinking, R.id.ptDrinkingReg, R.id.ptDrinkingValue, amount);
    }

    @Test
    public void testAddAndRecoverExercise() {
        addAndRecoverSingleValue(R.id.buttonExercise, R.id.ptExerciseReg, R.id.ptExerciseValue, exercise);
    }

    void addAndRecoverSingleValue(int idButton, int idEditText, int idTextField, String text) {
        onView(withId(idButton)).perform(scrollTo());
        onView(withId(idEditText)).perform(setTextInTextView(text), closeSoftKeyboard());
        onView(withId(idButton)).perform(click());
        onView(withId(idTextField)).check(matches(withText(text)));
    }

    private static ViewAction setTextInTextView(final String value){
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
