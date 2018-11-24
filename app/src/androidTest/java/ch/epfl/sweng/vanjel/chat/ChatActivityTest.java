package ch.epfl.sweng.vanjel.chat;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static org.hamcrest.Matchers.not;

public class ChatActivityTest {

    @Rule
    public final ActivityTestRule<ChatActivity> mActivityRule =
            new ActivityTestRule<>(ChatActivity.class, true, false);
    @Test
    public void sendMessageTest() throws Exception {
        setup("patientid1", "fn_ptest1", false, false, false, false);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText("test message"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withText("Message successfully sent.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        onView(withId(R.id.sendMessage)).check(matches(withText("test message")));
    }

   @Test
    public void receiveMessageTest() throws Exception {
       setup("doctorid1", "fn_dtest1", false, true, false, false);

       TimeUnit.SECONDS.sleep(1);
       onView(withId(R.id.receiveMessage)).check(matches(withText("test message")));
    }

    @Test
    public void failedMessageTest() throws Exception {
        setup("patientid1", "fn_ptest1", false, false, true, false);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText("test message"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withText("Failed to send message.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void cancelledLastMessageFetchTest() throws Exception {
        setup("patientid1", "fn_ptest1", false, false, false, true);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.receiveMessage)).check(doesNotExist());
    }

    private void setup(String id, String name, boolean userNull, boolean mockPatient, boolean shouldFail, boolean isCancelled) {
        FirebaseAuthCustomBackend.setNullUser(userNull);
        FirebaseAuthCustomBackend.setMockPatient(mockPatient);
        FirebaseDatabaseCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setIsCancelled(isCancelled);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, ChatActivity.class);
        intent.putExtra("contactUID", id);
        intent.putExtra("contactName", name);
        mActivityRule.launchActivity(intent);
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}