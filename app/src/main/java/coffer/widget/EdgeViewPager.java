package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @author：张宝全
 * @date：2019-07-09
 * @Description： 可以拖动的ViewPager
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class EdgeViewPager extends ViewPager {

    /**
     * 记录上次的位置
     */
    private int mLastX;
    private int mLastY;

    private Scroller mScroller;

    /**
     * 系统所能识别的最小滚动距离
     */
    private int mTouchSlop;

    private Interpolator mInterpolator;

    /**
     * 是否正在拖动
     */
    private boolean mDraging;

    public EdgeViewPager(@NonNull Context context) {
        super(context);
        init(context);
    }

    public EdgeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // 1、初始化滚动状态
        setOverScrollMode(OVER_SCROLL_NEVER);
        // 2、初始化滚动条、动画加速器
        mScroller = new Scroller(context);
        mInterpolator = new DecelerateInterpolator();
        // 3、创建View配置对象，并获取最小滚动距离
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                // 在按下阶段，暂停所有的动画行为
                mScroller.abortAnimation();
                mDraging = false;
                break;
            default:
                break;
        }

        return getLeft() != 0 ? true : super.onInterceptTouchEvent(ev);
    }
}
