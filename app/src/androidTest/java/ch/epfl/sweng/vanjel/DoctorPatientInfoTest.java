package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

public class DoctorPatientInfoTest {

    @Rule
    public final ActivityTestRule<DoctorPatientInfo> ActivityRule =
            new ActivityTestRule<DoctorPatientInfo>(DoctorPatientInfo.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, DoctorPatientInfo.class);
                    result.putExtra("patientUID", "patientid1");
                    return result;
                }
            };


    //TODO: mock tests once integrated with doctor appointments
    @Test
    public void searchTextViewsTest() throws Exception {
//        setupNoExtras(DoctorPatientInfo.class, ActivityRule, false, true, false, false, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.textViewConditions)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("Cats")));
        onView(withId(R.id.textViewSurgeries)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("THA in 2000")));
        onView(withId(R.id.textViewAllergies)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("Cats")));
        onView(withId(R.id.textViewDrugReactions)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("Carbamazepine : TEN")));
        onView(withId(R.id.textViewDrugs)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("Enalapril 10mg, 1 per day")));
        onView(withId(R.id.doctorPtSmokingValue)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("1")));
        onView(withId(R.id.doctorPtDrinkingValue)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("1")));
        onView(withId(R.id.textViewSubstances)).perform(scrollTo(),closeSoftKeyboard()).check(matches(withText("Cats")));
    }

    @AfterClass
    public static void restore(){
        restoreMockFlags();
    }
}
