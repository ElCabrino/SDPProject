package ch.epfl.sweng.vanjel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SurgeryList extends ArrayAdapter<Surgery> {

    private Activity context;
    private List<Surgery> surgeryList;


    public SurgeryList(Activity context, List<Surgery> surgeryList) {
        super(context,R.layout.list_surgeries_layout,surgeryList);
        this.context = context;
        this.surgeryList = surgeryList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_surgeries_layout,null,true);
        TextView textViewSurgeries = (TextView) listViewItem.findViewById(R.id.textViewSurgeries);

        Surgery surgery = surgeryList.get(position);

        textViewSurgeries.setText(surgery.getType() + " " + surgery.getYear());

        return listViewItem;

    }
}
