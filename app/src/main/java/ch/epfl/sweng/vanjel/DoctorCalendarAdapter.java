package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class DoctorCalendarAdapter extends RecyclerView.Adapter<DoctorCalendarAdapter.ViewHolder> {


    private List<DoctorCalendarItem> calendarItems;
    private Context context;

    public DoctorCalendarAdapter(List<DoctorCalendarItem> calendarItems, Context context) {
        this.calendarItems = calendarItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.doctor_calendar_layout,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DoctorCalendarItem doctorCalendarItem = calendarItems.get(i);

        viewHolder.textViewHead.setText(doctorCalendarItem.getHead());
        viewHolder.textViewDesc.setText(doctorCalendarItem.getDesc());

    }

    @Override
    public int getItemCount() {
        return calendarItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHead;
        public TextView textViewDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);

        }
    }

}
