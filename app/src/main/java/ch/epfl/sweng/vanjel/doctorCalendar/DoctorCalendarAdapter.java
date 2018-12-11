package ch.epfl.sweng.vanjel.doctorCalendar;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;

/**
 * Etienne?? Nicolas??
 * @author ??
 * @reviewer Vincent Cabrini
 */
public class DoctorCalendarAdapter extends RecyclerViewAdapter<DoctorCalendarAdapter.ViewHolder> {

    private ArrayList<DoctorCalendarItem> mData;

    DoctorCalendarAdapter(ArrayList<DoctorCalendarItem> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctor_calendar,
                parent,false);
        return new ViewHolder(view, R.id.date, R.id.patient, R.id.delete);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.date.setText(mData.get(position).getDate());
        holder.patient.setText(mData.get(position).getPatient().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {delete(holder.getAdapterPosition());
                                             }
                                         }
        );
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private void delete(int pos){
        mData.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, mData.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView patient;
        Button delete;

        public ViewHolder(@NonNull View itemView, int id1, int id2, int id3) {
            super(itemView);
            date = itemView.findViewById(id1);
            patient = itemView.findViewById(id2);
            delete = itemView.findViewById(id3);
        }

    }

}
