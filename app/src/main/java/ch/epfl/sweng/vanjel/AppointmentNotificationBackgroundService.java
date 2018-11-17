package ch.epfl.sweng.vanjel;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentNotificationBackgroundService extends Service {

    public static final String APPOINTMENT_SERVICE_INTENT = "ch.epfl.sweng.vanjel.appointmentService";

    private Handler handler;
    private Runnable runnable;
    private Context context = this;

    private FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    private FirebaseAuth auth = FirebaseAuthCustomBackend.getInstance();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        createNotificationChannel();

        //FirebaseDatabase.getInstance().getReference("TEST").setValue(100000);

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
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d("LISTENERTEST", dataSnapshot.toString());
                Log.d("LISTENERTEST", dataSnapshot.child("doctor").toString());
                String doctor = dataSnapshot.child("doctor").getValue().toString();
                Boolean notify = Boolean.parseBoolean(dataSnapshot.child("doctorNotified").getValue().toString());
                if (!notify) {
                    dataSnapshot.getRef().child("doctorNotified").setValue("true");
                    notifyDoctor(doctor);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
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

    private void notifyDoctor(String id) {
        if (auth.getCurrentUser().getUid().equals(id)){

            //Toast.makeText(this, "INSIDE IFFFFF", Toast.LENGTH_LONG).show();

            //create notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "appointmentID")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("New appointment")
                    .setContentText("A patient took a new appointment!")
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(0x00000002);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 , mBuilder.build());
        }
    }
}
