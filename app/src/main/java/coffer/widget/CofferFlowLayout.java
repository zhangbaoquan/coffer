package coffer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import coffer.androidjatpack.R;
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

    /**
     * 在wrap_content下 View的最大值
     */
    private int mMaxSize;
    private Context mContext;
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
        mContext = context;
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
                    // 2.2.2 设置当前的行宽、高
                    lineWidth = childWidth;
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
        realHeight = Math.min(lineHeight + paddingBottom + paddingTop,maxHeight);
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

    /***********  以下是在父容器内创建子View   ************/
    private View createTagView(String title){
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_arrage_item,
                this, false);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(title);
        return view;
    }

    private ArrayList<String> mTitle;
    private ItemClickListener mListener;

    public void setTag(ArrayList<String> title, final ItemClickListener listener){
        removeAllViews();
        mTitle = title;
        mListener = listener;
        int count = title.size();
        for (int i = 0; i < count; i++) {
            View chid = createTagView(title.get(i));
            final int finalI = i;
            chid.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG,"onClick : "+finalI);
                    mListener.onClick(finalI);
                }
            });
            chid.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.i(TAG,"onLongClick : "+finalI);
                    mListener.onLongClick(finalI);
                    return true;
                }
            });
            addView(chid);
        }
    }

    public interface ItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }

    public void removeView(int position){
        View child = getChildAt(position);
        removeView(child);
        updata();
    }

    private void updata(){
        setTag(mTitle,mListener);
    }
}
