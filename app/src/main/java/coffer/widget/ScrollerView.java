package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

/**
 * @author：张宝全
 * @date：2020-03-14
 * @Description： 可以滑动的View，使用Scroller来实现
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ScrollerView extends AppCompatButton {

    /**
     * 滑动对象
     */
    private Scroller mScroller;
    private int mLastX;
    private int mLastY;
    private int mx;
    private int my;

    public ScrollerView(Context context) {
        super(context);
        init(context);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mScroller = new Scroller(context);
    }

    /**
     * 缓慢的滑动到指定的位置
     * @param destX 滑动终点x坐标
     * @param destY 滑动终点y坐标
     * @param duration 滑动持续时间
     */
    public void smoothScrollTo(int destX,int destY,int duration){
        // 滑动起点x、y坐标
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        // 滑动的x、y距离
        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;
        mScroller.startScroll(scrollX,scrollY,deltaX,deltaY,duration);
        // 使用invalidate方法触发View重新绘制，从而触发computeScroll方法
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mx = getLeft();
                my = getTop();
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltax = x - mLastX;
                int deltay = y - mLastY;
                // 设置滑动边界
//                if (x > 800 || y > 800){
//                    return false;
//                }
                ((View)getParent()).scrollBy(-deltax,-deltay);
                break;
            case MotionEvent.ACTION_UP:
                smoothScrollTo(mx,my,1500);
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        // 根据源码，时间流逝的百分比来计算scrollX和Y，改变的百分比值和，这个过程相当于动画的插值器的概念
        if (mScroller.computeScrollOffset()){
            // scrollTo方法滑动的是内容，因此要调用当前View的父View的scrollTo方法才行。子View相对父View是内容。
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            // 二次重新绘制
            postInvalidate();
        }
    }


}
