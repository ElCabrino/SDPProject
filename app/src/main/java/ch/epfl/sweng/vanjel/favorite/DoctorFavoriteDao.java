package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DoctorFavoriteDao {

    @Query("SELECT * FROM doctorfavorite")
    List<DoctorFavorite> getAll();

    @Query("SELECT * FROM doctorfavorite WHERE doctorID = :id")
    List<DoctorFavorite> getWithKey(String id);

    @Query("DELETE FROM doctorfavorite")
    void nukeTable();

    @Insert
    void insertAll(DoctorFavorite... doctorFavorite);

    @Delete
    void delete(DoctorFavorite doctorFavorite);
}
