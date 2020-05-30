package coffer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import coffer.model.PieData;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/5/1
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class PieView extends View {
    /**
     * 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
     */
    private int[] mColors = {Color.RED, Color.BLACK, Color.YELLOW, Color.BLUE, Color.GREEN,Color.GRAY};

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private ArrayList<PieData> mData;

    private int mSize;

    /**
     * 开始角度
     */
    private int mStartAngle;

    public PieView(Context context) {
        super(context);
        init(context);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mSize = Util.dipToPixel(context,80);
        // 初始化画笔，有填充、抗锯齿
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        initData();
    }

    private void initData(){
        mData = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PieData data = new PieData();
            data.color = mColors[i];
            data.angle = 60;
            mData.add(data);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY){
            // 精确测量
            mWidth = widthSize;
        }else {
            // 最大值
            mWidth = mSize;
        }

        if (heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else {
            mHeight = mSize;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        for (int i = 0; i < mData.size(); i++) {
            int endAngle = mData.get(i).angle;
            mPaint.setColor(mData.get(i).color);
            canvas.drawArc(rectF,mStartAngle,endAngle,true,mPaint);
            mStartAngle += endAngle;
        }
    }

}
