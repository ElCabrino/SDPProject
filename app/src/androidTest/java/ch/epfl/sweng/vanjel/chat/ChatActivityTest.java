package ch.epfl.sweng.vanjel.chat;

import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
import static ch.epfl.sweng.vanjel.TestHelper.setupWithExtras;
import static org.hamcrest.Matchers.not;

public class ChatActivityTest {

    @Rule
    public final ActivityTestRule<ChatActivity> mActivityRule =
            new ActivityTestRule<>(ChatActivity.class, true, false);
    @Test
    public void sendMessageTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("contactUID", "patientid1");
        extras.put("contactName", "fn_ptest1");
        setupWithExtras(ChatActivity.class, mActivityRule, false, false, false, false, false, false, extras);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText("test message"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withText("Message successfully sent.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
        onView(withId(R.id.sendMessage)).check(matches(withText("test message")));
    }

    @Test
    public void sendEmptyMessage() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("contactUID", "patientid1");
        extras.put("contactName", "fn_ptest1");
        setupWithExtras(ChatActivity.class, mActivityRule, false, false, false, false, false, false, extras);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText(""));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withId(R.id.sendMessage)).check(matches(withText("test message")));
    }

   @Test
    public void receiveMessageTest() throws Exception {
       Map<String, String> extras = new HashMap<>();
       extras.put("contactUID", "doctorid1");
       extras.put("contactName", "fn_dtest1");
       setupWithExtras(ChatActivity.class, mActivityRule, false, true, false, false, false, false, extras);

       TimeUnit.SECONDS.sleep(1);
       onView(withId(R.id.receiveMessage)).check(matches(withText("test message")));
    }

    @Test
    public void failedMessageTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("contactUID", "patientid1");
        extras.put("contactName", "fn_ptest1");
        setupWithExtras(ChatActivity.class, mActivityRule, false, false, true, false, false, false, extras);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.messageToSend)).perform(typeText("test message"));
        onView(withId(R.id.sendMessageButton)).perform(click());
        onView(withText("Failed to send message.")).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));
    }

    @Test
    public void cancelledLastMessageFetchTest() throws Exception {
        Map<String, String> extras = new HashMap<>();
        extras.put("contactUID", "patientid1");
        extras.put("contactName", "fn_ptest1");
        setupWithExtras(ChatActivity.class, mActivityRule, false, false, false, false, true, false, extras);

        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.receiveMessage)).check(doesNotExist());
    }

    @AfterClass
    public static void restore() {
        restoreMockFlags();
    }
}