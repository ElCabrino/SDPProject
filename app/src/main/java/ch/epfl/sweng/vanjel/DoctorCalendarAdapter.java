package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorCalendarAdapter extends RecyclerView.Adapter<DoctorCalendarAdapter.ViewHolder> {

    private ArrayList<DoctorCalendarItem> mData = new ArrayList<>();
    private Context mContext;

    public DoctorCalendarAdapter(ArrayList<DoctorCalendarItem> data, Context context) {
        mData = data;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doctor_calendar,
                parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            patient = itemView.findViewById(R.id.patient);
            delete = itemView.findViewById(R.id.delete);
        }

    }

}