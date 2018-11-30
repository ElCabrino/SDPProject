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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    FirebaseDatabase database = FirebaseDatabaseCustomBackend.getInstance();
    DatabaseReference ref;

    Boolean isForward;
    HashMap<String, Object> isForwardDetails;
    private HashMap<String, Doctor> allDoctors;


    public FilteredDoctorAdapter(Context context, HashMap<String, Doctor> data, Boolean isForward, HashMap<String, Object> isForwardDetails, HashMap<String, Doctor> allDoctors){

        this.doctorHashMap = data;
        this.context = context;
        this.isForward = isForward;
        this.isForwardDetails = isForwardDetails;
        this.allDoctors = allDoctors;

        doctors = new ArrayList<>();

        ref = database.getReference("Forwards");

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.firstName.setText(doctors.get(i).getFirstName());
        viewHolder.lastName.setText(doctors.get(i).getLastName());
        viewHolder.activity.setText(doctors.get(i).getActivity());
        viewHolder.street.setText(doctors.get(i).getStreet());
        viewHolder.streetNumber.setText(doctors.get(i).getStreetNumber());
        viewHolder.city.setText(doctors.get(i).getCity());
        viewHolder.country.setText(doctors.get(i).getCountry());

        final int id = i;

        // we need to give the uid of the doctor the user want to see
        final String finalKey = getDoctorUIDWithKey(id);


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
                isForwardDetails.put("doctor1name", allDoctors.get(doctor1UID).toString());
                DatabaseReference r1 = ref.push();
                Task r2 = r1.updateChildren(isForwardDetails);
                r2.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Forward successfully done.", Toast.LENGTH_SHORT).show();
                        // TODO: delete request??
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed forward.", Toast.LENGTH_SHORT).show();
                    }
                });
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