package coffer.androidDemo.customViewDemo.weidget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.common.ComRecycleViewAdapter;
import coffer.common.ComRecycleViewHolder;
import coffer.jetpackDemo.adapter.ViewPagerFragmentStateAdapter;
import coffer.util.APP;
import coffer.util.CofferLog;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/8/14
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class ContainerView2 extends RelativeLayout {

    private static final String TAG = "ContainerView2_tag";

    private ZYViewPager mViewPager;
    protected static final int TOUCH_STATE_REST = 0;
    protected static final int TOUCH_STATE_SCROLLING = 1;
    protected int mTouchState;


    private int mInterceptLastX;
    private int mInterceptLastY;

    private int mLastX;

    /**
     * 最小识别的滑动距离
     */
    private int mTouchSlop;

    protected static final int GRADIENT = 4;

    /**
     * 起点坐标
     */
    private Point mStartPoint;
    private Point mMovePoint;

    private boolean mHLock = false;
    private boolean mVLock = false;

    public ContainerView2(Context context) {
        super(context);
        initView(context);
    }

    public ContainerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ContainerView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2,null);
        addView(view);
        mStartPoint = new Point();
        mMovePoint = new Point();
        mViewPager = view.findViewById(R.id.viewPager);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 3 / 2;
        setBackground(context.getResources().getDrawable(R.drawable.bg1));
        initAdapter(context);
    }


    private void initAdapter(Context context) {
        ArrayList<View> viewList = new ArrayList<>();
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.drawable.bilibili);
        ImageView imageView2 = new ImageView(context);
        imageView2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView2.setImageResource(R.drawable.coffer);
        viewList.add(imageView);
        viewList.add(imageView2);
        mViewPager.setAdapter(new CofferPagerAdapter(viewList));
        mViewPager.setCanScroll(true);
        mViewPager.setOnPageChangeListener(new PageSelectListener());
    }

    private class CofferPagerAdapter extends PagerAdapter {

        private ArrayList<View> mViewList;

        public CofferPagerAdapter(ArrayList<View> viewList) {
            this.mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mViewList.get(position);
            container.addView(v);
            return v;
        }
    }

    private class PageSelectListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            CofferLog.I(TAG, "i : " + i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    boolean mIsGetFocus = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 处理事件分发
        int action = ev.getAction();
        int childCount = getChildCount();
        if (action == MotionEvent.ACTION_DOWN){
            mIsGetFocus = false;
        }
        //不支持多点触摸
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            // 自己处理触摸事件
            if (onInterceptTouchEvent(ev)){
                // 如果自己没有获取到触摸焦点，而是在move事件的时候获取，则应给之前获取到触摸焦点控件发送cancel事件
                if (!mIsGetFocus && childCount != 0 && action == MotionEvent.ACTION_MOVE){
                    MotionEvent motionEvent = MotionEvent.obtain(ev);
                    motionEvent.setAction(MotionEvent.ACTION_CANCEL);
                    motionEvent.offsetLocation(-getChildAt(childCount - 1).getLeft(), -getChildAt(childCount - 1).getTop());
                    getChildAt(childCount - 1).dispatchTouchEvent(motionEvent);
                }
                mIsGetFocus = true;
                return onTouchEvent(ev);
            }else {
                // 释放触摸焦点
                mIsGetFocus = false;
            }

        }else if (mIsGetFocus){
            //如果自己已经或者触摸焦点了，而且事件又是非down和move(即可能是up、cancel、outside)
            //则该事件应交给自己
            return onTouchEvent(ev);
        }
        //由于触摸事件不能传递到非顶端控件，所以该触摸事件交给顶端的子控件处理
        if (childCount == 0) {
            return false;
        } else {
            boolean childDispatch;
            try {
                ev.offsetLocation(-getChildAt(childCount - 1).getLeft(), -getChildAt(childCount - 1).getTop());
                childDispatch = getChildAt(childCount - 1).dispatchTouchEvent(ev);
            } catch (Exception e) {
                childDispatch = false;
            }
            if (childDispatch) {
                return true;
            } else {
                mIsGetFocus = true;
                return true;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        CofferLog.I(TAG, "onInterceptTouchEvent : ");
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            mHLock = false;
            mVLock = false;
            mStartPoint.x = x;
            mStartPoint.y = y;
            mLastX = x;
            mTouchState = TOUCH_STATE_REST;
        }
        if (!APP.getEnableScrollToRigh()) {
            if (ev.getAction() != MotionEvent.ACTION_MOVE) {
                APP.setEnableScrollToRight(true);
            }
            return super.onInterceptTouchEvent(ev);
        }
        final int action = ev.getAction();
        float left = 0;
        float deltaX = x - mLastX;
        if ((action == MotionEvent.ACTION_MOVE) && (mTouchState != TOUCH_STATE_REST) && left + deltaX >= 0) {
            return true;
        }
        switch (action){
            case MotionEvent.ACTION_MOVE:
                mMovePoint.x = x;
                mMovePoint.y = y;

                int slop = Util.calculateA2B(mStartPoint, mMovePoint);
                float grad = Util.calculateGradient(mStartPoint, mMovePoint);

                if (!mHLock && slop >= mTouchSlop) {
                    if (Math.abs(grad) > GRADIENT) {
                        mLastX = x;
                        if (left + deltaX <= 0) {
                            mTouchState = TOUCH_STATE_REST;
                        } else {
                            mTouchState = TOUCH_STATE_SCROLLING;
                        }
                    }else if (mTouchState != TOUCH_STATE_SCROLLING && Math.abs(grad) < 2F) {
                        mHLock = true;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mTouchState = TOUCH_STATE_REST;
            default:
                mTouchState = TOUCH_STATE_REST;
                break;
        }
        return (mTouchState != TOUCH_STATE_REST) && !mHLock;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        CofferLog.I(TAG, "onTouchEvent : ");

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mHLock = false;
            mVLock = false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getRawX();
                int deltaX = x - mLastX;
                CofferLog.I("col", "deltaX : " + deltaX);
                if (deltaX > mTouchSlop && deltaX > 0) {
                    setTranslationX(deltaX);
                }
                break;
            case MotionEvent.ACTION_UP:
                hide();
                break;
            default:
                break;
        }
        return true;
    }

    public void show(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this,
                "translationX", getX(), 0);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    public void hide(){
        int scrollX = (int) getTranslationX();
        // 使用属性动画，将当前View从右边移除
        if (scrollX != 0) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this,
                    "translationX", scrollX, 1080);
            objectAnimator.setDuration(200);
            objectAnimator.start();
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });
        }
    }
}
