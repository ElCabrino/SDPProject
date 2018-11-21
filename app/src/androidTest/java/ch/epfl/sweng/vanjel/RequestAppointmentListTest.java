package ch.epfl.sweng.vanjel;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

@RunWith(AndroidJUnit4.class)
public class RequestAppointmentListTest {

    @Before
    public void init() {
        ActivityRule.finishActivity();
        FirebaseAuthCustomBackend.setNullUser(false);
        FirebaseAuthCustomBackend.setMockPatient(false);
        ActivityRule.launchActivity(new Intent());
    }

    @Rule
    public final IntentsTestRule<DoctorAppointmentsList> ActivityRule =
            new IntentsTestRule<>(DoctorAppointmentsList.class);

    @Test
    public void testDisplayPage() throws InterruptedException{
        TimeUnit.SECONDS.sleep(1);
    }

}
