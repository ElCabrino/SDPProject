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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
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
                    result.putExtra("doctorUID", "0N5Bg2yoxrgVzD9U5jWz1RuJLyj2");
                    result.putExtra("date", d);
                    return result;
                }
            };

    @Test
    public void testAppointmentHighlight(){
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0800)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF303F9F)));
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0800)).perform(scrollTo()).check(matches(withBackgroundColor(0xFF3F51B5)));
    }

    @Test
    public void testDoubleSelection() {
        onView(withId(R.id.button0800)).perform(scrollTo(), click());
        onView(withId(R.id.button0900)).perform(scrollTo(), click());
        onView(withText("You've already picked a time slot")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

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
        onView(withId(R.id.button1130)).perform(scrollTo(), click());
        onView(withId(R.id.buttonAppointment)).perform(click());
        onView(withText("Appointment successfully requested.")).inRoot(withDecorView(not(ActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        removeTestData(d);
    }

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

    @After
    public void removeToast() {
        Toast toast = ActivityRule.getActivity().mToast;
        if (toast != null) {
            toast.cancel();
        }
    }
}
