package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：张宝全
 * @date：2020-03-14
 * @Description：实现RecycleView弹性拖拽第一版
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ScollerContainer extends RelativeLayout {
    private static final String TAG = "ScollerContainer_tag";

    private RecyclerView mRecyclerView;
    private Scroller mScroller;

    public ScollerContainer(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ScollerContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    int mInterceptLastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        if (mRecyclerView == null) {
            return false;
        }
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        View view = linearLayoutManager.findViewByPosition(0);
        if (view == null){
            return false;
        }
        int top = view.getTop();
        int y = (int)ev. getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mInterceptLastY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (top == 0 && y - mInterceptLastY > 0){
                    intercept = true;
                }else {
                    intercept = false;
                }
                break;
            default:
                break;
        }
        return intercept;
    }

    int mLastX;
    int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        int y = (int) e.getY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.isFinished();
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltay = y - mLastY;
                Log.i(TAG,"deltay : "+deltay);
                if (deltay > 0){
                    scrollBy(0, -deltay);
                }
                break;
            case MotionEvent.ACTION_UP:
                smoothScroller(0, 0);
                break;
            default:
                break;
        }
        mLastY = y;
        return false;
    }

    private void smoothScroller(int dx,int dy){
        mScroller.startScroll(0,0,dx,dy,500);
        invalidate();

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
