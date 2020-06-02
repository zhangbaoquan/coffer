package coffer.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;
import coffer.util.CONSTANT;
import coffer.util.CofferLog;

import static coffer.util.CONSTANT.COFFER_TAG;

/**
 * @author：张宝全
 * @date：2020/6/2
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class TextBannerView extends View {

    private static final long PLAY_DURATION = 500;

    private static final int PAUSE_TIME = 3000;

    private int    verticalOffset;

    /**
     * 可自定义属性： 上下轮播动画时长
     */
    private int     mPlayDuration;

    /**
     * 可自定义属性： 上下轮播暂停时间间隔
     */
    private int     mPauseDuration;

    /**
     * 可兹定于实用性： 文字color
     */
    private int     mTextColor;

    private float     mTextSizeDp;


    /**
     * 文字位置竖直方向偏移量
     */
    private float  vOffsetNext;

    private float  vOffsetCurr;

    private int    mCurrPosition;

    private int    mNextPosition;

    private Paint mPaint;

    private Paint.FontMetrics mPaintFontMetrics;

    private ArrayList<String> mBannerStrings;

    private ValueAnimator mAnimator;
    private boolean mRunning = false;
    private boolean mVisible = false;
    private boolean mUserPresent = true;
    private boolean mStop = false;
    private boolean mReceiverRegister = false;
    private boolean mIsAbove16 = false;
    private final int MSG_SCROLL = 1;
    private Handler mHandler;

    private float baseLineY;

    public TextBannerView(Context context) {
        this(context, null);
    }

    public TextBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取相应属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBannerView);
        mPlayDuration = typedArray.getInt(R.styleable.TextBannerView_play_duration, (int) PLAY_DURATION);
        mPauseDuration = typedArray.getInt(R.styleable.TextBannerView_pause_duration, PAUSE_TIME);
        mTextColor = typedArray.getColor(R.styleable.TextBannerView_banner_text_color, getResources().getColor(R.color.item_h1_text_color));
        mTextSizeDp = getResources().getDimensionPixelSize(R.dimen.ad_text_size);
        typedArray.recycle();

        /***************** 初始化其它变量 **************/
        init(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {

        mBannerStrings = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setTextSize(mTextSizeDp);
        mPaint.setColor(mTextColor);
        mPaintFontMetrics = mPaint.getFontMetrics();

        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(mPlayDuration);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mIsAbove16 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MSG_SCROLL) {
                    if (mRunning) {
                        animVerticalOffset();
                        sendEmptyMessageDelayed(MSG_SCROLL, mPauseDuration);
                    }
                }
            }

        };

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mBannerStrings == null || mBannerStrings.size() == 0 ) {
            setMeasuredDimension(0, 0);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        breakText();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        baseLineY = (int) (getHeight()/2 - mPaintFontMetrics.ascent/2 - mPaintFontMetrics.descent/2);

        verticalOffset = (int) (getHeight() - baseLineY +  (mPaintFontMetrics.bottom - mPaintFontMetrics.top));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mBannerStrings != null && mBannerStrings.size() > 0) {
            if (mCurrPosition < mBannerStrings.size()) {
                canvas.drawText(mBannerStrings.get(mCurrPosition), 0, baseLineY + vOffsetCurr, mPaint);
            }
            if (mNextPosition < mBannerStrings.size()) {
                canvas.drawText(mBannerStrings.get(mNextPosition), 0, baseLineY + vOffsetCurr - verticalOffset, mPaint);
            }

        }
    }


    /**
     * onDraw()前的计算
     */
    private void preDraw() {
        int count = mBannerStrings.size();
        mNextPosition = mCurrPosition + 1 > count - 1 ? 0 : mCurrPosition + 1;
        vOffsetNext = vOffsetCurr - verticalOffset;
        invalidateView();
    }


    public void setStop(boolean stop){
        mStop  = stop;
        updateRunning();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mPaint.setColor(mTextColor);
    }

    private void updateRunning() {
        boolean running = mVisible && mUserPresent && !mStop;
        if (running != mRunning) {
            mRunning = running;
            if (running) {
                mHandler.removeMessages(MSG_SCROLL);
                mHandler.sendEmptyMessageDelayed(MSG_SCROLL, mPauseDuration);
            } else {
                mHandler.removeMessages(MSG_SCROLL);
            }
        }
    }

    /**
     * 放手后滑动到期望位置
     */
    private void animVerticalOffset() {
        if (mBannerStrings.size() > 1) {
            mAnimator.removeAllUpdateListeners();
            mAnimator.removeAllListeners();
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatorValue = (float) animation.getAnimatedValue();
                    vOffsetCurr = animatorValue * verticalOffset;
                    preDraw();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mCurrPosition = mNextPosition;
                    vOffsetCurr = 0;
                    preDraw();

                }
            });
            mAnimator.start();
        }
    }



    /**
     * 设置轮播文字
     *
     * @param strBanners
     */
    public void setBannerString(List<String> strBanners) {
        mBannerStrings.clear();
        if (strBanners == null) {
            return;
        }
        mBannerStrings.addAll(strBanners);
        breakText();
    }

    private void breakText() {
        if (mBannerStrings == null || mBannerStrings.size() == 0 || this.getMeasuredWidth() == 0) {
            return;
        }

        String tmp;
        for (int i = 0; i < mBannerStrings.size(); i++) {
            tmp = mBannerStrings.get(i);
            if (!TextUtils.isEmpty(tmp)) {
                int end = mPaint.breakText(tmp, 0, tmp.length(), true, this.getMeasuredWidth() - mPaint.measureText("..."), null);
                mBannerStrings.remove(i);
                mBannerStrings.add(i, tmp.substring(0, end) + (end < tmp.length() ? "..." : ""));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //动画过程中禁止点击
        if (mAnimator != null && mAnimator.isRunning()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mClickListener != null) {
                    mClickListener.onClick(mCurrPosition);
                }
                break;
        }
        return true;
    }

    private OnBannerClickListener mClickListener;

    public interface OnBannerClickListener {
        void onClick(int position);
    }

    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        mClickListener = clickListener;
    }


    @SuppressLint("NewApi")
    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        CofferLog.D(COFFER_TAG,"onScreenStateChanged");
        mVisible = screenState == SCREEN_STATE_ON;
        updateRunning();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        CofferLog.D(COFFER_TAG,"onVisibilityChanged");
        mVisible = visibility == View.VISIBLE;
        updateRunning();
    }

    private void invalidateView() {
        if (mIsAbove16) {
            postInvalidateOnAnimation();
        } else {
            invalidate();
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        CofferLog.D(COFFER_TAG,"onWindowVisibilityChanged");
        mVisible = visibility == View.VISIBLE;
        updateRunning();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        CofferLog.D(COFFER_TAG,"onAttachedToWindow");
        if (!mReceiverRegister) {
            final IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);
            filter.addAction(Intent.ACTION_USER_PRESENT);
            try {
                getContext().registerReceiver(mReceiver, filter);
                mReceiverRegister = true;
            } catch (Exception e) {
                CofferLog.e(e);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CofferLog.D(COFFER_TAG,"onDetachedFromWindow");
        if (mReceiverRegister) {
            try {
                getContext().unregisterReceiver(mReceiver);
                mReceiverRegister = false;
            } catch (Exception e) {
                CofferLog.e(e);
            }
        }
        updateRunning();
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mUserPresent = false;
                updateRunning();
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mUserPresent = true;
                updateRunning();
            }
        }
    };
}
