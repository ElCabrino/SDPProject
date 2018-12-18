package ch.epfl.sweng.vanjel.appointment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.doctorAppointment.DoctorAppointmentsList;
import ch.epfl.sweng.vanjel.firebase.FirebaseAuthCustomBackend;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.patientAppointment.PatientPersonalAppointments;


/**
 * @author Vincent CABRINI
 * @reviewer Aslam CADER
 */
public class AppointmentNotificationBackgroundService extends Service {


    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private final FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        createNotificationChannel();

        Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show();
        createDatabaseAppointmentListener();
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    private void createDatabaseAppointmentListener() {
        DatabaseReference ref = database.getReference("Requests");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {

                //ensure getValue return a non null object
                String doctor = dataSnapshot.child("doctor").toString();
                String notified = dataSnapshot.child("doctorNotified").toString();
                Boolean notify = Boolean.parseBoolean(notified);
                if (!notify) {
                    String title = "New appointment";
                    String text = "A patient took a new appointment!";
                    changeStateAndNotify(dataSnapshot.getRef().child("doctorNotified"), doctor, title, text, false);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {

                String patient = dataSnapshot.child("patient").toString();
                String bool = dataSnapshot.child("userNotified").toString();
                String durationString = dataSnapshot.child("duration").toString();

                int duration = Integer.parseInt(durationString);
                Boolean notify = Boolean.parseBoolean(bool);

                Boolean isAppointmentNull = duration == 0;

                if(!notify && !isAppointmentNull){
                    String title = "One of your appointment has been updated!";
                    String text = "A doctor saw your appointment request and accepted it, come and look which one is it!";
                    changeStateAndNotify(dataSnapshot.getRef().child("userNotified"), patient, title, text, true);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "appointmentChannel";
            String description = "appointment";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("appointmentID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent setupActivityToRun(Class<?> c) {
        Intent resIntent = new Intent(this, c);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void changeStateAndNotify(DatabaseReference stateToChange, String id, String title, String text, boolean isPatient){
        stateToChange.setValue(true);
        createNotification(id, title, text, isPatient);
    }

    private void createNotification(String id, String title, String text, boolean isPatient){
        PendingIntent pIntent;
        if (isPatient) {
            pIntent = setupActivityToRun(PatientPersonalAppointments.class);
        } else {
            pIntent = setupActivityToRun(DoctorAppointmentsList.class);
        }

        if (auth.getCurrentUser()!=null) {
            if (id.equals(auth.getCurrentUser().getUid())) {
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "appointmentID")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(0x00000002)
                        .setContentIntent(pIntent);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(0, mBuilder.build());
            }
        }
    }
}
