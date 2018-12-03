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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ch.epfl.sweng.vanjel.DoctorComingAppointmentsAdapter;
import ch.epfl.sweng.vanjel.DoctorInformation;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.recyclerViewAdapter;

/**
 * @author Aslam CADER
 * @reviewer
 */
public class ForwardRequestAdapter extends recyclerViewAdapter<ForwardRequestAdapter.ViewHolder> {

    Map<String,Forward> forwardsMap;
    List<Forward> forwardsList;
    Context context;

    public ForwardRequestAdapter(Context context, Map<String,Forward> forward){
        this.context = context;
        this.forwardsMap = forward;
        this.forwardsList = new ArrayList<>();
        forwardsList.addAll(forward.values());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ForwardRequestAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_forwarded_requests_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.doctor1.setText(forwardsList.get(i).getDoctor1name());
        //viewHolder.doctor2.setText(forward.get(i).getDoctor2());
        viewHolder.doctor2.setText(forwardsList.get(i).getDoctor2name());

        final String doctorUID = forwardsList.get(i).getDoctor2UID();

        viewHolder.doctorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DoctorInformation.class);
                intent.putExtra("doctorUID", doctorUID);
                v.getContext().startActivity(intent);
            }
        });

        final int pos = i;
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Forward", "Clicked");
                String uid = forwardToUid(forwardsList.get(pos));
                Log.d("Forward", uid);
                if(uid != null) {
                    FirebaseDatabase.getInstance().getReference("Forwards").child(uid).removeValue();
                }else {
                    Toast.makeText(context,"Couldn't delete requests",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override
    public int getItemCount() { return forwardsList.size();  }


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

    private String forwardToUid(Forward f){
        for (Map.Entry<String, Forward> entry : forwardsMap.entrySet()) {
            Log.d("Forward",entry.toString());
            if (f.equals(entry.getValue())) {
                Log.d("Forward",entry.getKey());
                return entry.getKey();
            }
        }
        return null;
    }

}
