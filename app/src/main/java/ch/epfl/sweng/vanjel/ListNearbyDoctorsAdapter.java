package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListNearbyDoctorsAdapter extends recyclerViewAdapter<ListNearbyDoctorsAdapter.ViewHolder> {

    ArrayList<Doctor> doctors;
    HashMap<String, Doctor> doctorHashMap;
    Context context;
    private LatLng userLocation;


    public ListNearbyDoctorsAdapter(Context context, HashMap<String, Doctor> data, LatLng userLocation) {

        this.doctorHashMap = data;
        this.context = context;
        this.userLocation = userLocation;

        doctors = new ArrayList<>();

//         loop for to take doctorHashmap to doctor
        for (Doctor doc : doctorHashMap.values())
            doctors.add(doc);


    }

    @NonNull
    @Override
    public ListNearbyDoctorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ListNearbyDoctorsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_distance, viewGroup, false));
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_doctor_cardview,
//                viewGroup,false);
//        ViewHolder holder = new ViewHolder(view);
//
//        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.firstName.setText(doctors.get(i).getFirstName());
        viewHolder.lastName.setText(doctors.get(i).getLastName());
        viewHolder.activity.setText(doctors.get(i).getActivity());
        viewHolder.distance.setText(String.format("%.2f", doctors.get(i).getDistance(userLocation,context) / 1000.0) + " km");

        final int id = i;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorInformation.class);
                for (Map.Entry<String,Doctor> entry : doctorHashMap.entrySet()) {
                    if (doctors.get(id).equals(entry.getValue())) {
                        intent.putExtra("doctorUID", entry.getKey());
                    }
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, activity, distance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            activity = itemView.findViewById(R.id.activity);
            distance = itemView.findViewById(R.id.distance);

        }

    }
}