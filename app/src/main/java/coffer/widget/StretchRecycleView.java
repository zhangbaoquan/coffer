package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：张宝全
 * @date：2020-03-22
 * @Description： 弹性的RecycleView
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class StretchRecycleView extends RecyclerView {

    private static final String TAG = "StretchRecycleView_tag";

    private LinearLayoutManager linearLayoutManager;
    private int viewHeight = 0;

    public StretchRecycleView(@NonNull Context context) {
        super(context);
    }

    public StretchRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        linearLayoutManager = (LinearLayoutManager) layout;
    }

    @Override
    public void onScrollStateChanged(int state) {
        Log.d(TAG, "state:" + state);
        super.onScrollStateChanged(state);
        if (state == 0) {
            int postion = linearLayoutManager.findFirstVisibleItemPosition();
            View view = linearLayoutManager.findViewByPosition(postion);
            int top = view.getTop();
            int offset = 0;
            if (viewHeight == 0) {
                viewHeight = view.getHeight();
            }
            if (top == 0) {
                return;
            } else if (-top < viewHeight / 2) {
                offset = top;
            } else {
                offset = viewHeight + top;
            }
            smoothScrollBy(0, offset);
        }
    }

    int offsetY = 0;


    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        offsetY += dy;

        int first = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
        int last = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        View firstview = linearLayoutManager.findViewByPosition(first);
        if (viewHeight == 0) {
            viewHeight = firstview.getHeight();
        }
        int offseta = firstview.getTop();
        float sx = 1f + (float) offseta / viewHeight;
        if (offsetY == 0) {
            View view = linearLayoutManager.findViewByPosition(first + 1);
            view.setScaleX(2);
        }

        firstview.setScaleX(sx);
        View lastview = linearLayoutManager.findViewByPosition(last);
        offseta = getHeight() - lastview.getBottom();
        sx = 1f + (float) offseta / viewHeight;
        lastview.setScaleX(sx);
    }

}
