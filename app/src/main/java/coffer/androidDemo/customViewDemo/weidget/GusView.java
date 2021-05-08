package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.androidjatpack.R;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：3/15/21
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class GusView extends View {

    private Drawable drawable;
    private Bitmap bitmap;
    private Paint mBpPaint;
    private Rect mShadowRectCurr;

    public GusView(Context context) {
        this(context,null);
    }

    public GusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBpPaint = new Paint();
        mBpPaint.setColor(Color.BLUE);
        mBpPaint.setAntiAlias(true);
        mBpPaint.setShadowLayer(5, 30, 30, 0xffe2ecf2);

//        mShadowRectCurr = new Rect();
//        drawable = context.getResources().getDrawable(R.drawable.bg_btn);
//        bitmap = Util.createBottomGraphicBitmap(Util.drawableToBitamp(drawable), 800, 60, 30, 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth()/2, getHeight()/2,50, mBpPaint);
    }

}
