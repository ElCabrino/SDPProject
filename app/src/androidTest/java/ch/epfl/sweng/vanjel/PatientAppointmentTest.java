package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class PatientAppointmentTest {

    private String d = "Mon Oct 29 2018";

    @Rule
    public final IntentsTestRule<PatientAppointmentActivity> ActivityRule =
            new IntentsTestRule<PatientAppointmentActivity>(PatientAppointmentActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, PatientAppointmentActivity.class);
                    result.putExtra("doctorUID", "eUy36LW1d2Reyp3Y2h7lL70ccxG3");
                    result.putExtra("date", d);
                    return result;
                }
            };

    @Test
    public void testAppointmentHighlight(){
        onView(withId(R.id.button0830)).perform(scrollTo(), click());
        onView(withId(R.id.button0830)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF303F9F)));
        onView(withId(R.id.button0830)).perform(scrollTo(), click());
        onView(withId(R.id.button0830)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF3F51B5)));
    }

    @Test
    public void testDoubleSelection() throws Exception {
        onView(withId(R.id.button0830)).perform(scrollTo(), click());
        onView(withId(R.id.button0930)).perform(scrollTo(), click());
        TimeUnit.SECONDS.sleep(1);
        onView(withText("You've already picked a time slot")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    // Check the background color
    public static Matcher<View> withBackgroundColor(final int backgroundColor) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                int color = ((ColorDrawable) view.getBackground().getCurrent()).getColor();
                return color == backgroundColor;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with background color value: " + backgroundColor);
            }
        };
    }

    @Test
    public void requestAppointmentTest() {
        onView(withId(R.id.button1030)).perform(scrollTo(), click());
        onView(withId(R.id.buttonAppointment)).perform(click());
        onView(withText("Appointment successfully requested.")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        removeTestData(d);
    }

    @Test
    public void doctorAvailabilityDisplayTest() {
        onView(withId(R.id.button0800)).check(matches(not(isEnabled())));
        onView(withId(R.id.button0830)).check(matches(isEnabled()));
        onView(withId(R.id.button0900)).check(matches(not(isEnabled())));
        onView(withId(R.id.button0930)).check(matches(isEnabled()));
        onView(withId(R.id.button1000)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1030)).check(matches(isEnabled()));
        onView(withId(R.id.button1100)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1130)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1200)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1230)).check(matches(isEnabled()));
        onView(withId(R.id.button1300)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1330)).check(matches(isEnabled()));
        onView(withId(R.id.button1400)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1430)).check(matches(isEnabled()));
        onView(withId(R.id.button1500)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1530)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1600)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1630)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1700)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1730)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1800)).check(matches(not(isEnabled())));
        onView(withId(R.id.button1830)).check(matches(not(isEnabled())));
    }

    // Remove appointment request generated by test.
    private void removeTestData(String day) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");
        ref.child(day).addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().setValue(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }
        );
    }

    // Cancel toast after test is finished, if it is still visible
    @After
    public void removeToast() {
        Toast toast = ActivityRule.getActivity().mToast;
        if (toast != null) {
            toast.cancel();
        }
    }
}
