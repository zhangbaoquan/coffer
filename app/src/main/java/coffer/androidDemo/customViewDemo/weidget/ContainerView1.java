package coffer.androidDemo.customViewDemo.weidget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.common.ComRecycleViewAdapter;
import coffer.common.ComRecycleViewHolder;
import coffer.util.CofferLog;

/**
 * @author：张宝全
 * @date：2020/8/14
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class ContainerView1 extends RelativeLayout {

    private static final String TAG = "ContainerView1_tag";

    private RecyclerView mRecyclerView;
    private ViewPager2 mViewPager2;
    private ArrayList<String> mData;

    private int mInterceptLastX;
    private int mInterceptLastY;

    private int mLastX;

    /**
     * 最小识别的滑动距离
     */
    private int mTouchSlop;

    public ContainerView1(Context context) {
        super(context);
        initView(context);
    }

    public ContainerView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ContainerView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setBackground(context.getResources().getDrawable(R.drawable.bg1));
        initData();
        mRecyclerView = new RecyclerView(context);
        addView(mRecyclerView, new RecyclerView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        initAdapter();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mData.add("欢欢 " + i);
        }
    }

    private void initAdapter() {
        ComRecycleViewAdapter<String> adapter = new ComRecycleViewAdapter<>(mData, new ComRecycleViewAdapter.OnBindDataListener<String>() {
            @Override
            public void onBindViewHolder(String model, ComRecycleViewHolder viewHolder, int type, int position) {
                viewHolder.setText(R.id.tv, model);
            }

            @Override
            public int getLayoutId(int type) {
                return R.layout.item_1;
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        CofferLog.I(TAG, "dispatchTouchEvent: ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        CofferLog.I(TAG, "onInterceptTouchEvent: ");
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                CofferLog.I(TAG, "down: ");
                intercept = false;
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mInterceptLastX;
                int deltaY = y - mInterceptLastY;
                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    CofferLog.I(TAG, "拦截 : ");
                    // 横向滑动时，拦截
                    intercept = true;
                } else {
                    intercept = false;
                    CofferLog.I(TAG, "不拦截 : ");
                }
                break;
            default:
                break;
        }
        mInterceptLastX = x;
        mInterceptLastY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

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
