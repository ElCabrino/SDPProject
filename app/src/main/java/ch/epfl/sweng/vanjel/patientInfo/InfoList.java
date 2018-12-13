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
class InfoList<Info> extends ArrayAdapter<Info> {

    private final Activity context;
    private final List<Info> infoList;
    private final int idLayout;
    private final int idTextView;

    InfoList(Activity context, List<Info> infoList, int idLayout, int idTextView) {
        super(context, idLayout, infoList);
        this.idTextView = idTextView;
        this.idLayout = idLayout;
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(idLayout,null,true);
        TextView textView = listViewItem.findViewById(idTextView);

        ch.epfl.sweng.vanjel.patientInfo.Info info = (ch.epfl.sweng.vanjel.patientInfo.Info) infoList.get(position);

        if(info instanceof Surgery) {
            String surgeryInfo = ((Surgery) info).getType() + " in " + ((Surgery) info).getYear();
            textView.setText(surgeryInfo);
        } else if (info instanceof DrugReaction) {
            String drugReactionInfo = ((DrugReaction) info).getDrug() + " : " + ((DrugReaction) info).getReaction();
            textView.setText(drugReactionInfo);
        } else if(info instanceof Drug) {
            String drugInfo = ((Drug) info).getDrug() + " " + ((Drug) info).getDosage()+ ", " +
                    ((Drug) info).getFrequency() + " per day";
            textView.setText(drugInfo);
        }
        else {
            textView.setText(info.getAndroidInfo());
        }

        return listViewItem;

    }

}