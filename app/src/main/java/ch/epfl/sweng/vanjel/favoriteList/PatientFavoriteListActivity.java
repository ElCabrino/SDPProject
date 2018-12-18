package ch.epfl.sweng.vanjel.favoriteList;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ch.epfl.sweng.vanjel.LayoutHelper;
import ch.epfl.sweng.vanjel.R;
import ch.epfl.sweng.vanjel.favorite.LocalDatabase;

public class PatientFavoriteListActivity extends AppCompatActivity {

    private PatientFavoriteListAdapter adapter;
    private LocalDatabase localDatabase;
    private TextView noFavorite;

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
        noFavorite = findViewById(R.id.noFavorite);
        //set up adapter
        RecyclerView recyclerView = findViewById(R.id.doctorFavoriteCardView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PatientFavoriteListAdapter(this);
        recyclerView.setAdapter(adapter);
        //local database reference
        this.localDatabase = Room.databaseBuilder(this,
                LocalDatabase.class, "local-database").allowMainThreadQueries().build();
        fetchDataLocalDatabase();
        LayoutHelper.adaptLayoutIfNoData(adapter.favoriteDoctorList.isEmpty(), noFavorite);
    }

    private void fetchDataLocalDatabase() {
        adapter.favoriteDoctorList = localDatabase.doctorFavoriteDao().getAll();
    }

    public int getAdapterCount(){
        return adapter.getItemCount();
    }
}
