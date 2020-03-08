package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author：张宝全
 * @date：2020-02-05
 * @Description： 实现左右滑动，切换不同的界面
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class HorizontalView extends ViewGroup {

    public HorizontalView(Context context) {
        super(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        // 如果没有子View，则宽高都为0
        if (getChildCount() == 0){
            setMeasuredDimension(0,0);
        }
        // 如果宽、高都是AT_MOST（即Wrap_Content）,则宽度为所有子View的宽度之和，高度为第一个View的高度
        else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
            View child = getChildAt(0);
            int width = child.getMeasuredWidth() * getChildCount();
            int height = child.getMeasuredHeight();
            setMeasuredDimension(width,height);
        }
        else if (widthMode == MeasureSpec.AT_MOST){

        }


        //
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
