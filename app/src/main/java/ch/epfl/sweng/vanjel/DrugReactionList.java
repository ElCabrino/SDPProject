package ch.epfl.sweng.vanjel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DrugReactionList extends ArrayAdapter<DrugReaction> {

    private Activity context;
    private List<DrugReaction> drugReactionList;

    public DrugReactionList(Activity context, List<DrugReaction> drugReactionList) {
        super(context,R.layout.list_drug_reactions_layout,drugReactionList);
        this.context = context;
        this.drugReactionList = drugReactionList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_drug_reactions_layout,null,true);
        TextView textViewDrugReactions = (TextView) listViewItem.findViewById(R.id.textViewDrugReactions);

        DrugReaction drugReaction = drugReactionList.get(position);

        textViewDrugReactions.setText(drugReaction.getDrug() + " : " + drugReaction.getReaction());

        return listViewItem;

    }



}
