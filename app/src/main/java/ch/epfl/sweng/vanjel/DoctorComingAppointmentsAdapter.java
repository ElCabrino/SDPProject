package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorComingAppointmentsAdapter extends recyclerViewAdapter<DoctorComingAppointmentsAdapter.ViewHolder> {

    ArrayList<Appointment> appointments;
    Context context;
    public DoctorComingAppointmentsAdapter(Context context, ArrayList<Appointment> givenAppointments){
        this.context = context;
        this.appointments = givenAppointments;
    }

    @NonNull
    @Override
    public DoctorComingAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorComingAppointmentsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_coming_appointment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorComingAppointmentsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.lastName.setText(appointments.get(i).getDuration().toString());
        viewHolder.date.setText(appointments.get(i).getDay());
        viewHolder.time.setText(appointments.get(i).getHour());
    }



    @Override
    public int getItemCount() { return appointments.size();  }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView lastName, date, time, duration;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            lastName = itemView.findViewById(R.id.appointmentLastName);
            date = itemView.findViewById(R.id.appointmentDate);
            time = itemView.findViewById(R.id.appointmentTime);
        }
    }
}
