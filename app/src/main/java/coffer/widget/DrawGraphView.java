package coffer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/5/1
 * @Description： 绘制图形练习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class DrawGraphView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mWidth;
    private int mHeight;

    private int mSize;
    private int mRoundSize;

    private Picture mPicture;

    public DrawGraphView(Context context) {
        super(context);
        init(context);
    }

    public DrawGraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        // 初始化一些大小
        mSize = Util.dipToPixel(context,80);
        mRoundSize = Util.dipToPixel(context,8);

        // 设置画笔属性：黑色、抗锯齿、镂空
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(Util.dipToPixel(context,1));
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        // 设置文字画笔属性：大小8dp、红色、对齐居中、抗锯齿、镂空
        mTextPaint = new Paint();
        mTextPaint.setTextSize(Util.dipToPixel(context,10));
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        // Picture看作是一个录制Canvas操作的录像机
        mPicture = new Picture();
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
        drawTest(canvas);
    }

    /**
     * 绘制练习
     * @param canvas 画布
     */
    private void drawTest(Canvas canvas){
        // 绘制矩形
        RectF rectF = new RectF(0,0,mWidth,mHeight);
//        canvas.drawRect(0,0,mWidth,mHeight,mPaint);
//        canvas.drawRect(rectF,mPaint);

        // 绘制圆角矩形
        canvas.drawRoundRect(rectF,mRoundSize,mRoundSize,mPaint);

        // 绘制文字（居中）,首先需要计算基准线,y = 矩形中心y值 + 矩形中心与基线的距离,
        // 文字高度的一半 - 基线到文字底部的距离（也就是bottom）
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseLine = rectF.centerY() + distance;
        canvas.drawText("coffer",rectF.centerX(),baseLine,mTextPaint);

        // 将画布的坐标平移至矩形的View的中心
//        canvas.translate(mWidth/2,mHeight/2);
//        // 将画布缩放一半，向第四象限方向
//        canvas.scale(0.5f,0.5f);
//        canvas.drawRoundRect(rectF,mRoundSize,mRoundSize,mPaint);
//        canvas.rotate(180);
//        canvas.drawRoundRect(rectF,mRoundSize,mRoundSize,mPaint);

        // 内矩形内绘制一个表盘
//        canvas.drawCircle(mWidth/2,mHeight/2,90,mPaint);
//        canvas.drawCircle(mWidth/2,mHeight/2,80,mPaint);
//        canvas.translate(mWidth/2,mHeight/2);
//        for (int i = 0; i < 360; i+=10) {
//            canvas.drawLine(0,80,0,90,mPaint);
//            canvas.rotate(10);
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
