package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import java.util.Map;

public class TestHelper {

    public static void restoreMockFlags() {
        FirebaseDatabaseCustomBackend.setShouldFail(false);
        FirebaseDatabaseCustomBackend.setIsCancelled(false);
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(true);
    }

    public static void setupWithExtras(Class<?> c, ActivityTestRule<?> rule, boolean userNull, boolean mockPatient, boolean shouldFail, boolean isCancelled, boolean isCancelledSecond, Map<String, String> extras) {
//        rule.finishActivity();
        FirebaseAuthCustomBackend.setNullUser(userNull);
        FirebaseAuthCustomBackend.setMockPatient(mockPatient);
        FirebaseDatabaseCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setIsCancelled(isCancelled);
        FirebaseDatabaseCustomBackend.setIsCancelledSecond(isCancelledSecond);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, c);
        for (String key : extras.keySet()) {
            intent.putExtra(key, extras.get(key));
        }
        rule.launchActivity(intent);
    }

    public static void setupNoExtras(Class<?> c, ActivityTestRule<?> rule, boolean userNull, boolean mockPatient, boolean shouldFail, boolean isCancelled, boolean isCancelledSecond) {
//        rule.finishActivity();
        FirebaseAuthCustomBackend.setNullUser(userNull);
        FirebaseAuthCustomBackend.setMockPatient(mockPatient);
        FirebaseDatabaseCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setIsCancelled(isCancelled);
        FirebaseDatabaseCustomBackend.setIsCancelledSecond(isCancelledSecond);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, c);
        rule.launchActivity(intent);
    }
}
