package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

/**
 * @author：张宝全
 * @date：2021/2/18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class MotionButtion extends Button {
    public MotionButtion(Context context) {
        this(context,null);
    }

    public MotionButtion(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MotionButtion(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
