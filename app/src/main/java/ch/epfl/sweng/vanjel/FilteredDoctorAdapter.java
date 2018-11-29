package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Aslam CADER
 * @reviewer
 */
public class FilteredDoctorAdapter extends recyclerViewAdapter<FilteredDoctorAdapter.ViewHolder> {

    ArrayList<Doctor> doctors;
    HashMap<String, Doctor> doctorHashMap;
    Context context;

    Boolean isForward;


    public FilteredDoctorAdapter(Context context, HashMap<String, Doctor> data, Boolean isForward){

        this.doctorHashMap = data;
        this.context = context;
        this.isForward = isForward;

        doctors = new ArrayList<>();

        // loop for to take doctorHashmap to doctor
        for(Doctor doc: doctorHashMap.values())
            doctors.add(doc);


    }

    @NonNull
    @Override
    public FilteredDoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FilteredDoctorAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.firstName.setText(doctors.get(i).getFirstName());
        viewHolder.lastName.setText(doctors.get(i).getLastName());
        viewHolder.activity.setText(doctors.get(i).getActivity());
        viewHolder.street.setText(doctors.get(i).getStreet());
        viewHolder.streetNumber.setText(doctors.get(i).getStreetNumber());
        viewHolder.city.setText(doctors.get(i).getCity());
        viewHolder.country.setText(doctors.get(i).getCountry());

        final int id = i;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorInformation.class);

                // we need to give the uid of the doctor the user want to see
                String key = "";
                for(Map.Entry entry: doctorHashMap.entrySet()){
                    if(doctors.get(id).equals(entry.getValue())){
                        key = (String) entry.getKey();
                        break; //breaking because its one to one map
                    }
                }
                intent.putExtra("doctorUID", key);
                context.startActivity(intent);
            }
        });

        // TODO: add onclickListener for forward to this doctor
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, activity, street, streetNumber, city, country;

        Button forwardButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            activity = itemView.findViewById(R.id.activity);
            street = itemView.findViewById(R.id.street);
            streetNumber = itemView.findViewById(R.id.streetNumber);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            forwardButton = itemView.findViewById(R.id.forwardButtonInFilteredDoctors);

            if(!isForward) forwardButton.setVisibility(View.INVISIBLE);
        }
    }
}