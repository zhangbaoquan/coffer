package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

/**
 * @author：张宝全
 * @date：2020/10/10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class AirXfermodeCircleView extends ImageView {

    private static final String TAG = "AirXfermodeCircleView";

    private static final int MIN_SIZE_DEFAULT = 100;

    private Paint mDrawablePaint;

    public AirXfermodeCircleView(Context context) {
        this(context, null);
    }

    public AirXfermodeCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AirXfermodeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AirXfermodeCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


        initializePaints();
    }

    private void initializePaints() {

        mDrawablePaint = new Paint();
        mDrawablePaint.setAntiAlias(true);
        mDrawablePaint.setColor(Color.BLUE);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST){
            widthSize = dpToPx(MIN_SIZE_DEFAULT);
        }

        if (heightMode == MeasureSpec.AT_MOST){
            heightSize = dpToPx(MIN_SIZE_DEFAULT);
        }

        int minSize = Math.min(widthSize, heightSize);

        //Update measured dimension.
        setMeasuredDimension(minSize, minSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Canvas circle image.

//        try {
//            drawCircleImage(canvas);
//        }catch (Exception e){
//
//        }
//        mDrawablePaint.setXfermode(null);
        drawImage(canvas);
    }

    private void drawImage(Canvas canvas){
        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);

        int canvasWidth = canvas.getWidth();
        int r = 150;
        int cx = getResources().getDisplayMetrics().widthPixels / 2;
        int cy = getResources().getDisplayMetrics().heightPixels / 2;
        //绘制黄色的圆形
        mDrawablePaint.setColor(0xFFFFCC44);
        canvas.drawCircle(cx, cy, r, mDrawablePaint);
        //绘制蓝色的矩形
//        mDrawablePaint.setColor(0xFF66AAFF);
//        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, mDrawablePaint);
    }

    /**
     * Draw circle image with paint PorterDuff.
     *
     * @param canvas
     *         canvas.
     */
    private void drawCircleImage(Canvas canvas) {

        //Save layer with parameter.
        canvas.saveLayerAlpha(getLeft(), getTop(), getRight(), getBottom(), 200);

        //Get drawable source.
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();

        //Computer circle image radius.
        float radiusX = (getWidth() - getPaddingLeft() - getPaddingRight()) / 2f;
        float radiusY = (getHeight() - getPaddingTop() - getPaddingBottom()) / 2f;
        float radius = Math.min(radiusX, radiusY);

        //Computer
        Rect rectF = new Rect();
        rectF.left = getLeft() + getPaddingLeft();
        rectF.top = getTop() + getPaddingTop();
        rectF.right = getRight() - getPaddingRight();
        rectF.bottom = getBottom() - getPaddingBottom();

        int targetBitmapWidth = rectF.right - rectF.left;
        int targetBitmapHeight = rectF.bottom - rectF.top;

        int diameter = (int) (radius * 2 + 0.5);

        float circleX = getPaddingLeft() + radius;
        float circleY = getPaddingTop() + radius;

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetBitmapWidth, targetBitmapHeight, false);

        /*
        Draw destination bitmap.
        */
        Bitmap bitmapDestination = Bitmap.createBitmap(targetBitmapWidth, targetBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvasDestination = new Canvas(bitmapDestination);
        //Draw circle.
        canvasDestination.drawCircle(circleX, circleY, radius, mDrawablePaint);
        //Draw destination bitmap.
        canvas.drawBitmap(bitmapDestination, 0, 0, mDrawablePaint);

        /*
        Set  paint's porterDuffMode
        */
        mDrawablePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        /*
        Draw source bitmap.
        */
        canvas.drawBitmap(scaledBitmap, rectF, rectF, mDrawablePaint);

        //Reset mode value.
        mDrawablePaint.setXfermode(null);
        //Restore.
        canvas.restore();

    }


    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5);
    }

}
