package ch.epfl.sweng.vanjel;

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
import java.util.Map;

public class DoctorComingAppointmentsAdapter extends recyclerViewAdapter<DoctorComingAppointmentsAdapter.ViewHolder> {

    ArrayList<Appointment> appointments;
    private SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd yyyy");
    Date currentDate;
    Context context;
    public DoctorComingAppointmentsAdapter(Context context, ArrayList<Appointment> givenAppointments){
        this.context = context;
        this.appointments = givenAppointments;
        this.currentDate = new Date();
    }

    @NonNull
    @Override
    public DoctorComingAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DoctorComingAppointmentsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_coming_appointment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorComingAppointmentsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.lastName.setText(appointments.get(i).getDuration().toString());
        viewHolder.time.setText(appointments.get(i).getHour());

        // if today, "Today"
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
        TextView lastName, date, time, duration;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            lastName = itemView.findViewById(R.id.appointmentLastName);
            date = itemView.findViewById(R.id.appointmentDate);
            time = itemView.findViewById(R.id.appointmentTime);
        }
    }
}
