package ch.epfl.sweng.vanjel;

import org.junit.Test;

import ch.epfl.sweng.vanjel.appointment.AppointmentNotificationBackgroundService;

import static org.junit.Assert.assertNotNull;

public class AppointmentNotificationBackgroundServiceTest {

    @Test
    public void onCreateTest() {
        assertNotNull(AppointmentNotificationBackgroundService.class);
    }

}