package ch.epfl.sweng.vanjel.chat;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import ch.epfl.sweng.vanjel.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class ChatListActivityTest {

    @Rule
    public final ActivityTestRule<ChatListActivity> mActivityRule =
            new ActivityTestRule<>(ChatListActivity.class);

    @Test
    public void displayChatListTest() throws Exception {
        runAsPatient();
        String expectedName = "fn_dtest1 ln_dtest1";
        TimeUnit.SECONDS.sleep(3);
        onView(withId(R.id.contactName)).check(matches(withText(expectedName)));
        onView(withId(R.id.time)).check(matches(withText("07.30")));
        onView(withId(R.id.lastMessage)).check(matches(withText("test message")));
    }

    private void runAsPatient() {
        FirebaseAuthCustomBackend.setMockPatient(true);
        mActivityRule.finishActivity();
        Intent i = new Intent();
        mActivityRule.launchActivity(i);
    }

}