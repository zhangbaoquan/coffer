package coffer.widget;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.util.CofferLog;

/**
 * @author：张宝全
 * @date：2020/6/2
 * @Description： 文字上下滚动的BannerView
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferTextBannerView extends View {

    private static final int PLAY_DURATION = 500;

    private static final int PAUSE_TIME = 3000;

    /**
     * 画笔
     */
    private Paint mPaint;
    private Paint.FontMetrics mFontMetrics;
    private int mTextColor;
    private int mTextSizeDp;

    /** 动画控制相关 **/
    private ValueAnimator mAnimator;
    private Handler mHandler;
    /**
     * 滚动动画的间隔时间、播放时间
     */
    private int mPauseTime;
    private int mPlayingTime;
    /**
     * 动画是否允许执行
     */
    private boolean mRunning = false;
    /**
     * 当前View 是否可见
     */
    private boolean mViewVisible;

    /**
     * 滚动消息的标识
     */
    private static final int SCROLL_MSG = 1;

    /**
     * 当前是否锁屏
     */
    private boolean mScreenOn;

    private int mCurPosition;
    private int mNextPosition;

    /**
     * SDK 版本是否大于16
     */
    private boolean mIsAbove16;

    /**
     * 内容
     */
    private ArrayList<String> mContent;
    private boolean mReceiverRegister;

    public CofferTextBannerView(Context context) {
        super(context);
        init(context,null);
    }

    public CofferTextBannerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CofferTextBannerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        // 初始化相关属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextBannerView);
        mPauseTime = typedArray.getInt(R.styleable.TextBannerView_pause_duration,PAUSE_TIME);
        mPlayingTime = typedArray.getInt(R.styleable.TextBannerView_play_duration,PLAY_DURATION);
        mTextColor = typedArray.getColor(R.styleable.TextBannerView_banner_text_color, getResources().getColor(R.color.item_h1_text_color));
        mTextSizeDp = getResources().getDimensionPixelSize(R.dimen.ad_text_size);

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSizeDp);
        mFontMetrics = mPaint.getFontMetrics();

        // 初始化动画
        mAnimator = ValueAnimator.ofInt(0,1);
        mAnimator.setDuration(mPlayingTime);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == SCROLL_MSG){

                    sendEmptyMessageDelayed(SCROLL_MSG,mPauseTime);
                }
            }
        };

    }

    private void setContent(ArrayList<String> content){
        mContent = content;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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
                CofferLog.e(e);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mReceiverRegister){
            try {
                getContext().unregisterReceiver(mReceiver);
                mReceiverRegister = false;
            }catch (Exception e){
                CofferLog.e(e);
            }
        }
        updateSetting();
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        // 屏幕锁屏会调用此方法
        mScreenOn = screenState == SCREEN_STATE_ON;
        updateSetting();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        // View 的显示状态发生改变时也会调用此方法
        mViewVisible = VISIBLE == visibility;
        updateSetting();
    }

    private void invalidateView(){
        if (mIsAbove16){
            postInvalidateOnAnimation();
        }else {
            invalidate();
        }
    }

    private void updateSetting(){
        boolean currentStatus = mViewVisible && mScreenOn;
        if (currentStatus != mRunning){
            mRunning = currentStatus;
            if (currentStatus){
                // 当前可以执行动画，状态改变时需要先移除前一个动画
                mHandler.removeMessages(SCROLL_MSG);
                mHandler.sendEmptyMessageDelayed(SCROLL_MSG,PAUSE_TIME);
            }else {
                mHandler.removeMessages(SCROLL_MSG);
            }
        }
    }

    /**
     * 注册监听屏幕是否锁屏的广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)){
                mScreenOn = false;
                updateSetting();
            }else if (Intent.ACTION_USER_PRESENT.equals(action)){
                mScreenOn = true;
                updateSetting();
            }
        }
    };
}
