package ch.epfl.sweng.vanjel.patientInfo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;
/**
 * Class to allow the listViews to expand and be displayed correctly.
 *
 * @author Etienne CAQUOT
 * @reviewer Nicolas BRANDT
 */
public class ExpandedListView extends ListView {

    private int old_count = 0;

    public ExpandedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getChildAt(0) != null) {
            if (getCount() != old_count) {
                old_count = getCount();
                android.view.ViewGroup.LayoutParams params = getLayoutParams();
                params.height = getCount() * (old_count > 0 ? getChildAt(0).getHeight() + 2 : 0);
                setLayoutParams(params);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction()== MotionEvent.ACTION_MOVE)
            return true;
        return super.dispatchTouchEvent(ev);
    }
}