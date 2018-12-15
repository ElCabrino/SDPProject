package ch.epfl.sweng.vanjel.firebase;

import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class FirebaseDatabaseTest {

    @Test
    public void testReference() {
        assertNotNull(FirebaseDatabase.getInstance());
    }

}
