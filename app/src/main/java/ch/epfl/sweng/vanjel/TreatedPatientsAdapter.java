package ch.epfl.sweng.vanjel;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class TreatedPatientsAdapter extends recyclerViewAdapter<TreatedPatientsAdapter.ViewHolder>  {

    ArrayList<Patient> treatedPatients;
    HashMap<String, Patient> patientsHashMap;
    Context context;

    public TreatedPatientsAdapter(Context context, HashMap<String, Patient> data){

        this.patientsHashMap = data;
        this.context = context;

        treatedPatients = new ArrayList<>();

        for(Patient patient: patientsHashMap.values())
            treatedPatients.add(patient);
    }

    @NonNull
    @Override
    public TreatedPatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TreatedPatientsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.firstName.setText(treatedPatients.get(i).getFirstName());
        viewHolder.lastName.setText(treatedPatients.get(i).getLastName());
        viewHolder.street.setText(treatedPatients.get(i).getStreet());
        viewHolder.streetNumber.setText(treatedPatients.get(i).getStreetNumber());
        viewHolder.city.setText(treatedPatients.get(i).getCity());
        viewHolder.country.setText(treatedPatients.get(i).getCountry());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorPatientInfo.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return treatedPatients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, activity, street, streetNumber, city, country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            activity = itemView.findViewById(R.id.activity);
            activity.setVisibility(View.GONE);
            street = itemView.findViewById(R.id.street);
            streetNumber = itemView.findViewById(R.id.streetNumber);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
        }
    }
}
