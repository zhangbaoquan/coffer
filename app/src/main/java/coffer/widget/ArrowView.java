package coffer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author：张宝全
 * @date：2019-06-13
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ArrowView extends View {


    private int mStrokeColor; // 边框的颜色
    private int mStrokeWidth; // 边框的宽度
    private Paint mPaint = new Paint();

    private int mWidth;
    private int mHeight;

    public ArrowView(Context context) {
        super(context);
        init(context);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArrowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mPaint.setColor(Color.BLACK);       //设置画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //设置画笔模式为填充
        mPaint.setStrokeWidth(10f);         //设置画笔宽度为10px
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算宽度
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                mWidth = 50;
            }
        }

        // 计算高度
        int heighMode = MeasureSpec.getMode(widthMeasureSpec);
        int heighSize = MeasureSpec.getSize(widthMeasureSpec);
        if (heighMode == MeasureSpec.EXACTLY) {
            mHeight = heighSize;
        } else {
            if (heighMode == MeasureSpec.AT_MOST) {
                mHeight = 550;
            }

        }
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0,0,mWidth/2,mHeight/2,mPaint);
        canvas.drawLine(mWidth/2,mHeight/2,0,mHeight,mPaint);
    }
}
