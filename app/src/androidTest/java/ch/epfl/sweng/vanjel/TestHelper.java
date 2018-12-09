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
        FirebaseDatabaseCustomBackend.setIsCancelledSecond(false);
        FirebaseDatabaseCustomBackend.setIsCancelledThird(false);
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(true);
    }

    public static void setupWithExtras(Class<?> c, ActivityTestRule<?> rule, boolean userNull, boolean mockPatient, boolean shouldFail, boolean isCancelled, boolean isCancelledSecond, boolean isCancelledThird, Map<String, String> extras ,Map<String,Boolean> extrasBoolean) {
        FirebaseAuthCustomBackend.setNullUser(userNull);
        FirebaseAuthCustomBackend.setMockPatient(mockPatient);
        FirebaseDatabaseCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setIsCancelled(isCancelled);
        FirebaseDatabaseCustomBackend.setIsCancelledSecond(isCancelledSecond);
        FirebaseDatabaseCustomBackend.setIsCancelledThird(isCancelledThird);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, c);
        for (String key : extras.keySet()) {
            intent.putExtra(key, extras.get(key));
        }
        for (String key : extrasBoolean.keySet()) {
            intent.putExtra(key, extras.get(key));
        }
        rule.launchActivity(intent);
    }

    public static void setupNoExtras(Class<?> c, ActivityTestRule<?> rule, boolean userNull, boolean mockPatient, boolean shouldFail, boolean isCancelled, boolean isCancelledSecond, boolean isCancelledThird) {
        FirebaseAuthCustomBackend.setNullUser(userNull);
        FirebaseAuthCustomBackend.setMockPatient(mockPatient);
        FirebaseAuthCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setShouldFail(shouldFail);
        FirebaseDatabaseCustomBackend.setIsCancelled(isCancelled);
        FirebaseDatabaseCustomBackend.setIsCancelledSecond(isCancelledSecond);
        FirebaseDatabaseCustomBackend.setIsCancelledThird(isCancelledThird);

        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Intent intent = new Intent(targetContext, c);
        rule.launchActivity(intent);
    }
}
