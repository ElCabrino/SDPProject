package ch.epfl.sweng.vanjel.patientInfo;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * @author Vincent CABRINI
 * @reviewer Nicolas BRANDT
 */
public class InfoList<Info> extends ArrayAdapter<Info> {

    private Activity context;
    private List<Info> infoList;
    private int idLayout, idTextView;
    private ch.epfl.sweng.vanjel.patientInfo.Info info;

    public InfoList(Activity context, List<Info> infoList, int idLayout, int idTextView) {
        super(context, idLayout, infoList);
        this.idTextView = idTextView;
        this.idLayout = idLayout;
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(idLayout,null,true);
        TextView textView = (TextView) listViewItem.findViewById(idTextView);

        info = (ch.epfl.sweng.vanjel.patientInfo.Info) infoList.get(position);

        if(info instanceof Surgery) {
            textView.setText(((Surgery) info).type + " in " + ((Surgery) info).getYear());
            //textViewDrugReactions.setText(drugReaction.getDrug() + " : " + drugReaction.getReaction());
        } else if (info instanceof DrugReaction) {
            textView.setText(((DrugReaction) info).getDrug() + " : " + ((DrugReaction) info).getReaction());
        } else if(info instanceof Drug) {
            textView.setText(((Drug) info).getDrug() + " " + ((Drug) info).getDosage()+ ", " +
                    ((Drug) info).getFrequency() + " per day");
        }
        else {
            textView.setText(info.getAndroidInfo());
        }

        return listViewItem;

    }

}