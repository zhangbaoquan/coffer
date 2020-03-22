package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * @author：张宝全
 * @date：2020-02-05
 * @Description： 实现左右滑动，切换不同的界面
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class HorizontalView extends ViewGroup {

    private static final String TAG = "HorizontalView_tag";

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;

    /**
     * 记录上次滑动的坐标
     */
    private int mLastX;
    private int mLastY;

    /**
     * 记录上次滑动的坐标，用在是否拦截事件的方法上
     */
    private int mLastInterceptX;
    private int mLastInterceptY;

    private int mChildrenCount;
    private int mChildIndex;
    /**
     * 记录View的宽度
     */
    private int mTouchSlop;

    public HorizontalView(Context context) {
        super(context);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mVelocityTracker.computeCurrentVelocity(1000);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int detlax = x - mLastInterceptX;
                int deltay = y - mLastInterceptY;
                if (Math.abs(detlax) > Math.abs(deltay)) {
                    // 水平移动的距离大于纵向，拦截事件
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        mLastInterceptX = x;
        mLastInterceptY = y;
        return isIntercept;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mChildrenCount = getChildCount();
        // 如果没有子View，则宽高都为0
        if (mChildrenCount == 0) {
            setMeasuredDimension(0, 0);
        }
        // 如果宽、高都是AT_MOST（即Wrap_Content）,则宽度为所有子View的宽度之和，高度为第一个View的高度
        else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View child = getChildAt(0);
            int width = child.getMeasuredWidth() * mChildrenCount;
            int height = child.getMeasuredHeight();
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        mChildrenCount = getChildCount();
        for (int i = 0; i < mChildrenCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                int viewWidth = child.getMeasuredWidth();
                child.layout(childLeft, 0, childLeft + viewWidth, child.getMeasuredHeight());
                childLeft += viewWidth;
            }
        }
    }

    private void smoothScrllorBy(int deltax,int deltay){
        mScroller.startScroll(getScrollX(),0,deltax,deltay,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    int mStartX;
    int mEndX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                mLastX = x;
                mStartX = getScrollX();
                Log.i(TAG,"down mLastX : "+mLastX);
                Log.i(TAG,"down mStartX : "+mStartX);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                int deltax = x - mLastX;
                if (getScrollX()< 0){
                    deltax = 0;
                }
                if (getScrollX() > getMeasuredWidth()){
                    deltax = 0;
                }
                scrollBy(-deltax,0);
                break;
            case MotionEvent.ACTION_UP:
                mEndX = getScrollX();
                Log.i(TAG,"down mLastX : "+mLastX);
                Log.i(TAG,"down mStartX : "+mEndX);
                int dx = mEndX - mStartX;
                if (dx > 0){
                    if (dx < 1080 / 3){
                        smoothScrllorBy(-dx,getScrollY());
                    }else {
                        smoothScrllorBy(1080-dx,getScrollY());
                    }
                }else {
                    if (-dx < 1080 / 3){
                        smoothScrllorBy(-dx,getScrollY());
                    }else {
                        smoothScrllorBy(-1080-dx,getScrollY());
                    }
                }
                break;
            default:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        // 当Activity destroy时
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();

    }
}
