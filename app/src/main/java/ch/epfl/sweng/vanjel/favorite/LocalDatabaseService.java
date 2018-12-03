package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import ch.epfl.sweng.vanjel.Doctor;

public final class LocalDatabaseService {

    private LocalDatabase db;

    public LocalDatabaseService(Context context) {
        this.db = Room.databaseBuilder(context,
                LocalDatabase.class, "local-database").allowMainThreadQueries().build();
    }

    public DoctorFavorite doctorToDoctorFavorite(Doctor doctor, String DoctorUid){
        return new DoctorFavorite(DoctorUid, doctor.getFirstName(), doctor.getLastName(),
                doctor.getStreet(), doctor.getStreetNumber(), doctor.getCity(), doctor.getCountry(), doctor.getActivity());
    }

    public List<DoctorFavorite> getAll() { return db.doctorFavoriteDao().getAll();}

    public void save(Doctor doctor, String doctorUid){
        db.doctorFavoriteDao().insertAll(doctorToDoctorFavorite(doctor, doctorUid));
    }

    public void delete(Doctor doctor, String doctorUid){
        db.doctorFavoriteDao().delete(doctorToDoctorFavorite(doctor, doctorUid));
    }

    public void nuke() { db.doctorFavoriteDao().nukeTable();}

    public List<DoctorFavorite> getWithKey(String id){ return db.doctorFavoriteDao().getWithKey(id);}
}
