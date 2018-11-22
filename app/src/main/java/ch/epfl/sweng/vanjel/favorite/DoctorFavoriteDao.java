package ch.epfl.sweng.vanjel;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

@Dao
public interface DoctorFavoriteDao {

    @Insert
    void insertAll(DoctorFavorite ... doctorFavorite);

    @Delete
    void delete(DoctorFavorite doctorFavorite);
}
