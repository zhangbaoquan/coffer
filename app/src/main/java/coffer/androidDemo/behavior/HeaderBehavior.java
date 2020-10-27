package coffer.androidDemo.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @author：张宝全
 * @date：2020/10/23
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class HeaderBehavior extends CoordinatorLayout.Behavior<View> {

    // 记录手指触摸的位置
    private int mLastY = 0;

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(@NonNull CoordinatorLayout parent, @NonNull View child,
                                @NonNull MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getRawY();
                child.setTranslationY(child.getTranslationY() + y - mLastY);
                mLastY = y;
                break;
            default:
                break;
        }
        return true;
    }
}
