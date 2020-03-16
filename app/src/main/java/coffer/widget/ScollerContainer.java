package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * @author：张宝全
 * @date：2020-03-14
 * @Description：实现QQ侧滑
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ScollerContainer extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView,mMainView;
    private int mWidth;

    public ScollerContainer(@NonNull Context context) {
        super(context);
        initView();
    }

    public ScollerContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    private void initView(){
        mViewDragHelper = ViewDragHelper.create(this,callback);
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback(){

        // 开始检测触摸事件
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return mMainView == child;
        }

        // 处理水平方向滑动
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {

            return left;
        }

        // 处理竖直方向滑动
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return 0;
        }

        // 拖到结束后调用
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 手指抬起后缓慢移动到指定的位置
            if (mMainView.getLeft() < 500){
                // 关闭菜单，相当于Scroller调用startScroll
                mViewDragHelper.smoothSlideViewTo(mMainView,0,0);
                ViewCompat.postInvalidateOnAnimation(ScollerContainer.this);
            }else {
                // 打开菜单
                mViewDragHelper.smoothSlideViewTo(mMainView,300,0);
                ViewCompat.postInvalidateOnAnimation(ScollerContainer.this);
            }
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMainView.getMeasuredWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 将触摸事件传递给mViewDragHelper
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
