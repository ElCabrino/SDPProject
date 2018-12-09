package ch.epfl.sweng.vanjel.treatedPatient;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ch.epfl.sweng.vanjel.patientInfo.DoctorPatientInfo;
import ch.epfl.sweng.vanjel.model.Patient;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.doctorInformation.recyclerViewAdapter;

public class TreatedPatientsAdapter extends recyclerViewAdapter<TreatedPatientsAdapter.ViewHolder> {

    ArrayList<Patient> treatedPatients;
    Context context;

    public TreatedPatientsAdapter(Context context, ArrayList<Patient> data){
        this.context = context;
        this.treatedPatients = data;
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
