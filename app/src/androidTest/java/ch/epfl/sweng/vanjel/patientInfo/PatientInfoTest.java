package ch.epfl.sweng.vanjel.patientInfo;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class
PatientInfoTest {

    private String id = "ABLlrLukjAaPzaf5GA03takkw5k2";

    private String cond1 = "Heart failure";
    private String cond2 = "Diabetes";
    private String al1 = "Peanuts";
    private String al2 = "Cats";
    private String sub1 = "Cocaine";
    private String sub2 = "Krokodil";


    private String surgery1 = "THA";
    private String surgery1Year = "2000";
    private String surgery2 = "Cholecystectomy";
    private String surgery2Year = "2005";
    private String expectedSurgery1 = surgery1 + " in " + surgery1Year;
    private String expectedSurgery2 = surgery2 + " in " + surgery2Year;

    private String drDrug1 = "Carbamazepine";
    private String drReaction1 = "TEN";
    private String drDrug2 = "Contrast";
    private String drReaction2 = "Rashes";
    private String expectedDrugReaction1 = drDrug1 + " : " + drReaction1;
    private String expectedDrugReaction2 = drDrug2 + " : " + drReaction2;


    private String drugDrug1 = "Enalapril";
    private String drugDosage1 = "10mg";
    private String drugFreq1 = "1";
    private String drugDrug2 = "Propranolol";
    private String drugDosage2 = "80mg";
    private String drugFreq2 = "2";
    private String expectedDrug1 = drugDrug1 + " " + drugDosage1 + ", " + drugFreq1 + " per day";
    private String expectedDrug2 = drugDrug2 + " " + drugDosage2 + ", " + drugFreq2 + " per day";


    private String smoking = "40";
    private String drinking = "10";
    private String exercise = "0";

  
    private ArrayList<InfoString> conditions;
    private ArrayList<InfoString> allergies;
    private ArrayList<InfoString> substances;
    private ArrayList<Surgery> surgeries;
    private ArrayList<String> expectedSurgeries;
    private ArrayList<DrugReaction> drugReactions;
    private ArrayList<String> expectedDrugReactions;
    private ArrayList<Drug> drugs;
    private ArrayList<String> expectedDrugs;

    //TODO: retrieval tests and mocks
    @BeforeClass
    public static void beforeClass(){
        FirebaseAuthCustomBackend.setMockPatient(true);
        FirebaseAuthCustomBackend.setNullUser(false);
    }

    @Before
    public void before(){
        FirebaseAuthCustomBackend.setMockPatient(true);
        FirebaseAuthCustomBackend.setNullUser(false);
    }

    @Rule
    public final IntentsTestRule<PatientInfo> ActivityRule =
            new IntentsTestRule<>(PatientInfo.class);


    @Before
    public void setUp() throws Exception
    {
        populateConditions();
        populateAllergies();
        populateSubstances();
        populateSurgeries();
        populateDrugReactions();
        populateDrugs();

    }

    @After
    public void waitForToast() throws Exception {
        TimeUnit.SECONDS.sleep(5);
    }

    private void populateArray(ArrayList<Info> list, Info e1, Info e2) {
    }

    private void populateConditions()
    {
        conditions = new ArrayList<InfoString>();
        conditions.add(new InfoString(cond1));
        //conditions.add(new InfoString(cond2));
    }

    private void populateAllergies()
    {
        allergies = new ArrayList<InfoString>();
        allergies.add(new InfoString(al1));
        allergies.add(new InfoString(al2));
    }

    private void populateSubstances()
    {
        substances = new ArrayList<InfoString>();
        substances.add(new InfoString(sub1));
        substances.add(new InfoString(sub2));
    }

    private void populateSurgeries()
    {
        surgeries = new ArrayList<Surgery>();
        surgeries.add(new Surgery(surgery1, surgery1Year));
        surgeries.add(new Surgery(surgery2, surgery2Year));
        expectedSurgeries = new ArrayList<String>();
        expectedSurgeries.add(expectedSurgery1);
        expectedSurgeries.add(expectedSurgery2);

    }

    private void populateDrugReactions()
    {
        drugReactions = new ArrayList<DrugReaction>();
        drugReactions.add(new DrugReaction(drDrug1, drReaction1));
        drugReactions.add(new DrugReaction(drDrug2, drReaction2));
        expectedDrugReactions = new ArrayList<String>();
        expectedDrugReactions.add(expectedDrugReaction1);
        expectedDrugReactions.add(expectedDrugReaction2);
    }

    private void populateDrugs()
    {
        drugs = new ArrayList<Drug>();
        drugs.add(new Drug(drugDrug1, drugDosage1, drugFreq1));
        drugs.add(new Drug(drugDrug2, drugDosage2,drugFreq2));
        expectedDrugs = new ArrayList<String>();
        expectedDrugs.add(expectedDrug1);
        expectedDrugs.add(expectedDrug2);
    }

    @Test
    public void testAddAndRecoverCondition() throws InterruptedException {
        onView(ViewMatchers.withId(R.id.buttonPriorConditions)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (InfoString condition : conditions) {
            onView(withId(R.id.ptPriorConditionsReg)).perform(setTextInTextView(condition.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonPriorConditions)).perform(click());
        }
        /*onView(withId(R.id.ptSurgeryInfo)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < conditions.size(); i++ ) {
            onView(allOf(withId(R.id.textViewConditions), withText(conditions.get(i).getInfo())))
                    .check(matches(isDisplayed()));
        }*/

    }

    @Test
    public void testAddAndRecoverAllergy() throws InterruptedException {
        onView(withId(R.id.buttonAllergy)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (InfoString allergy : allergies) {
            onView(withId(R.id.ptAllergyReg)).perform(setTextInTextView(allergy.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonAllergy)).perform(click());
        }

        /*onView(withId(R.id.ptDrugReactionInfo)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < allergies.size(); i++ ) {
            onView(allOf(withId(R.id.textViewAllergies), withText(allergies.get(i).getInfo())))
                    .check(matches(isDisplayed()));
        }*/

    }

    //@Ignore
    @Test
    public void testAddAndRecoverSubstance() throws InterruptedException {
        onView(withId(R.id.buttonSubstance)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);

        for (InfoString substance : substances) {
            onView(withId(R.id.ptSubstanceReg)).perform(setTextInTextView(substance.getInfo()), closeSoftKeyboard());
            onView(withId(R.id.buttonSubstance)).perform(click());

        }

        /*onView(withId(R.id.buttonGenInfoPtReg)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < substances.size(); i++ ) {
            onView(allOf(withId(R.id.textViewSubstances), withText(substances.get(i).getInfo())))
                    .check(matches(isDisplayed()));
        }*/

    }

    @Test
    public void testAddAndRecoverSurgery() throws InterruptedException {
        onView(withId(R.id.buttonSurgery)).perform(scrollTo());
        //onView(withId(R.id.patientInfoListView)).pageScroll(View.FOCUS_UP);
        TimeUnit.SECONDS.sleep(4);
        for (Surgery surgery: surgeries) {
            onView(withId(R.id.ptSurgeryYearReg)).perform(setTextInTextView(surgery.getYear()), closeSoftKeyboard());
            onView(withId(R.id.ptSurgeryReg)).perform(setTextInTextView(surgery.getType()), closeSoftKeyboard());
            onView(withId(R.id.buttonSurgery)).perform(click());
        }

        /*onView(withId(R.id.ptAllergyInfo)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < expectedSurgeries.size(); i++ ) {
            onView(allOf(withId(R.id.textViewSurgeries), withText(expectedSurgeries.get(i))))
                    .check(matches(isDisplayed()));
        }*/

    }


    //@Ignore
    @Test
    public void testAddAndRecoverDrugReaction() throws InterruptedException {
        onView(withId(R.id.buttonDrugReaction)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for (DrugReaction reaction: drugReactions) {
            onView(withId(R.id.ptDrugReactionDrugReg)).perform(setTextInTextView(reaction.getDrug()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugReactionReactionReg)).perform(setTextInTextView(reaction.getReaction()), closeSoftKeyboard());
            onView(withId(R.id.buttonDrugReaction)).perform(click());
        }

        /*onView(withId(R.id.ptDrugRegimenInfo)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < expectedDrugReactions.size(); i++ ) {
            onView(allOf(withId(R.id.textViewDrugReactions), withText(expectedDrugReactions.get(i))))
                    .check(matches(isDisplayed()));
        }*/

    }

    //@Ignore
    @Test
    public void testAddAndRecoverDrug() throws InterruptedException {
        onView(withId(R.id.buttonDrugRegimen)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(4);
        for (Drug drug : drugs) {
            onView(withId(R.id.ptDrugRegimenDrugReg)).perform(setTextInTextView(drug.getDrug()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugRegimenDosageReg)).perform(setTextInTextView(drug.getDosage()), closeSoftKeyboard());
            onView(withId(R.id.ptDrugRegimenTimesReg)).perform(setTextInTextView(drug.getFrequency()), closeSoftKeyboard());
            onView(withId(R.id.buttonDrugRegimen)).perform(click());
        }

        /*onView(withId(R.id.ptSmokingInfo)).perform(scrollTo());
        TimeUnit.SECONDS.sleep(5);
        for ( int i = 0; i < expectedDrugs.size(); i++ ) {
            onView(allOf(withId(R.id.textViewDrugs), withText(expectedDrugs.get(i))))
                    .check(matches(isDisplayed()));
        }*/

    }

    //@Ignore
    @Test
    public void testAddAndRecoverSmoking() {
        addAndRecoverSingleValue(R.id.buttonSmoking, R.id.ptSmokingReg, R.id.ptSmokingValue, smoking);
    }

    //@Ignore
    @Test
    public void testAddAndRecoverDrinking() {
        addAndRecoverSingleValue(R.id.buttonDrinking, R.id.ptDrinkingReg, R.id.ptDrinkingValue, drinking);
    }

    //@Ignore
    @Test
    public void testAddAndRecoverExercise() {
        addAndRecoverSingleValue(R.id.buttonExercise, R.id.ptExerciseReg, R.id.ptExerciseValue, exercise);
    }

    private void addAndRecoverSingleValue(int idButton, int idEditText, int idTextField, String text) {
        onView(withId(idButton)).perform(scrollTo(), closeSoftKeyboard());
        //onView(withId(R.id.ptDrugReactionList)).perform(swipeDown());
        onView(withId(idEditText)).perform(clearText(), setTextInTextView(text), closeSoftKeyboard());
        onView(withId(idButton)).perform(click());
        onView(withId(idTextField)).perform(scrollTo());
        // onView(withId(idTextField)).check(matches(withText(text)));

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