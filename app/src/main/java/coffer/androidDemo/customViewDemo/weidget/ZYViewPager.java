package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

import coffer.androidjatpack.R;
import coffer.util.APP;
import coffer.util.CofferLog;

/**
 * @作者：Super
 * @创建时间 2013 2013-12-5 上午11:08:54
 */
public class ZYViewPager extends ViewPager {

    private static final String TAG = "ZYViewPager_tag";

    private ZYViewPagerScroller mZYScroller;
    private boolean isIntercept;            //是否拦截掉手势，false标示不拦截 默认为true
    /**
     * 监听的触摸x坐标
     */
    protected float mLastMotionX;
    private boolean mCanScroll = true;

    public ZYViewPager(Context context) {
        super(context);
        initScroller(context);
    }

    public ZYViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScroller(context);
    }

    private void initScroller(Context context) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mZYScroller = new ZYViewPagerScroller(context, AnimationUtils.loadInterpolator(context, R.anim.interpolator_decelerate));
            mField.set(this, mZYScroller);
            mZYScroller.setZYDuration(400);
        } catch (Exception e) {

        }
        isIntercept = true;
    }

    public void setCanScroll(boolean canScroll) {
        mCanScroll = canScroll;
    }

    public void setIntercept(boolean isFlag) {
        isIntercept = isFlag;
    }

    public void setScrollIndex(int currentTab) {

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!mCanScroll) {
            return false;
        }
        if (!isIntercept) {
            return super.onInterceptTouchEvent(event);
        }
		//默认事件不拦截
        boolean enableLeft = false;
        boolean enableRight = false;
        float eventFloatX = event.getX();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mLastMotionX = eventFloatX;
        } else if (action == MotionEvent.ACTION_MOVE) {
            enableLeft = !canScrollHorizontally(1) && (eventFloatX - mLastMotionX < 0);
            enableRight = !canScrollHorizontally(-1) && (eventFloatX - mLastMotionX > 0);
            CofferLog.I(TAG, "enableLeft 1 : " + enableLeft);
            CofferLog.I(TAG, "enableRight 1 : " + enableRight);
        }
        CofferLog.I(TAG, "super.onInterceptTouchEvent(event) : " + super.onInterceptTouchEvent(event));
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mCanScroll) {
            return false;
        }
        if (!isIntercept) {
            return super.onTouchEvent(event);
        }
		//默认事件不拦截
        boolean enableLeft = false;
        boolean enableRight = false;
        float eventFloatX = event.getX();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            APP.setEnableScrollToRight(false);
        } else if (action == MotionEvent.ACTION_MOVE) {
            enableLeft = !canScrollHorizontally(1) && (eventFloatX - mLastMotionX < 0);
            enableRight = !canScrollHorizontally(-1) && (eventFloatX - mLastMotionX > 0);
            CofferLog.I(TAG, "enableLeft 2 : " + enableLeft);
            CofferLog.I(TAG, "enableRight 2 : " + enableRight);
            APP.setEnableScrollToRight(enableRight);
        }
        mLastMotionX = eventFloatX;
        return super.onTouchEvent(event);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

}
