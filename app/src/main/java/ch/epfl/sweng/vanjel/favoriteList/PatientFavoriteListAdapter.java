package ch.epfl.sweng.vanjel.favoriteList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.favorite.DoctorFavorite;
import ch.epfl.sweng.vanjel.recyclerViewAdapter;

public class PatientFavoriteListAdapter extends recyclerViewAdapter<PatientFavoriteListAdapter.ViewHolder> {

    private Context context;
    List<DoctorFavorite> favoriteDoctorList;

    public PatientFavoriteListAdapter(Context context){
        this.context = context;
        this.favoriteDoctorList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PatientFavoriteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PatientFavoriteListAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_favorite_list, viewGroup, false), i);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientFavoriteListAdapter.ViewHolder viewHolder, int i) {
        String firstName = favoriteDoctorList.get(i).getFirstName();
        String lastName = favoriteDoctorList.get(i).getLastName();
        viewHolder.firstNameFavorite.setText(firstName);
        viewHolder.lastNameFavorite.setText(lastName);

    }

    @Override
    public int getItemCount() { return favoriteDoctorList.size(); }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView firstNameFavorite, lastNameFavorite;
        int favoriteListIndex; //number of the cardview, index in appointmentList

        ViewHolder(@NonNull View itemView, int i) {
            super(itemView);

            firstNameFavorite = itemView.findViewById(R.id.firstNameDoctorFavorite);
            lastNameFavorite = itemView.findViewById(R.id.lastNameDoctorFavorite);
            favoriteListIndex = i;

        }
    }
}
