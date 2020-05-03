package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import coffer.model.ViewPosData;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： 瀑布流布局
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferFlowLayout extends ViewGroup {

    private static final String TAG = "CofferFlowLayout_tag";

    private int mMaxSize;
    /**
     * 这个集合存放所有子View的位置信息，方便后面布局用
     */
    private ArrayList<ViewPosData> mPosHelper;

    public CofferFlowLayout(Context context) {
        super(context);
        init(context);
    }

    public CofferFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mPosHelper = new ArrayList<>();
        mMaxSize = Util.dipToPixel(context,300);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        // 0、初始化行宽、行高
        int lineWidth = 0,lineHeight = 0;
        // 0.1 初始化瀑布流布局真正的宽、高
        int realWidth = 0,realHeight = 0;
        // 1、设置瀑布流的最大宽、高
        int maxWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : mMaxSize;
        int maxHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : mMaxSize;

        // 2、测量子View的大小
        int childCount = getChildCount();
        mPosHelper.clear();
        Log.i(TAG,"mPosHelper.clear");
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE){
                measureChild(child,widthMeasureSpec,heightMeasureSpec);
                LayoutParams layoutParams = child.getLayoutParams();
                int leftMargin = 0;
                int rightMargin = 0;
                int topMargin = 0;
                int bottomMargin = 0;
                if (layoutParams instanceof MarginLayoutParams){
                    MarginLayoutParams marginLayoutParams = (MarginLayoutParams) layoutParams;
                    leftMargin = marginLayoutParams.leftMargin;
                    rightMargin = marginLayoutParams.rightMargin;
                    topMargin = marginLayoutParams.topMargin;
                    bottomMargin = marginLayoutParams.bottomMargin;
                }
                // 2.1 计算出子View 占据的宽、高
                int childWidth = leftMargin + rightMargin + child.getMeasuredWidth();
                int childHeight = topMargin + bottomMargin + child.getMeasuredHeight();
                // 2.2.1换行
                if (childWidth + lineWidth + paddingLeft + paddingRight > maxWidth) {
                    Log.i(TAG,"换行");
                    // 2.2.2 设置当前的行宽、高
                    lineWidth = 0;
                    realHeight = lineHeight;
                    lineHeight += childHeight;
                    // 2.2.3 计算子View的位置
                    ViewPosData data = new ViewPosData();
                    data.left = paddingLeft + leftMargin;
                    data.top = paddingTop + realHeight + topMargin;
                    data.right = paddingLeft + childWidth - rightMargin;
                    data.bottom = paddingTop + realHeight + childHeight - paddingBottom;
                    mPosHelper.add(data);
                }else {
                    // 2.3.1 计算子View的位置
                    ViewPosData data = new ViewPosData();
                    data.left = paddingLeft + leftMargin +lineWidth;
                    data.top = paddingTop + realHeight + topMargin;
                    data.right = paddingLeft + childWidth + lineWidth - rightMargin;
                    data.bottom = paddingTop + realHeight + childHeight - paddingBottom;
                    mPosHelper.add(data);
                    // 2.3.2 不换行，计算当前的行宽、高
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight,childHeight);
                }
            }
        }
        // 设置最终的宽、高
        realWidth = maxWidth;
        realHeight = Math.max(lineHeight + paddingBottom + paddingTop,maxHeight);
        setMeasuredDimension(realWidth,realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child != null && child.getVisibility() != View.GONE){
                ViewPosData data = mPosHelper.get(i);
                child.layout(data.left,data.top,data.right,data.bottom);
            }
        }
    }
}
