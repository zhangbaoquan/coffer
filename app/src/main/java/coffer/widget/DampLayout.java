package coffer.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import coffer.androidjatpack.R;
import coffer.androidDemo.customViewDemo.recycleView.SuperRecycleView;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/5/12
 * @Description： 左右弹性阻尼的View
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class DampLayout extends FrameLayout {
    private static final String TAG = "DampLayout_tag";

    private static final int LEFT = 1;
    private static final int RIGHT = 1 << 1;

    private SuperRecycleView mSuperRecycleView;
    /**
     * 当前默认的滑动方向 RIGHT = 1 << 1 == 2,
     * LEFT | RIGHT == 3
     */
    private int mScrollOrigin = LEFT | RIGHT;

    /**
     * 弹性系数，值越小，说明越难滑动
     */
    private static final float RATIO = 0.3f;

    /**
     * 横向滑动的距离
     */
    private int mTranslationX;

    /**
     * 记录当前按下的坐标点
     */
    private Point mPoint;

    /**
     * 最大滑动距离
     */
    private int mMaxScroll;

    /**
     * 最小识别滑动距离
     */
    private int mTouchSlop;

    /**
     * 最右边隐藏的文案
     */
    private TextView mTip;
    /**
     * 最右边箭头的
     */
    private FrameLayout mTipArrowContainer;

    private View mContentView;

    private Listener mListener;


    public DampLayout(@NonNull Context context) {
        super(context);
        init(context);

    }

    public DampLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setClipToPadding(false);
        setClickable(true);
        mPoint = new Point();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaxScroll = Util.dipToPixel(context, 80);

        // 添加一个竖排TextView
        mTip = new TextView(context);
        mTip.setText("查看更多");
        mTip.setTextColor(Color.BLACK);
        mTip.setGravity(Gravity.CENTER_VERTICAL);
        mTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
        mTip.setTranslationX(Util.dipToPixel(context, -20));
        LayoutParams flpTip = new LayoutParams(Util.dipToPixel(context, 13),
                Util.dipToPixel(context, 80));
        addView(mTip, flpTip);

        // 添加一个箭头的容器
        mTipArrowContainer = new FrameLayout(context);
        LayoutParams layoutParams = new LayoutParams(Util.dipToPixel(context, 8),
                Util.dipToPixel(context, 80));
        layoutParams.gravity = Gravity.RIGHT;
        layoutParams.rightMargin = Util.dipToPixel(context, 6);
        addView(mTipArrowContainer, layoutParams);
        // 在容器中加入箭头view
        ImageView img = new ImageView(context);
        img.setImageResource(R.drawable.header_arrow);
        LayoutParams imgLp = new LayoutParams(Util.dipToPixel(context, 8),
                Util.dipToPixel(context, 30));
        imgLp.gravity = Gravity.CENTER_VERTICAL;
        mTipArrowContainer.addView(img);
        // 给箭头设置点击事件
        mTipArrowContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.click();
                }
            }
        });
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        if (enableLeftScroll() || enableRightScroll()) {
            return true;
        } else {
            return super.canScrollHorizontally(direction);
        }
    }

    /**
     * 是否可以左滑
     *
     * @return true 可以
     */
    private boolean enableLeftScroll() {
        // 3 & 1 == 1
        return (mScrollOrigin & LEFT) == LEFT;
    }

    /**
     * 是否可以右滑
     *
     * @return true 可以
     */
    private boolean enableRightScroll() {
        // 3 & 2 == 2;
        return (mScrollOrigin & RIGHT) == RIGHT;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTranslationX = getMeasuredWidth();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mContentView == null || getChildCount() == 0 || mScrollOrigin == 0) {
            return super.dispatchTouchEvent(ev);
        }
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setRecycleViewScrollStatus(false);
                mPoint.set(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                int distance = calculationA2B(mPoint,new Point(x,y));
                // 当前是滑动事件
                if (distance > mTouchSlop){
                    int moveX = mPoint.x - x;
                    int scrollX = (int) (moveX * RATIO);
                    if (moveX > 0 && enableLeftScroll()){
                        // 左滑 ,滑动值取负
                        if (scrollX > mMaxScroll){
                            scrollX = mMaxScroll;
                        }
                        mTipArrowContainer.setTranslationX(-scrollX);
                        mTip.setTranslationX(mTranslationX-scrollX);
                        mContentView.setTranslationX(-scrollX);
                        setRecycleViewScrollStatus(false);
                        return true;
                    }else if (moveX < 0 && enableRightScroll()){
                        // 右滑 ,滑动值取正
                        if (scrollX < -mMaxScroll){
                            scrollX = -mMaxScroll;
                        }
                        mTipArrowContainer.setTranslationX(-scrollX);
                        mTip.setTranslationX(mTranslationX-scrollX);
                        mContentView.setTranslationX(-scrollX);
                        setRecycleViewScrollStatus(false);
                        return true;
                    }
                }else {
                    setRecycleViewScrollStatus(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = (int) mContentView.getTranslationX();
                if (scrollX != 0){
                    // 这里选择用属性动画而没有用前面提到的setTranslationX，
                    // 主要是这里需要指明动画的起始位置和终点位置，当然也可以使用scrollBy、scrollTo 组合，
                    // 因为这些都只是滑动View视觉，仅属性动画滑动的是View本身
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mContentView,
                            "translationX",scrollX,0);
                    objectAnimator.setDuration(200);
                    objectAnimator.start();
                    ObjectAnimator.ofFloat(mTipArrowContainer,
                            "translationX",scrollX,0).setDuration(200).start();
                    ObjectAnimator.ofFloat(mTip,"translationX"
                            ,mTip.getTranslationX(),mTranslationX).setDuration(200).start();
                    final boolean show = mTip.getTranslationX() < mTranslationX - mTip.getMeasuredWidth();
                    objectAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (show && mListener != null){
                                mListener.click();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                }
                setRecycleViewScrollStatus(true);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 计算两个点之间的距离，利用勾股定理
     *
     * @return 距离
     */
    private int calculationA2B(Point start, Point end) {
        int deltaX = start.x - end.x;
        int deltaY = start.y - end.y;
        return (int) Math.sqrt(deltaX * deltaX - deltaY * deltaY);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child != null && child != mTip && child != mTipArrowContainer) {
            mContentView = child;
        }
    }

    public void setSuperRecycleView(SuperRecycleView recycleView){
        this.mSuperRecycleView = recycleView;
    }

    private void setRecycleViewScrollStatus(boolean status){
        if (mSuperRecycleView != null){
            mSuperRecycleView.setCanScrollVertically(status);
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public interface Listener {
        void click();
    }

    /**
     * 设置View的滑动状态
     *
     * @param status false: 禁止滑动
     */
    public void setEnableScroll(boolean status) {
        if (!status) {
            mScrollOrigin = 0;
        } else {
            mScrollOrigin = LEFT | RIGHT;
        }
    }
}
