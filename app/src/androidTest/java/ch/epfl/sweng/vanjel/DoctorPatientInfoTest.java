package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class DoctorPatientInfoTest {

    @Rule
    public final IntentsTestRule<DoctorPatientInfo> ActivityRule =
            new IntentsTestRule<>(DoctorPatientInfo.class);

    @BeforeClass
    public static void setUp(){
        FirebaseAuthCustomBackend.setMockPatient(true);
        FirebaseAuthCustomBackend.setNullUser(false);
    }
    //TODO: mock tests once integrated with doctor appointments
    @Test
    public void searchTextViewsTest() throws Exception {
        onView(withId(R.id.doctorPtPriorConditionsInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtSurgeryInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtAllergyInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtDrugReactionInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtDrugRegimenInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtSmokingInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtDrinkingInfo)).perform(scrollTo(),closeSoftKeyboard());
        onView(withId(R.id.doctorPtSubstanceInfo)).perform(scrollTo(),closeSoftKeyboard());
    }

}
