package ch.epfl.sweng.vanjel.patientAppointment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.epfl.sweng.vanjel.R;

/**
 * @author Nicolas BRANDT
 * @reviewer
 */
class PtPersonalAppointmentsList extends ArrayAdapter<PtPersonalAppointment> {

    private final Activity context;
    private final List<PtPersonalAppointment> appointmentList;

    PtPersonalAppointmentsList(Activity context, List<PtPersonalAppointment> appointmentList) {
        super(context, R.layout.list_pt_personal_appointments, appointmentList);
        this.context = context;
        this.appointmentList = appointmentList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_pt_personal_appointments,null,true);
        TextView textViewDoctorDate = listViewItem.findViewById(R.id.textViewAppointmentDoctorDate);
        TextView textViewTime = listViewItem.findViewById(R.id.textViewAppointmentTime);
        TextView textViewLocation = listViewItem.findViewById(R.id.textViewAppointmentLocation);

        PtPersonalAppointment ap = appointmentList.get(position);

        String confirmationStatus;
        if (ap.pendingStatus) {
            confirmationStatus = "(pending)";
        } else {
            confirmationStatus = "(confirmed)";
        }

        textViewDoctorDate.setText(ap.getDate() + " - Dr." + ap.getDoctor() + " " + confirmationStatus);
        textViewTime.setText(ap.getTime() + ", " + ap.getDuration() + " minutes");
        textViewLocation.setText(ap.getLocation());

        return listViewItem;

    }



}
