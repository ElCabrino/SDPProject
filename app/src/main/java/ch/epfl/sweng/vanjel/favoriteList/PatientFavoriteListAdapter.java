package ch.epfl.sweng.vanjel.favoriteList;

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

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.RecyclerViewAdapter;
import ch.epfl.sweng.vanjel.doctorInformation.DoctorInformation;
import ch.epfl.sweng.vanjel.favorite.DoctorFavorite;

/**
 * Class used to populate the activity for favorite Doctors
 *
 * @reviewer Etienne CAQUOT
 */
public class PatientFavoriteListAdapter extends RecyclerViewAdapter<PatientFavoriteListAdapter.ViewHolder> {

    private final Context context;
    List<DoctorFavorite> favoriteDoctorList;

    PatientFavoriteListAdapter(Context context){
        this.context = context;
        this.favoriteDoctorList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PatientFavoriteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PatientFavoriteListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_favorite_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PatientFavoriteListAdapter.ViewHolder viewHolder, int i) {
        String firstName = favoriteDoctorList.get(i).getFirstName();
        String lastName = favoriteDoctorList.get(i).getLastName();
        String number = favoriteDoctorList.get(i).getStreetNumber();
        String activity = favoriteDoctorList.get(i).getProfession();
        String street = favoriteDoctorList.get(i).getStreet();
        String city = favoriteDoctorList.get(i).getCity();
        String country = favoriteDoctorList.get(i).getCountry();
        viewHolder.firstNameFavorite.setText(firstName);
        viewHolder.lastNameFavorite.setText(lastName);
        viewHolder.activityFavorite.setText(activity);
        viewHolder.streetFavorite.setText(street);
        viewHolder.numberFavorite.setText(number);
        viewHolder.cityFavorite.setText(city);
        viewHolder.countryFavorite.setText(country);
        viewHolder.doctorUid = favoriteDoctorList.get(i).getDoctorID();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorInformation.class);
                intent.putExtra("doctorUID", viewHolder.doctorUid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { return favoriteDoctorList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        final TextView firstNameFavorite;
        final TextView lastNameFavorite;
        final TextView numberFavorite;
        final TextView streetFavorite;
        final TextView cityFavorite;
        final TextView countryFavorite;
        final TextView activityFavorite;
        String doctorUid;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstNameFavorite = itemView.findViewById(R.id.firstName);
            lastNameFavorite = itemView.findViewById(R.id.lastName);
            activityFavorite = itemView.findViewById(R.id.activity);
            streetFavorite = itemView.findViewById(R.id.street);
            numberFavorite = itemView.findViewById(R.id.streetNumber);
            cityFavorite = itemView.findViewById(R.id.city);
            countryFavorite= itemView.findViewById(R.id.country);

        }
    }
}
