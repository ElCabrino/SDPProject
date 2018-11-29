package ch.epfl.sweng.vanjel.forwardRequest;

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

import ch.epfl.sweng.vanjel.DoctorComingAppointmentsAdapter;
import ch.epfl.sweng.vanjel.DoctorInformation;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.recyclerViewAdapter;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class ForwardRequestAdapter extends recyclerViewAdapter<ForwardRequestAdapter.ViewHolder> {


    ArrayList<Forward> forward;

    Context context;

    public ForwardRequestAdapter(Context context, ArrayList<Forward> forward){
        this.context = context;
        this.forward = forward;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForwardRequestAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_forwarded_requests_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.doctor1.setText(forward.get(i).getDoctor1());
        //viewHolder.doctor2.setText(forward.get(i).getDoctor2());
        viewHolder.doctor2.setText("Doctor2");

        final String doctorUID = forward.get(i).getDoctor2();

        // TODO: button onClick listener

        viewHolder.doctorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DoctorInformation.class);
                intent.putExtra("doctorUID", doctorUID);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return forward.size();  }


    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView doctor1, doctor2;
        Button doctorDetails, delete;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            doctor1 = itemView.findViewById(R.id.firstDoctorRequested);
            doctor2 = itemView.findViewById(R.id.secondDoctorAdviced);
            doctorDetails = itemView.findViewById(R.id.seeForwadedDoctor);
            delete = itemView.findViewById(R.id.deleteForwardRequest);
        }
    }

}
