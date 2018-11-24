package ch.epfl.sweng.vanjel.chat;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static ch.epfl.sweng.vanjel.TestHelper.restoreMockFlags;
import static ch.epfl.sweng.vanjel.TestHelper.setupNoExtras;

public class ChatListActivityTest {

    @Rule
    public final ActivityTestRule<ChatListActivity> mActivityRule =
            new ActivityTestRule<>(ChatListActivity.class);

    @Test
    public void displayChatListTest() throws Exception {
        setupNoExtras(ChatListActivity.class, mActivityRule, false, true, false, false, false);
        Intents.init();
        String expectedName = "fn_dtest1 ln_dtest1";
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.contactName)).check(matches(withText(expectedName)));
        onView(withId(R.id.time)).check(matches(withText("07.30")));
        onView(withId(R.id.lastMessage)).check(matches(withText("test message")));
        onView(withId(R.id.chat)).perform(click());
        intended(hasComponent(ChatActivity.class.getName()));
        Intents.release();
    }

    @Test
    public void getClassUsersCancelled() throws Exception {
        setupNoExtras(ChatListActivity.class, mActivityRule, false, true, false, true, false);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.contactName)).check(doesNotExist());
        onView(withId(R.id.time)).check(doesNotExist());
        onView(withId(R.id.lastMessage)).check(doesNotExist());
    }

    @Test
    public void getChatsCancelled() throws Exception {
        setupNoExtras(ChatListActivity.class, mActivityRule, false, true, false, false, true);
        TimeUnit.SECONDS.sleep(1);
        onView(withId(R.id.contactName)).check(doesNotExist());
        onView(withId(R.id.time)).check(doesNotExist());
        onView(withId(R.id.lastMessage)).check(doesNotExist());
    }

    @AfterClass
    public static void restore(){
        restoreMockFlags();
    }
}