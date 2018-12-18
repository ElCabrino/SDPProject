package ch.epfl.sweng.vanjel;

import android.view.View;
import android.widget.TextView;

public class LayoutHelper {

    public static void adaptLayoutIfNoData(boolean isEmpty, TextView textView) {
        if(isEmpty){
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
