package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.Room;
import android.content.Context;

import ch.epfl.sweng.vanjel.Doctor;

public final class LocalDatabaseService {

    private LocalDatabase db;
    private DoctorFavorite doctorFavorite;

    public LocalDatabaseService(Context context, Doctor doctor, String DoctorUid) {
        this.db = Room.databaseBuilder(context,
                LocalDatabase.class, "local-database").allowMainThreadQueries().build();
        this.doctorFavorite = new DoctorFavorite(DoctorUid, doctor.getFirstName(), doctor.getLastName(),
                doctor.getStreet(), doctor.getStreetNumber(), doctor.getCity(), doctor.getCountry(), doctor.getActivity());
    }

    public void save(){
        db.doctorFavoriteDao().insertAll(doctorFavorite);
    }

    public void delete(){
        db.doctorFavoriteDao().delete(this.doctorFavorite);
    }
}
