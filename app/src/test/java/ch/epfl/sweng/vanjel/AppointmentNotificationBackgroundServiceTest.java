package ch.epfl.sweng.vanjel;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppointmentNotificationBackgroundServiceTest {

    @Test
    public void onCreateTest() {
        assertNotNull(AppointmentNotificationBackgroundService.class);
    }

}