package ch.epfl.sweng.vanjel.doctorAppointment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.appointment.Appointment;
import ch.epfl.sweng.vanjel.appointment.AppointmentComparator;
import ch.epfl.sweng.vanjel.doctorInformation.DoctorInformation;
import ch.epfl.sweng.vanjel.patientInfo.DoctorPatientInfo;
import ch.epfl.sweng.vanjel.searchDoctor.SearchDoctor;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;

/**
 * @author Vincent CABRINI
 * @reviewer Aslam CADER
 * @reviewer Etienne CAQUOT
 */
public class DoctorAppointmentListAdapter extends RecyclerViewAdapter<DoctorAppointmentListAdapter.ViewHolder> {

    private final Context context;
    ArrayList<Appointment> appointmentsList;

    private List<String> treatedPatientsMap;


    private final FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

    DoctorAppointmentListAdapter(Context context){
        this.context = context;

        this.appointmentsList = new ArrayList<>();

    }

    DoctorAppointmentListAdapter(Context context, ArrayList<Appointment> appointments){
        this.context = context;

        this.appointmentsList = appointments;

        Collections.sort(appointmentsList, new AppointmentComparator());

    }

    @NonNull
    @Override
    public DoctorAppointmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorAppointmentListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_appointment_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentListAdapter.ViewHolder viewHolder, int i) {
        Appointment appointment = appointmentsList.get(i);
        viewHolder.bind(appointment);

        final String patientUID = appointmentsList.get(i).getPatientUid();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, DoctorPatientInfo.class).putExtra("patientUID", patientUID));
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView dayTextView;
        TextView fromHourTextView;
        String appointmentId;
        String doctorUid;
        String patientUid;
        String day;
        Button acceptRequestButton;
        Button declineRequestButton;
        Button forwardRequestButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            fromHourTextView = itemView.findViewById(R.id.fromTextView);
            acceptRequestButton = itemView.findViewById(R.id.acceptAppointmentButton);
            acceptRequestButton.setOnClickListener(this);
            forwardRequestButton = itemView.findViewById(R.id.forwardAppointmentButton);
            forwardRequestButton.setOnClickListener(this);
            declineRequestButton = itemView.findViewById(R.id.declineAppointmentButton);
            declineRequestButton.setOnClickListener(this);

        }

        public void bind(Appointment appointment) {
            dayTextView.setText(appointment.getDay());
            fromHourTextView.setText(appointment.getHour());
            appointmentId = appointment.getAppointmentID();
            doctorUid = appointment.getDoctorUid();
            patientUid = appointment.getPatientUid();
            day = appointment.getDay();
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            switch (i) {
                case R.id.acceptAppointmentButton:
                    acceptRequest();
                    break;
                case R.id.forwardAppointmentButton:
                    forwardRequest();
                    break;
                case R.id.declineAppointmentButton:
                    declineRequest();
                    break;
                default:
                    break;
            }
        }

        void acceptRequest() {
            createDialog();
            Toast.makeText(context, "Accept for " + dayTextView.getText(), Toast.LENGTH_LONG).show();
        }

        void declineRequest() {
            deleteRequestFirebase();
        }

        void forwardRequest() {
            Intent intent = new Intent(context, SearchDoctor.class);
            intent.putExtra("isForward", true);
            intent.putExtra("doctor1Forward", doctorUid);
            intent.putExtra("patientForward", patientUid);
            context.startActivity(intent);
        }

        void createDialog() {
            //builder for dialog window
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            //add the prompt sentence
            builder.setMessage("Choose the duration for the appointment");
            //set up the input field for duration
            final EditText durationInputView = new EditText(context);
            durationInputView.setId(R.id.durationChosenByDoctor);
            durationInputView.setInputType(InputType.TYPE_CLASS_NUMBER);
            durationInputView.setGravity(Gravity.CENTER);
            builder.setView(durationInputView);
            //set up the confirm button
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    modifyDurationFirebase(durationInputView.getText().toString().trim());
                }
            });
            //set up the cancel button
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }

        private void deleteRequestFirebase() {
            appointmentsList = new ArrayList<>(); //the list is refreshed in DoctorAppointmentList

            database.getReference("Requests").child(appointmentId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "Appointment declined", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "An error occurred when declining the appointment", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void modifyDurationFirebase(String duration) {
            storeTreatedPatientFirebase(doctorUid, patientUid, day);
            appointmentsList = new ArrayList<>();
            database.getReference("Requests").child(appointmentId).child("duration").setValue(duration).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, "A notification has been sent to the patient", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, "An error occurred when notifying the patient", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void storeTreatedPatientFirebase(final String doctorUID, final String patientUID, final String date) {
            database.getReference("Doctor").child(doctorUID).child("TreatedPatientsActivity").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    treatedPatientsMap = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        treatedPatientsMap.add(dataSnapshot1.getValue(String.class));
                    }
                    if (treatedPatientsMap == null || !(treatedPatientsMap.contains(patientUID))) {
                        Map<String, Object> newPatient = new HashMap<>();
                        newPatient.put(patientUID, date);
                        database.getReference("Doctor").child(doctorUID).child("TreatedPatientsActivity").updateChildren(newPatient);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.d("ERROR", "The read failed: " + databaseError.getCode());
                }
            });
        }
    }
}