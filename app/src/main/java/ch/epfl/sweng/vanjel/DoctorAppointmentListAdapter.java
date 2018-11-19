package ch.epfl.sweng.vanjel;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorAppointmentListAdapter extends recyclerViewAdapter<DoctorAppointmentListAdapter.ViewHolder> {

    Context context;
    ArrayList<Appointment> appointmentsList = new ArrayList<>();

    public DoctorAppointmentListAdapter(Context context, ArrayList<Appointment> appointments){
        this.context = context;

        for (Appointment a: appointments){
            this.appointmentsList.add(a);
        }

    }

    @NonNull
    @Override
    public DoctorAppointmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorAppointmentListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_appointment_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAppointmentListAdapter.ViewHolder viewHolder, int i) {
        String day = appointmentsList.get(i).getDay();
        String hour = appointmentsList.get(i).getHour();
        viewHolder.dayTextView.setText(day);
        viewHolder.fromHourTextView.setText(hour);

    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayCardView, dayTextView, hourCardView, fromHourTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayCardView = itemView.findViewById(R.id.dayCardView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            hourCardView = itemView.findViewById(R.id.hourCardview);
            fromHourTextView = itemView.findViewById(R.id.fromTextView);

        }

    }
}
