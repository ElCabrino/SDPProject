package ch.epfl.sweng.vanjel;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DoctorAppointmentListAdapter extends recyclerViewAdapter<DoctorAppointmentListAdapter.ViewHolder> {

    Context context;
    ArrayList<Appointment> appointmentsList;

    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();

    public DoctorAppointmentListAdapter(Context context){
        this.context = context;

        this.appointmentsList = new ArrayList<>();

    }

    public DoctorAppointmentListAdapter(Context context, ArrayList<Appointment> appointments){
        this.context = context;

        this.appointmentsList = appointments;

    }

    @NonNull
    @Override
    public DoctorAppointmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorAppointmentListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_appointment_list, viewGroup, false), i);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentListAdapter.ViewHolder viewHolder, int i) {
        String day = appointmentsList.get(i).getDay();
        String hour = appointmentsList.get(i).getHour();
        viewHolder.dayTextView.setText(day);
        viewHolder.fromHourTextView.setText(hour);
        viewHolder.acceptRequestButton.setOnClickListener(viewHolder);
        viewHolder.declineRequestButton.setOnClickListener(viewHolder);

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView dayCardView, dayTextView, hourCardView, fromHourTextView;
        Button acceptRequestButton, declineRequestButton;
        int appointmentListIndex; //number of the cardview, index in appointmentList

        public ViewHolder(@NonNull View itemView, int i) {
            super(itemView);

            dayCardView = itemView.findViewById(R.id.dayCardView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            hourCardView = itemView.findViewById(R.id.hourCardview);
            fromHourTextView = itemView.findViewById(R.id.fromTextView);
            acceptRequestButton = itemView.findViewById(R.id.acceptAppointmentButton);
            declineRequestButton = itemView.findViewById(R.id.declineAppointmentButton);
            appointmentListIndex = i;

        }

        void createDialog(){
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
                    modifyDurationFirebase(appointmentListIndex, durationInputView.getText().toString().trim());
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

        void acceptRequest(){
            createDialog();
            Toast.makeText(context, "Accept for " + dayTextView.getText(), Toast.LENGTH_LONG).show();
        }

        void declineRequest(){
            deleteRequestFirebase(appointmentListIndex);
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            switch (i){
                case R.id.acceptAppointmentButton:
                    acceptRequest();
                    break;
                case R.id.declineAppointmentButton:
                    declineRequest();
                    break;

            }
        }
    }

    private void deleteRequestFirebase(int i){
        String appointmentID = appointmentsList.get(i).getAppointmentID();
        appointmentsList = new ArrayList<>(); //the list_icon is refreshed in DoctorAppointmentList

        database.getReference("Requests").child(appointmentID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    private void modifyDurationFirebase(int i, String duration) {
        String appointmentID = appointmentsList.get(i).getAppointmentID();
        appointmentsList = new ArrayList<>();
        database.getReference("Requests").child(appointmentID).child("duration").setValue(duration).addOnSuccessListener(new OnSuccessListener<Void>() {
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
}
