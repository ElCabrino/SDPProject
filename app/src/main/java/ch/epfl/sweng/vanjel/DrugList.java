package ch.epfl.sweng.vanjel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DrugList extends ArrayAdapter<Drug> {

    private Activity context;
    private List<Drug> drugList;

    public DrugList(Activity context, List<Drug> drugList) {
        super(context,R.layout.list_drugs_layout,drugList);
        this.context = context;
        this.drugList = drugList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_drugs_layout,null,true);
        TextView textViewDrugs = (TextView) listViewItem.findViewById(R.id.textViewDrugs);

        Drug drug = drugList.get(position);

        textViewDrugs.setText(drug.getDrug() + " " + drug.getDosage() + ", " +  drug.getFrequency() + " times per day");

        return listViewItem;

    }

}
