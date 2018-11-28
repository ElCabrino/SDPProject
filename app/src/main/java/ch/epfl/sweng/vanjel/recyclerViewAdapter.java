package ch.epfl.sweng.vanjel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
/**
 * @author Aslam CADER
 * @reviewer
 */
public abstract class recyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    @NonNull
    @Override
    public abstract T onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override
    public abstract void onBindViewHolder(@NonNull T viewHolder, int i);

    @Override
    public abstract int getItemCount();


}
