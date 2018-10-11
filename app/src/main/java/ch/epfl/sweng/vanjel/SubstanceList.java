package ch.epfl.sweng.vanjel;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SubstanceList extends ArrayAdapter<Substance> {

    private Activity context;
    private List<Substance> substanceList;

    public SubstanceList(Activity context, List<Substance> substanceList) {
        super(context,R.layout.list_substances_layout, substanceList);
        this.context = context;
        this.substanceList = substanceList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_substances_layout,null,true);
        TextView textViewSubstances = (TextView) listViewItem.findViewById(R.id.textViewSubstances);

        Substance substance = substanceList.get(position);

        textViewSubstances.setText(substance.getSubstance());

        return listViewItem;

    }

}
