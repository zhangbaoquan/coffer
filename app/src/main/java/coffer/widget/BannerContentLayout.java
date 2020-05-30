package coffer.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.util.Util;


/**
 * Created by wangqingfeng on 2019/9/3.
 */
public class BannerContentLayout extends FrameLayout {

    private static final String TAG = "BannerContentLayout_TAG";

    private static final long ANIMATION_DURATION = 350;   //放手之后banner归位动画时长
    private static final int BANNER_TIME = 3000;          //轮播时间间隔
    private static final int MSG_SCROLL = 1;

    private int mIndicatorDefaultColor;      //indicator 默认色
    private int mIndicatorSelectedColor;     //indicator 选中色
    private int mCurIndicatorColor;          //当前位置的indicator颜色
    private int mNextIndicatorColor;
    private int mPreIndicatorColor;

    private int mIndicatorRadius;           //indicator 半径
    private float mSelectedIndicatorRadius;   //选中的indicator 半径
    private float mCurIndicatorRadius;
    private float mNextIndicatorRadius;
    private float mPreIndicatorRadius;

    private int mIndicatorPadding;           //indicator 之间 padding
    private int mIndicatorBottom;            //indicator 距离底部bottom
    private int mIndicatorCircleY;           //indicator 绘制位置y
    private Paint mIndicatorPaint;

    private long mAnimationDuration;         //放手之后banner归位动画时长
    private int mBannerDuration;             //轮播时间间隔
    private int mCurPosition;                //当前位置
    private int mNextPosition;               //下一个view的位置
    private int mPrePosition;                //上一个view的位置
    private int mItemCount;
    private ArrayList<View> mItemViews;

    private int mTouchSlop;
    private int mScrollX;                    //滚动距离
    private boolean mRunning;
    private boolean mCanPlay;
    private ValueAnimator mAnimator;
    private boolean mVisible;
    private boolean mUserPresent = true;
    private BannerHandler mHandler;
    private boolean mReceiverRegister = false;

    private int mBannerChangePosition = -1;      //记录回调的位置
    private OnBannerChangedListener mBannerChangedListener;

    public BannerContentLayout(Context context) {
        this(context, null);
    }

    public BannerContentLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerContentLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mIndicatorDefaultColor = context.getResources().getColor(R.color.banner_indicator_default);
        mIndicatorSelectedColor = context.getResources().getColor(R.color.banner_indicator_selected);
        mIndicatorRadius = Util.dipToPixel(context.getResources(), 3);
        mSelectedIndicatorRadius = mIndicatorRadius;
        mIndicatorPadding = Util.dipToPixel(context.getResources(), 4);
        mIndicatorBottom = Util.dipToPixel(context, 4);

        mIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnimationDuration = ANIMATION_DURATION;
        mBannerDuration = BANNER_TIME;

        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(mAnimationDuration);
        mAnimator.setInterpolator(new DecelerateInterpolator());
        mHandler = new BannerHandler(this);
        mItemViews = new ArrayList<>();
    }

    private void handleScroll() {
        if (mScrollX == 0) {
            mScrollX = -1;
            updateViewPosition(mScrollX);
            animToPosition();
        }
    }

    private void preDraw() {
        mPrePosition = mCurPosition - 1 < 0 ? mItemViews.size() - 1 : mCurPosition - 1;
        mNextPosition = mCurPosition + 1 > mItemViews.size() - 1 ? 0 : mCurPosition + 1;
        updateViewPosition(mScrollX);
        updateIndicator(mScrollX);
        invalidate();
    }

    private void updateViewPosition(int scrollX) {
        if (mCurPosition >= 0 && mCurPosition < mItemViews.size()) {
            View curView = mItemViews.get(mCurPosition);
            if (curView != null) {
                curView.layout(scrollX, getPaddingTop(), curView.getMeasuredWidth() + scrollX, getHeight() - getPaddingBottom());
                curView.invalidate();

                if (scrollX > 0 && mPrePosition >= 0 && mPrePosition < mItemViews.size()) {
                    View preView = mItemViews.get(mPrePosition);
                    if (preView != null) {
                        preView.layout(curView.getLeft() - preView.getMeasuredWidth(),
                                curView.getTop(), curView.getLeft(), curView.getBottom());
                        preView.invalidate();
                    }
                }

                if (scrollX < 0 && mNextPosition >= 0 && mNextPosition < mItemViews.size()) {
                    View nextView = mItemViews.get(mNextPosition);
                    if (nextView != null) {
                        nextView.layout(curView.getRight(), curView.getTop(),
                                nextView.getLeft() + nextView.getMeasuredWidth(), curView.getBottom());
                        nextView.invalidate();
                    }
                }
            }
            if (mBannerChangedListener != null) {
                float positionOffset = Math.abs(scrollX * 1f / getWidth());
                int nextPosition = scrollX > 0 ? mCurPosition - 1 : mCurPosition + 1;
                if (nextPosition >= mItemViews.size()) {
                    nextPosition = 0;
                } else if (nextPosition < 0) {
                    nextPosition = mItemViews.size() - 1;
                }
                mBannerChangedListener.onBannerScrolled(mCurPosition, nextPosition, positionOffset);
            }
        }
    }

    private void updateIndicator(int scrollX) {
        int absScrollX = Math.abs(scrollX);
        float fraction = absScrollX * 1f / getWidth();
        if (fraction == 0) {
            //半径
            mCurIndicatorRadius = mSelectedIndicatorRadius;
            mPreIndicatorRadius = mIndicatorRadius;
            mNextIndicatorRadius = mIndicatorRadius;

            //颜色
            mCurIndicatorColor = mIndicatorSelectedColor;
            mPreIndicatorColor = mIndicatorDefaultColor;
            mNextIndicatorColor = mIndicatorDefaultColor;
        } else {
            float radiusChangedSize = (mSelectedIndicatorRadius - mIndicatorRadius) * fraction;
            if (scrollX > 0) {
                mPreIndicatorRadius = mIndicatorRadius + radiusChangedSize;
                mCurIndicatorRadius = mSelectedIndicatorRadius - radiusChangedSize;
                mNextIndicatorRadius = mIndicatorRadius;

                mPreIndicatorColor = getColor(fraction, mIndicatorDefaultColor, mIndicatorSelectedColor);
                mCurIndicatorColor = getColor(fraction, mIndicatorSelectedColor, mIndicatorDefaultColor);
                mNextIndicatorColor = mIndicatorDefaultColor;
            } else if (scrollX < 0) {
                mPreIndicatorRadius = mIndicatorRadius;
                mCurIndicatorRadius = mSelectedIndicatorRadius - radiusChangedSize;
                mNextIndicatorRadius = mIndicatorRadius + radiusChangedSize;

                mPreIndicatorColor = mIndicatorDefaultColor;
                mCurIndicatorColor = getColor(fraction, mIndicatorSelectedColor, mIndicatorDefaultColor);
                mNextIndicatorColor = getColor(fraction, mIndicatorDefaultColor, mIndicatorSelectedColor);
            }
        }
    }

    private View getCurView() {
        if (mCurPosition >= 0 && mCurPosition < mItemViews.size()) {
            return mItemViews.get(mCurPosition);
        }
        return null;
    }

    private View getPreView() {
        if (mPrePosition >= 0 && mPrePosition < mItemViews.size()) {
            return mItemViews.get(mPrePosition);
        }
        return null;
    }

    private View getNextView() {
        if (mNextPosition >= 0 && mNextPosition < mItemViews.size()) {
            return mItemViews.get(mNextPosition);
        }
        return null;
    }

    /**
     * 放手后滑动到期望位置
     */
    private void animToPosition() {
        View curView = getCurView();
        if (curView != null && curView.getLeft() != 0) {
            final int deltaX;
            final int originalX = mScrollX;
            if (curView.getLeft() > 0) {
                View preView = getPreView();
                deltaX = preView == null ? 0 : -preView.getLeft();
            } else {
                View nextView = getNextView();
                deltaX = nextView == null ? 0 : -nextView.getLeft();
            }
            mAnimator.removeAllUpdateListeners();
            mAnimator.removeAllListeners();
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatorValue = (float) animation.getAnimatedValue();
                    mScrollX = (int) (originalX + animatorValue * deltaX);
                    preDraw();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (deltaX < 0) {
                        mCurPosition = mNextPosition;
                    } else {
                        mCurPosition = mPrePosition;
                    }
                    callBannerSelected(mCurPosition);
                    mScrollX = 0;
                    preDraw();
                }
            });
            mAnimator.start();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!mReceiverRegister) {
            final IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            try {
                getContext().registerReceiver(mReceiver, filter);
                mReceiverRegister = true;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        mVisible = getVisibility() == VISIBLE;
        updateRunning();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mReceiverRegister) {
            try {
                getContext().unregisterReceiver(mReceiver);
                mReceiverRegister = false;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        updateRunning();
//        for (int i = 0; i < mItemViews.size(); i++) {
//            if (mBannerAnimators.get(i) != null && mBannerAnimators.get(i).isRunning()) {
//                updatePaint(1.0f, i);
//            }
//        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        preDraw();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = mItemViews.size();
        View child;
        for (int i = 0; i < childCount; i++) {
            child = mItemViews.get(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWidthMeasureSpec;
            if (lp.width == LayoutParams.MATCH_PARENT) {
                final int width = Math.max(0, getMeasuredWidth()
                        - getPaddingLeft() - getPaddingRight()
                        - lp.leftMargin - lp.rightMargin);
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                        width, MeasureSpec.EXACTLY);
            } else {
                childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                        getPaddingLeft() + getPaddingRight() +
                                lp.leftMargin + lp.rightMargin,
                        lp.width);
            }

            final int childHeightMeasureSpec;
            if (lp.height == LayoutParams.MATCH_PARENT) {
                final int height = Math.max(0, getMeasuredHeight()
                        - getPaddingTop() - getPaddingBottom()
                        - lp.topMargin - lp.bottomMargin);
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                        height, MeasureSpec.EXACTLY);
            } else {
                childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                        getPaddingTop() + getPaddingBottom() +
                                lp.topMargin + lp.bottomMargin,
                        lp.height);
            }
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
        preDraw();
        mIndicatorCircleY = getHeight() - mIndicatorBottom;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mCanPlay) {
            drawIndicator(canvas);
        }
    }

    private void drawIndicator(Canvas canvas) {
        int itemCount = mItemViews.size();
        int indicatorRectWidth = itemCount * mIndicatorRadius * 2 + (itemCount - 1) * mIndicatorPadding;
        int startIndicator = getWidth() / 2 - indicatorRectWidth / 2;
        for (int i = 0; i < itemCount; i++) {
            int tempColor = i == mCurPosition ? mCurIndicatorColor :
                    (i == mNextPosition ? mNextIndicatorColor :
                            (i == mPrePosition ? mPreIndicatorColor : mIndicatorDefaultColor));
            mIndicatorPaint.setColor(tempColor);
            float radius = (i == mCurPosition ? mCurIndicatorRadius :
                    (i == mNextPosition ? mNextIndicatorRadius : (i == mPrePosition ? mPreIndicatorRadius : mIndicatorRadius)));
            canvas.drawCircle(startIndicator + mIndicatorRadius + i * (mIndicatorPadding + 2 * mIndicatorRadius),
                    mIndicatorCircleY, radius, mIndicatorPaint);
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        mItemViews.add(child);
        mItemCount = mItemViews.size();
        if (mItemCount > 1) {
            mCanPlay = true;
        }
        requestLayout();
        updateRunning();
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        mItemViews.clear();
        mCurPosition = 0;
        mPrePosition = 0;
        mNextPosition = 0;
    }

    @SuppressLint("NewApi")
    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        mVisible = screenState == SCREEN_STATE_ON;
        updateRunning();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        mVisible = visibility == View.VISIBLE;
        updateRunning();
    }

    private Point mDownPoint = new Point();
    private Point mMovePoint = new Point();
    private boolean mIsDragging;
    private int mLastX;
    private int mLastActionX;
    private int mLastActionY;
    private boolean mHandleEvent = true;
    private boolean mHasComputeHandleEvent;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (mCanPlay) {
//            PluginRely.enableGesture(false);
        }
        //动画过程中禁止点击
        if (mAnimator != null && mAnimator.isRunning()) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHasComputeHandleEvent = false;
                mDownPoint.set(x, y);
                mIsDragging = false;
                mScrollX = 0;
                mLastX = x;
                mLastActionX = x;
                mLastActionY = y;
                if (mCanPlay) {
                    mHandler.removeMessages(MSG_SCROLL);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mMovePoint.set(x, y);
                //判断是滑动还是点击
                if (calculateA2B(mDownPoint, mMovePoint) >= mTouchSlop) {
                    mIsDragging = true;
                }
                if (mIsDragging && !mHasComputeHandleEvent) {
                    mHandleEvent = Math.abs(x - mLastActionX) > Math.abs(y - mLastActionY);
                    mHasComputeHandleEvent = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mCanPlay && !mHandler.hasMessages(MSG_SCROLL)) {
                    mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mBannerDuration);
                }
                break;
            default:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCanPlay) {
//            PluginRely.enableGesture(false);
        }
        //动画过程中禁止点击
        if (mAnimator != null && mAnimator.isRunning()) {
            return false;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownPoint.set(x, y);
                mIsDragging = false;
                mScrollX = 0;
                mLastX = x;
                mLastActionX = x;
                mLastActionY = y;
                if (mCanPlay) {
                    mHandler.removeMessages(MSG_SCROLL);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                mMovePoint.set(x, y);
                //判断是滑动还是点击
                if (calculateA2B(mDownPoint, mMovePoint) >= mTouchSlop) {
                    mIsDragging = true;
                }
                if (mIsDragging && !mHasComputeHandleEvent) {
                    mHandleEvent = Math.abs(x - mLastActionX) > Math.abs(y - mLastActionY);
                    mHasComputeHandleEvent = true;
                }
                if (mCanPlay) {
                    getParent().requestDisallowInterceptTouchEvent(mHandleEvent);
                }
                mLastActionX = x;
                mLastActionY = y;
                if (mIsDragging && mCanPlay && mHandleEvent) {
                    mScrollX = dx;
                    preDraw();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mHasComputeHandleEvent = false;
                if (mIsDragging && mScrollX != 0) {
                    animToPosition();
                }
                if (mCanPlay && !mHandler.hasMessages(MSG_SCROLL)) {
                    mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mBannerDuration);
                }
                if (mIsDragging) {
                    return true;
                }
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
                mIsDragging = false;
                mLastX = 0;
                mLastActionX = 0;
                mLastActionY = 0;
                mHasComputeHandleEvent = false;
                if (mCanPlay) {
//                    PluginRely.enableGesture(true);
                    if (mHandler != null && !mHandler.hasMessages(MSG_SCROLL)) {
                        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mBannerDuration);
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void updateRunning() {
        if (mBannerChangePosition != mCurPosition) {
            callBannerSelected(mCurPosition);
        }
        boolean running = mVisible && mUserPresent && mCanPlay;
        if (running != mRunning) {
            mRunning = running;
            mHandler.removeMessages(MSG_SCROLL);
            if (running) {
                mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mBannerDuration);
            }
        }
    }

    private void callBannerSelected(int position) {
        if (mBannerChangedListener != null) {
            mBannerChangePosition = position;
            mBannerChangedListener.onBannerSelected(position);
        }
    }

    private int getColor(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }

    private int calculateA2B(Point startPoint, Point endPoint) {
        int xDist = startPoint.x - endPoint.x;
        int yDist = startPoint.y - endPoint.y;
        return (int) Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public void setOnBannerChangedListener(OnBannerChangedListener listener) {
        mBannerChangedListener = listener;
    }

    private static class BannerHandler extends Handler {
        private WeakReference<BannerContentLayout> mWeakBannerLayout;
        private Rect mRect;

        BannerHandler(BannerContentLayout bannerContentLayout) {
            this.mWeakBannerLayout = new WeakReference<>(bannerContentLayout);
            mRect = new Rect();
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_SCROLL
                    && mWeakBannerLayout != null) {
                BannerContentLayout layout = mWeakBannerLayout.get();
                if (layout != null && layout.getParent() != null && layout.mRunning) {
                    if (layout.getLocalVisibleRect(mRect)
                            && mRect.left == layout.getLeft()
                            && mRect.right == layout.getRight()) {
                        layout.handleScroll();
                    }
                    sendEmptyMessageDelayed(MSG_SCROLL, layout.mBannerDuration);
                }
            }
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //PluginLogUtils.i("== onReceive action:"+action+" mUserPresent:"+mUserPresent);
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mUserPresent = false;
                updateRunning();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mUserPresent = true;
                updateRunning();
            }
        }
    };

    public interface OnBannerChangedListener {
        /**
         * @param position       当前位置
         * @param nextPosition   下一个 要到达的位置
         * @param positionOffset 偏移百分比
         */
        void onBannerScrolled(int position, int nextPosition, float positionOffset);

        void onBannerSelected(int position);
    }
}
