package ch.epfl.sweng.vanjel;

public class TestHelper {

    public static void restoreMockFlags() {
        FirebaseDatabaseCustomBackend.setShouldFail(false);
        FirebaseDatabaseCustomBackend.setIsCancelled(false);
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(true);
    }
}
