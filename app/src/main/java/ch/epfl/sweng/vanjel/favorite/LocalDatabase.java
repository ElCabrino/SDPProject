package ch.epfl.sweng.vanjel.favorite;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DoctorFavorite.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract DoctorFavoriteDao doctorFavoriteDao();
}
