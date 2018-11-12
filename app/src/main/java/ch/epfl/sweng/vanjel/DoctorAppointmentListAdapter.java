package ch.epfl.sweng.vanjel;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DoctorAppointmentListAdapter extends recyclerViewAdapter<DoctorAppointmentListAdapter.ViewHolder> {

    Context context;
    ArrayList<Appointment> appointmentsList;

    public DoctorAppointmentListAdapter(Context context, ArrayList<Appointment> appointments){
        this.context = context;
        this.appointmentsList = appointments;

    }

    @NonNull
    @Override
    public DoctorAppointmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorAppointmentListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.dayTextView.setText(appointmentsList.get(i).getDay());
        viewHolder.fromHourTextView.setText(appointmentsList.get(i).getHour());

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayCardView, dayTextView, hourCardView, fromHourTextView, toHourTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayCardView = itemView.findViewById(R.id.dayCardView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            hourCardView = itemView.findViewById(R.id.hourCardview);
            fromHourTextView = itemView.findViewById(R.id.fromTextView);
            toHourTextView = itemView.findViewById(R.id.toTextView);

        }

    }
}
