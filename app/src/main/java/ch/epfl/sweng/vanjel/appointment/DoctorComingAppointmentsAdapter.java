package ch.epfl.sweng.vanjel.appointment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ch.epfl.sweng.vanjel.patientInfo.DoctorPatientInfo;
import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class DoctorComingAppointmentsAdapter extends RecyclerViewAdapter<DoctorComingAppointmentsAdapter.ViewHolder> {

    ArrayList<Appointment> appointments;
    private SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
    Date currentDate;
    Context context;
    private HashMap<String, Patient> patientHashMap;


    public DoctorComingAppointmentsAdapter(Context context, ArrayList<Appointment> givenAppointments, HashMap<String, Patient> patients){

        this.context = context;
        this.appointments = givenAppointments;
        this.currentDate = new Date();
        this.patientHashMap = patients;

    }

    @NonNull
    @Override
    public DoctorComingAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorComingAppointmentsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_coming_appointment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorComingAppointmentsAdapter.ViewHolder viewHolder, int i) {
        final String uid = appointments.get(i).getPatientUid();
        Patient patient = patientHashMap.get(uid);
        viewHolder.lastName.setText(patient.getLastName());
        viewHolder.firstName.setText(patient.getFirstName());
        viewHolder.time.setText(appointments.get(i).getHour());
        viewHolder.duration.setText(appointments.get(i).getDuration() + " min");

        // if today, we want to display "Today"
        Boolean isToday;
        try {
            isToday = isAppointmentToday(appointments.get(i));
        } catch (ParseException e) {
            isToday = false;
            e.printStackTrace();
        }

        if(isToday){
            viewHolder.date.setText("Today");
        } else {
            viewHolder.date.setText(appointments.get(i).getDay());
        }

        // Button click listener to redirect to patient Information
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorPatientInfo.class);

                // we need to give the patient uid
                String key = uid;
                intent.putExtra("patientUID", key);
                context.startActivity(intent);

            }

        });
    }


    public boolean isAppointmentToday(Appointment appointment) throws ParseException {
        currentDate = formatter.parse(formatter.format(currentDate));
        int comparator = formatter.parse(appointment.getDay()).compareTo(currentDate);

        if(comparator == 0) return true;
        else return false;
    }


    @Override
    public int getItemCount() { return appointments.size();  }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView lastName, date, time, duration, firstName, birthday;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            duration = itemView.findViewById(R.id.appointmentDuration);
            lastName = itemView.findViewById(R.id.appointmentLastName);
            date = itemView.findViewById(R.id.appointmentDate);
            time = itemView.findViewById(R.id.appointmentTime);
            firstName = itemView.findViewById(R.id.appointmentFirstName);
        }
    }
}
