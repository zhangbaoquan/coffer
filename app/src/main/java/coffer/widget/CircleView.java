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
 * @date：2020-03-22
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CircleView extends View {

    private Paint mPaint;

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth() - getPaddingLeft() - getPaddingRight();
        int y = getHeight()  - getPaddingTop() - getPaddingBottom();
        int radius = Math.min(x,y) / 2;
        canvas.drawCircle(x / 2,y / 2,radius,mPaint);
    }
}
