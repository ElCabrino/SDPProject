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
import java.util.List;
import java.util.Map;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;
import ch.epfl.sweng.vanjel.models.Patient;
import ch.epfl.sweng.vanjel.patientInfo.DoctorPatientInfo;

/**
 * Class used to populate the activity_treated_patients recyclerView
 *
 * @reviewer Etienne CAQUOT
 */
public class TreatedPatientsAdapter extends RecyclerViewAdapter<TreatedPatientsAdapter.ViewHolder> {

    private final ArrayList<Patient> treatedPatients;
    private final List<String> mapPatients;
    private final Context context;

    TreatedPatientsAdapter(Context context, Map<String,Patient> data){
        this.context = context;
        this.treatedPatients = new ArrayList<>();
        this.treatedPatients.addAll(data.values());
        this.mapPatients = new ArrayList<>();
        this.mapPatients.addAll(data.keySet());
    }

    @NonNull
    @Override
    public TreatedPatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TreatedPatientsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_doctor_cardview, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TreatedPatientsAdapter.ViewHolder viewHolder,int i) {
        viewHolder.firstName.setText(treatedPatients.get(i).getFirstName());
        viewHolder.lastName.setText(treatedPatients.get(i).getLastName());
        viewHolder.street.setText(treatedPatients.get(i).getStreet());
        viewHolder.streetNumber.setText(treatedPatients.get(i).getStreetNumber());
        viewHolder.city.setText(treatedPatients.get(i).getCity());
        viewHolder.country.setText(treatedPatients.get(i).getCountry());

        final int pos = i;

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorPatientInfo.class).putExtra("patientUID",mapPatients.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return treatedPatients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView firstName;
        final TextView lastName;
        final TextView activity;
        final TextView street;
        final TextView streetNumber;
        final TextView city;
        final TextView country;
        final TextView drStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            drStatus = itemView.findViewById(R.id.drStatus);
            drStatus.setVisibility(View.GONE);
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
