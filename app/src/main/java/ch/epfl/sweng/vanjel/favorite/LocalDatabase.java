package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DoctorFavorite.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract DoctorFavoriteDao doctorFavoriteDao();
}
