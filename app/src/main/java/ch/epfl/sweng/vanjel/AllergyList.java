package ch.epfl.sweng.vanjel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AllergyList extends ArrayAdapter<Allergy> {

    private Activity context;
    private List<Allergy> allergyList;


    public AllergyList(Activity context, List<Allergy> allergyList) {
        super(context,R.layout.list_allergies_layout,allergyList);
        this.context = context;
        this.allergyList = allergyList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_allergies_layout,null,true);
        TextView textViewAllergies = (TextView) listViewItem.findViewById(R.id.textViewAllergies);

        Allergy allergy = allergyList.get(position);

        textViewAllergies.setText(allergy.getAllergy());

        return listViewItem;

    }
}
