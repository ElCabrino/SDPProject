package ch.epfl.sweng.vanjel;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PatientPersonalAppointments extends AppCompatActivity {

    DatabaseReference dbAp;
    DatabaseReference dbDoc;

    ListView listViewAp;
    String id;

    List<PtPersonalAppointment> apList = new ArrayList<>();
    // to optimize
    HashMap<String,ArrayList<String>> idToDoc = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_personal_appointments);

        id = FirebaseAuth.getInstance().getUid();
        if (id == null) { id = "jEd45lJyOTQP2yeyB9OYxpbEEXa2";}

        dbAp = FirebaseDatabase.getInstance().getReference("Requests");
        dbDoc = FirebaseDatabase.getInstance().getReference("Doctor");

        listViewAp = (ListView) findViewById(R.id.ptPersonalAppointmentsListView);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //recover doctor names
        populateDocMap();

        dbAp.addValueEventListener(new ValueEventListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apList.clear();
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    //PtPersonalAppointment ap = ds.getValue(PtPersonalAppointment.class);
                    String date = dateSnapshot.getKey();
                        for (DataSnapshot ds : dateSnapshot.getChildren()) {
                            if (ds.child("patient").getValue(String.class).equals(id)) {
                                String docId = ds.child("doctor").getValue(String.class);
                                String doc = idToDoc.get(docId).get(0);
                                String loc = idToDoc.get(docId).get(1);
                                String time = ds.child("time").getValue(String.class);
                                String duration = ds.child("duration").getValue(String.class);
                                PtPersonalAppointment ap = new PtPersonalAppointment(doc, loc, date, time,duration);
                                apList.add(ap);
                            }
                        }
                }

                apList.sort(new appointmentsComparator());
                PtPersonalAppointmentsList adapter = new PtPersonalAppointmentsList(PatientPersonalAppointments.this,apList);
                listViewAp.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private class appointmentsComparator implements Comparator<PtPersonalAppointment> {
        @Override
        public int compare(PtPersonalAppointment ap1, PtPersonalAppointment ap2) {
            int diff = parseDate(ap1.getDate())- parseDate(ap2.getDate());
            if (diff == 0) {
                return parseTime(ap1.getTime())- parseTime(ap2.getTime());
            }
            return diff;
        }

        private int parseDate(String date) {
            String d = date.substring(8,10);
            String m = date.substring(4,7);
            String y = date.substring(11,15);
            return yearNum(y) + monthNum(m) + dayNum(d);
        }

        private int parseTime(String time) {
            return Integer.parseInt(time.substring(0,2))*100+
                    Integer.parseInt(time.substring(3,5));
        }

        private int dayNum(String d) {
            return Integer.parseInt(d);
        }

        private int monthNum(String m) {
            int i = 1;
            switch (m) {
                case "Jan":
                    break;
                case "Feb":
                    i = 2;
                    break;
                case "Mar":
                    i = 3;
                    break;
                case "Apr":
                    i = 4;
                    break;
                case "May":
                    i = 5;
                    break;
                case "Jun":
                    i = 6;
                    break;
                case "Jul":
                    i = 7;
                    break;
                case "Aug":
                    i = 8;
                    break;
                case "Sep":
                    i = 9;
                    break;
                case "Oct":
                    i = 10;
                    break;
                case "Nov":
                    i = 11;
                    break;
                case "Dec":
                    i = 12;
                    break;
            }
            return i*100;
        }

        private int yearNum(String y) {
            int i = Integer.parseInt(y);
            return i * 10000;
        }

    }

    private void populateDocMap() {
        //recover doctor names and locations
        dbDoc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idToDoc.clear();
                for (DataSnapshot idSnapshot : dataSnapshot.getChildren()) {
                    String name = idSnapshot.child("lastName").getValue(String.class);
                    String location = idSnapshot.child("streetNumber").getValue(String.class) + " " +
                            idSnapshot.child("street").getValue(String.class) + " " +
                            idSnapshot.child("city").getValue(String.class);
                    String docId = idSnapshot.getKey();
                    ArrayList<String> list = new ArrayList<>();
                    list.add(name);
                    list.add(location);
                    idToDoc.put(docId,list);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



}
