package coffer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @author：张宝全
 * @date：2020/7/14
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class GrayFrameLayout extends FrameLayout {

    private Paint mPaint;

    public GrayFrameLayout(Context context) {
        super(context);
        init();
    }

    public GrayFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GrayFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        mPaint = new Paint();
        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(null,mPaint,Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(null,mPaint,Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        canvas.restore();
    }
}
