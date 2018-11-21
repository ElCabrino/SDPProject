package ch.epfl.sweng.vanjel.chat;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

public class ChatActivityTest {

    @BeforeClass
    public static void runAsPatient() {
        FirebaseAuthCustomBackend.setNullUser(false);
    }

    @Rule
    public final ActivityTestRule<ChatActivity> mActivityRule =
            new ActivityTestRule<ChatActivity>(ChatActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Context targetContext = InstrumentationRegistry.getInstrumentation()
                            .getTargetContext();
                    Intent result = new Intent(targetContext, ChatActivity.class);
                    result.putExtra("contactUID", "doctorid1");
                    result.putExtra("contactName", "fn_dtest1");
                    return result;
                }
            };


    @Test
    public void sendMessageTest() throws Exception {
        runAsPatient();
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText("test message"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withText("Message successfully sent.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        onView(withId(R.id.sendMessage)).check(matches(withText("test message")));
    }

}