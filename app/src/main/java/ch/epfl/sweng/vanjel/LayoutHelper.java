package ch.epfl.sweng.vanjel;

import android.view.View;
import android.widget.TextView;

/**
 * @author Etienne CAQUOT
 */
public class LayoutHelper {

    /**
     * method used to show or not a textView depending a boolean value
     * @param isEmpty boolean used to set the visibility of the textView
     * @param textView the textView of which we want to change the visibility
     */
    public static void adaptLayoutIfNoData(boolean isEmpty, TextView textView) {
        if(isEmpty){
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
