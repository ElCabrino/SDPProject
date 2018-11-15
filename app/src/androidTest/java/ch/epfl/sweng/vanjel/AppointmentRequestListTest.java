package ch.epfl.sweng.vanjel;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class AppointmentRequestListTest {

    @Rule
    public final IntentsTestRule<DoctorAppointmentsList> ActivityRule =
            new IntentsTestRule<>(DoctorAppointmentsList.class);

    //@Before
    public void init() {
        //Appointment a = new Appointment("monday", "10:00", "doctorid1", "patientid1");
        //FirebaseDatabaseCustomBackend.getInstance().getReference().child("Requests").setValue(a);
    }

    @Test
    public void testDisplayPage() throws InterruptedException{
        TimeUnit.SECONDS.sleep(5);
    }

}
