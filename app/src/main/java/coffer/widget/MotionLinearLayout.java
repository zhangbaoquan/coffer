package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author：张宝全
 * @date：2021/2/18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class MotionLinearLayout extends LinearLayout {
    public MotionLinearLayout(Context context) {
        this(context,null);
    }

    public MotionLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MotionLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("cocc","MotionLinearLayout dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("cocc","MotionLinearLayout onInterceptTouchEvent");
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("cocc","MotionLinearLayout onTouchEvent");
        return super.onTouchEvent(event);
    }

}
