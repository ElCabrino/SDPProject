package ch.epfl.sweng.vanjel;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;

public class AppointmentNotificationBackgroundService extends Service {

    public static final String APPOINTMENT_SERVICE_INTENT = "ch.epfl.sweng.vanjel.appointmentService";

    private Handler handler;
    private Runnable runnable;
    private Context context = this;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

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
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                for (DataSnapshot dayRequest: dataSnapshot.getChildren()){
                    String doctor = dayRequest.child("doctor").getValue().toString();
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

    private void notifyDoctor(String id) {
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(id)){
            Toast.makeText(this, "INSIDE IFFFFF", Toast.LENGTH_LONG).show();

//            ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
//            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//            //create notification
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Profile.class)
//                    .setContentTitle("yo")
//                    .setContentText("notiif")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        }
    }
}
