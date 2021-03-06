package ch.epfl.sweng.vanjel.searchDoctor;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ch.epfl.sweng.vanjel.models.Doctor;
import ch.epfl.sweng.vanjel.doctorInformation.DoctorInformation;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.firebase.FirebaseDatabaseCustomBackend;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;
/**
 * Conditional display for cardviews in order to display the filtered doctors
 */
/**
 * @author Aslam CADER
 * @reviewer Etienne CAQUOT
 */
public class FilteredDoctorAdapter extends RecyclerViewAdapter<FilteredDoctorAdapter.ViewHolder> {


    private final ArrayList<Doctor> doctors;
    private final HashMap<String, Doctor> doctorHashMap;
    private final Context context;

    private final DatabaseReference ref;

    private final Boolean isForward;
    private final HashMap<String, Object> isForwardDetails;
    private final HashMap<String, Doctor> allDoctors;



    FilteredDoctorAdapter(Context context, HashMap<String, Doctor> data, Boolean isForward, HashMap<String, Object> isForwardDetails, HashMap<String, Doctor> allDoctors){

        this.doctorHashMap = data;
        this.context = context;
        this.isForward = isForward;
        this.isForwardDetails = isForwardDetails;
        this.allDoctors = allDoctors;

        doctors = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
        ref = database.getReference("Forwards");

        // loop for to take doctorHashmap to doctor
        doctors.addAll(doctorHashMap.values());

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

        // we need to give the uid of the doctor the user want to see
        final String finalKey = getDoctorUIDWithKey(i);
        final int id = i;


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorInformation.class).putExtra("doctorUID", finalKey);
                context.startActivity(intent);
            }
        });
        
        // patientUID, doctor1name, doctor2String, doctor2UID (redirection needed)
        viewHolder.forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isForwardDetails.put("doctor2name", doctors.get(id).toString());
                isForwardDetails.put("doctor2UID", finalKey);
                String doctor1UID = (String) isForwardDetails.get("doctor1UID");
                Doctor doctor = allDoctors.get(doctor1UID);
                if (doctor != null) {
                    isForwardDetails.put("doctor1name", doctor.toString());
                    ref.push().updateChildren(isForwardDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Forward successfully done.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed forward.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private String getDoctorUIDWithKey(int id){
        String key = "";
        for(Map.Entry entry: doctorHashMap.entrySet()){
            if(doctors.get(id).equals(entry.getValue())){
                key = (String) entry.getKey();
                break; //breaking because its one to one map
            }
        }

        return key;
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final TextView firstName;
        final TextView lastName;
        final TextView activity;
        final TextView street;
        final TextView streetNumber;
        final TextView city;
        final TextView country;

        final Button forwardButton;



        ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            activity = itemView.findViewById(R.id.activity);
            street = itemView.findViewById(R.id.street);
            streetNumber = itemView.findViewById(R.id.streetNumber);
            city = itemView.findViewById(R.id.city);
            country = itemView.findViewById(R.id.country);
            forwardButton = itemView.findViewById(R.id.forwardButtonInFilteredDoctors);

            if(!isForward) {

                forwardButton.setVisibility(View.GONE);

            } else {

                forwardButton.setVisibility(View.VISIBLE);

            }
        }
    }
}