package ch.epfl.sweng.vanjel.favoriteList;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.favorite.LocalDatabase;

public class PatientFavoriteListActivity extends AppCompatActivity {

    private PatientFavoriteListAdapter adapter;
    private LocalDatabase localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshView();
    }

    private void refreshView(){
        setContentView(R.layout.activity_favorite_list);
        //set up adapter
        RecyclerView recyclerView = findViewById(R.id.doctorCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientFavoriteListAdapter(this);
        recyclerView.setAdapter(adapter);
        //local database reference
        this.localDatabase = Room.databaseBuilder(this,
                LocalDatabase.class, "local-database").allowMainThreadQueries().build();
        fetchDataLocalDatabase();
    }

    private void fetchDataLocalDatabase() {
        adapter.favoriteDoctorList = localDatabase.doctorFavoriteDao().getAll();
    }

    public int getAdapterCount(){
        return adapter.getItemCount();
    }
}
