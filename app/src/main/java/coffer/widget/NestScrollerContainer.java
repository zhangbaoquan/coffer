package coffer.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：张宝全
 * @date：2020-03-23
 * @Description： 实现RecycleView 粘性拖拽第二版
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class NestScrollerContainer extends LinearLayout implements NestedScrollingParent2 {

    private static final String TAG = "NestScroller_tag";

    private static final int MAX_HEIGHT = 200;
    private View headerView;
    private View footerView;
    private View childView;

    private ReboundAnimator animator;
    /**
     * 针对冗余fling期间，保证回弹动画只执行一次
     */
    private boolean isFirstRunAnim;

    public NestScrollerContainer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public NestScrollerContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //这里的childView将是你xml中嵌套进的子view，我这里是RecyclerView
        childView = getChildAt(0);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, MAX_HEIGHT);
        // 将头、尾视图插入
        addView(headerView, 0, layoutParams);
        addView(footerView, getChildCount(), layoutParams);
        // 上移MAX_HEIGHT，隐藏头视图
        scrollBy(0, MAX_HEIGHT);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        headerView = new View(context);
        footerView = new View(context);
    }


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i(TAG, "onStartNestedScroll  axes : " + axes + " ,type : " + type);
        // 返回true表示处理事件
        return target instanceof RecyclerView;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        Log.i(TAG, "onNestedScrollAccepted  axes : " + axes + " ,type : " + type);
        if (animator == null) {
            // 初始化动画对象
            animator = new ReboundAnimator();
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        // 复位初始位置
        Log.i(TAG, "onStopNestedScroll type : " + type);
        isFirstRunAnim = false;
        if (getScrollY() != MAX_HEIGHT) {
            //优化代码执行效率
            animator.startOfFloat(target, getScrollY());
        }
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed, int type) {
        // 这个方法仅在目标View可以滚动时调用。
        Log.i(TAG, "onNestedScroll type : " + type + " ，dxConsumed ：" + dxConsumed + "，dyConsumed ："
                + dyConsumed+"，dxUnconsumed："+dxUnconsumed+"，dyUnconsumed："+dyUnconsumed);
        getParent().requestDisallowInterceptTouchEvent(true);
        // type == TYPE_NON_TOUCH 表示Filing，这个是NestedScrollingParent2对NestedScrollingParent的
        // 扩展优化，让RecycleView 在Filing 下更加顺滑
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            //非手指触发的滑动，即Filing
            //解决冗余fling问题
            if (((Math.floor(getScrollY()) == 0) || ((Math.ceil(getScrollY()) == 2 * MAX_HEIGHT))) && !isFirstRunAnim) {
                int startY = 0;
                if (dyUnconsumed > 0) {
                    // 表示向上Filing，将尾视图滑动出来了
                    startY = 2 * MAX_HEIGHT;
                }
                animator.startOfFloat(target, startY);
                isFirstRunAnim = true;
            }
            if (isFirstRunAnim)
                return;

            // dy>0向下fling dy<0向上fling
            boolean showTop = dyUnconsumed < 0 && !target.canScrollVertically(-1);
            boolean showBottom = dyUnconsumed > 0 && !target.canScrollVertically(1);
            if (showTop || showBottom) {
                if (animator.isStarted()) {
                    animator.pause();
                }
                scrollBy(0, damping(dyUnconsumed));
                if (animator.isPaused()) {
                    //手动cancel 避免内存泄漏
                    animator.cancel();
                }
            }
            //调整错位
            adjust(dyUnconsumed, target);
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        Log.i(TAG, "onNestedPreScroll type : " + type);
        // 如果在自定义ViewGroup之上还有父View交给我来处理
        getParent().requestDisallowInterceptTouchEvent(true);
        if (type == ViewCompat.TYPE_TOUCH) {
            Log.i(TAG, "onNestedPreScroll dy : " + dy + " , getScrollY : " + getScrollY());
            //手指触发的滑动 dy < 0向下scroll dy > 0向上scroll
            // canScrollVertically ：检测目标视图（RecycleView）是否可以在特定方向垂直滚动，负数表示向上滚
            // 当前的滚动如果是向下，并且子View已经不能向上滚动时（表示滑到顶了），表示要显示顶部，否则向下
            // 这里需要注意下，在初始化时，为了隐藏头视图，整个View已经向上滚动了MAX_HEIGHT（200），因此此时
            // 向上滚动时，getScrollY == MAX_HEIGHT（200)，在RecycleView自己的内容可以滚动时，getScrollY
            // 还是MAX_HEIGHT（200)，因为仅头视图在外面，当滚到底，即RecycleView滚上去显示尾视图时，
            // getScrollY == 头视图的高度 + 尾视图的高度
            boolean hiddenTop = dy > 0 && getScrollY() < MAX_HEIGHT && !target.canScrollVertically(-1);
            boolean showTop = dy < 0 && !target.canScrollVertically(-1);
            boolean hiddenBottom = dy < 0 && getScrollY() > MAX_HEIGHT && !target.canScrollVertically(1);
            boolean showBottom = dy > 0 && !target.canScrollVertically(1);
            if (hiddenTop || showTop || hiddenBottom || showBottom) {
                if (animator.isStarted()) {
                    animator.pause();
                }
                scrollBy(0, damping(dy));
                if (animator.isPaused()) {
                    //手动cancel 避免内存泄漏
                    animator.cancel();
                }
                // 事件交给父View消耗，consumed[1]表示父View的y轴
                consumed[1] = dy;
            }
            //调整错位
            adjust(dy, target);
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        Log.i(TAG, "onNestedFling consumed : " + consumed);
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    /**
     * 衰减可继续scroll或fling的距离
     */
    private int damping(int dy) {
        //计算衰减系数,越大可继续scroll或fling的距离越短
        int i = (int) (Math.abs(MAX_HEIGHT - getScrollY()) * 0.01);
        return i < MAX_HEIGHT * 0.01 ? dy : dy / i;
    }

    /**
     * 调整错位问题(强转精度损失造成的错位)
     */
    private void adjust(int condition1, View condition2) {
        if (condition1 > 0 && getScrollY() > MAX_HEIGHT && !condition2.canScrollVertically(-1)) {
            scrollTo(0, MAX_HEIGHT);
        }
        if (condition1 < 0 && getScrollY() < MAX_HEIGHT && !condition2.canScrollVertically(1)) {
            scrollTo(0, MAX_HEIGHT);
        }
    }

    /**
     * 限制滑动 移动y轴不能超出最大范围
     */
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        } else if (y > MAX_HEIGHT * 2) {
            y = MAX_HEIGHT * 2;
        }
        super.scrollTo(x, y);
    }

    /**
     * 回弹动画
     */
    private class ReboundAnimator extends ValueAnimator {
        private View target;

        private ReboundAnimator() {
            init();
        }

        private void init() {
            this.setInterpolator(new DecelerateInterpolator());//添加减速插值器
            this.setDuration(260);
            //添加值更新监听器
            this.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float currValue = (float) getAnimatedValue();
                    scrollTo(0, (int) currValue);
                    // 调整错位问题(强转精度损失造成的错位)
                    if (getScrollY() > MAX_HEIGHT && !target.canScrollVertically(-1)) {
                        scrollTo(0, MAX_HEIGHT);
                    }
                    if (getScrollY() < MAX_HEIGHT && !target.canScrollVertically(1)) {
                        scrollTo(0, MAX_HEIGHT);
                    }
                }
            });
        }

        private void startOfFloat(View target, float startY) {
            this.setFloatValues(startY, MAX_HEIGHT);
            this.target = target;
            this.start();
        }
    }
}
