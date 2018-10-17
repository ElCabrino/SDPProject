package ch.epfl.sweng.vanjel;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class InfoList<Info> extends ArrayAdapter<Info> {

    private Activity context;
    private List<Info> infoList;
    private int idLayout;
    private int idTextView;
    private ch.epfl.sweng.vanjel.Info info;

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

        info = (ch.epfl.sweng.vanjel.Info) infoList.get(position);

        textView.setText(info.getAndroidInfo());

        return listViewItem;

    }

}