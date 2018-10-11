package ch.epfl.sweng.vanjel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ConditionList extends ArrayAdapter<Condition> {

    private Activity context;
    private List<Condition> conditionList;


    public ConditionList(Activity context, List<Condition> conditionList) {
        super(context,R.layout.list_conditions_layout,conditionList);
        this.context = context;
        this.conditionList = conditionList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_conditions_layout,null,true);
        TextView textViewConditions = (TextView) listViewItem.findViewById(R.id.textViewConditions);

        Condition condition = conditionList.get(position);

        textViewConditions.setText(condition.getCondition());

        return listViewItem;

    }
}
