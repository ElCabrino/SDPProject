package ch.epfl.sweng.vanjel.patientInfo;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class PatientInfoTest {

    private ArrayList<InfoString> conditions;
    private ArrayList<InfoString> allergies;
    private ArrayList<InfoString> substances;
    private ArrayList<Surgery> surgeries;
    private ArrayList<DrugReaction> drugReactions;
    private ArrayList<Drug> drugs;

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class, true, false);

    @Before
    public void setUp() {
        populateConditions();
        populateAllergies();
        populateSubstances();
        populateSurgeries();
        populateDrugReactions();
        populateDrugs();
    }

    // Must wait 5 seconds for Toast to disappear after each tests
    @After
    public void waitForToast() throws Exception {
        TimeUnit.SECONDS.sleep(5);
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }

    private void populateConditions()
    {
        String cond1 = "Heart failure";
        conditions = new ArrayList<>();
        conditions.add(new InfoString(cond1));
    }

    private void populateAllergies()
    {
        String al1 = "Peanuts";
        String al2 = "Cats";
        allergies = new ArrayList<>();
        allergies.add(new InfoString(al1));
        allergies.add(new InfoString(al2));
    }

    private void populateSubstances()
    {
        String sub1 = "Cocaine";
        String sub2 = "Krokodil";
        substances = new ArrayList<>();
        substances.add(new InfoString(sub1));
        substances.add(new InfoString(sub2));
    }

    private void populateSurgeries()
    {

        String surgery1 = "THA";
        String surgery1Year = "2000";
        String surgery2 = "Cholecystectomy";
        String surgery2Year = "2005";
        surgeries = new ArrayList<>();
        surgeries.add(new Surgery(surgery1, surgery1Year));
        surgeries.add(new Surgery(surgery2, surgery2Year));

    }

    private void populateDrugReactions()
    {

        String drDrug1 = "Carbamazepine";
        String drReaction1 = "TEN";
        String drDrug2 = "Contrast";
        String drReaction2 = "Rashes";
        drugReactions = new ArrayList<>();
        drugReactions.add(new DrugReaction(drDrug1, drReaction1));
        drugReactions.add(new DrugReaction(drDrug2, drReaction2));
    }

    private void populateDrugs()
    {
        String drugDrug1 = "Enalapril";
        String drugDosage1 = "10mg";
        String drugFreq1 = "1";
        String drugDrug2 = "Propranolol";
        String drugDosage2 = "80mg";
        String drugFreq2 = "2";
        drugs = new ArrayList<>();
        drugs.add(new Drug(drugDrug1, drugDosage1, drugFreq1));
        drugs.add(new Drug(drugDrug2, drugDosage2,drugFreq2));
    }

    @Test
    public void testAddAndRecoverCondition() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(ViewMatchers.withId(R.id.buttonPriorConditions)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(1);
        for (InfoString condition : conditions) {
            onView(withId(R.id.ptPriorConditionsReg)).perform(setTextInTextView(condition.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonPriorConditions)).perform(click());
            onView(withText("Condition added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverAllergy() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.buttonAllergy)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (InfoString allergy : allergies) {
            onView(withId(R.id.ptAllergyReg)).perform(setTextInTextView(allergy.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonAllergy)).perform(click());
            onView(withText("Allergy added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverSubstance() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.buttonSubstance)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);

        for (InfoString substance : substances) {
            onView(withId(R.id.ptSubstanceReg)).perform(setTextInTextView(substance.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonSubstance)).perform(click());
            onView(withText("Substance added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverSurgery() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.buttonSurgery)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(4);
        for (Surgery surgery: surgeries) {
            onView(withId(R.id.ptSurgeryYearReg)).perform(setTextInTextView(surgery.getYear()), closeSoftKeyboard());
            onView(withId(R.id.ptSurgeryReg)).perform(setTextInTextView(surgery.getType()), closeSoftKeyboard());
            onView(withId(R.id.buttonSurgery)).perform(click());
            onView(withText("Surgery added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverDrugReaction() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.buttonDrugReaction)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (DrugReaction reaction: drugReactions) {
            onView(withId(R.id.ptDrugReactionDrugReg)).perform(setTextInTextView(reaction.getDrug()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugReactionReactionReg)).perform(setTextInTextView(reaction.getReaction()), closeSoftKeyboard());
            onView(withId(R.id.buttonDrugReaction)).perform(click());
            onView(withText("Drug reaction added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverDrug() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.buttonDrugRegimen)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(4);
        for (Drug drug : drugs) {
            onView(withId(R.id.ptDrugRegimenDrugReg)).perform(setTextInTextView(drug.getDrug()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugRegimenDosageReg)).perform(setTextInTextView(drug.getDosage()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugRegimenTimesReg)).perform(setTextInTextView(drug.getFrequency()), closeSoftKeyboard());
            onView(withId(R.id.buttonDrugRegimen)).perform(click());
            onView(withText("Drug added.")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Test
    public void testAddAndRecoverSmoking() {
        String smoking = "1";
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        addAndRecoverSingleValue(R.id.buttonSmoking, R.id.ptSmokingReg, R.id.ptSmokingValue, smoking);
    }

    @Test
    public void testAddAndRecoverDrinking() {
        String drinking = "1";
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        addAndRecoverSingleValue(R.id.buttonDrinking, R.id.ptDrinkingReg, R.id.ptDrinkingValue, drinking);
    }

    @Test
    public void testAddAndRecoverExercise() {
        String exercise = "1";
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        addAndRecoverSingleValue(R.id.buttonExercise, R.id.ptExerciseReg, R.id.ptExerciseValue, exercise);
    }

    @Test
    public void updateSingleInfo() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.textViewConditions)).perform(scrollTo());
        onView(withId(R.id.textViewConditions)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonPatientInfoUpdate)).perform(click());
        TimeUnit.SECONDS.sleep(4);

    }

    @Test
    public void deleteSingleInfo() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.textViewConditions)).perform(scrollTo());
        onView(withId(R.id.textViewConditions)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonPatientInfoDelete)).perform(click());
        onView(withText("Condition deleted")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void updateDoubleInfo() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.textViewSurgeries)).perform(scrollTo());
        onView(withId(R.id.textViewSurgeries)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonPatientInfoUpdateDoubleInfo)).perform(click());
    }

    @Test
    public void updateDrugInfo() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.textViewDrugs)).perform(scrollTo());
        onView(withId(R.id.textViewDrugs)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonPatientInfoUpdateDrug)).perform(click());
    }

    @Test
    public void deleteDrugInfo() throws InterruptedException {
        setupNoExtras(PatientInfo.class, ActivityRule, false, true, false, false, false, false, false);
        onView(withId(R.id.textViewDrugs)).perform(scrollTo());
        onView(withId(R.id.textViewDrugs)).perform(click());
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.buttonPatientInfoDeleteDrug)).perform(click());
        onView(withText("Drug deleted")).inRoot(withDecorView(Matchers.not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    private void addAndRecoverSingleValue(int idButton, int idEditText, int idTextField, String text) {
        onView(withId(idButton)).perform(scrollTo(), closeSoftKeyboard());
        onView(withId(idEditText)).perform(clearText(), setTextInTextView(text), closeSoftKeyboard());
        onView(withId(idButton)).perform(click());
        onView(withId(idTextField)).perform(scrollTo());
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