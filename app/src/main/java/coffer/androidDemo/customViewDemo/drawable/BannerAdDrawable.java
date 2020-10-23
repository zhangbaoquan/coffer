package coffer.androidDemo.customViewDemo.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import coffer.androidjatpack.R;
import coffer.util.Util;

/**
 * 精选banner卡片样式
 * Created by jy on 2020/3/14.
 */
public class BannerAdDrawable extends Drawable {

    private Bitmap bitmap;
    private String des = "", source = "", btnText = "";
    private String sourceText;

    private Paint mPaintSource, mPaintButton;
    private TextPaint mPaintDes;
    private StaticLayout slDes;

    private Rect mRectImg, mRectDes, mRectSource, mRectBtn;
    private Rect mRectBM;

    private Paint mPaintBg;
    private RectF mRectBg;
    private int radius;

    public BannerAdDrawable(Context context, Bitmap bitmap, String des, String source, String btnText, int width, int height) {
        radius = Util.dipToPixel(context, 4);
        this.bitmap = bitmap;
        if (!TextUtils.isEmpty(des)) {
            this.des = des;
        }
        if (!TextUtils.isEmpty(source)) {
            this.source = source;
        }
        if (!TextUtils.isEmpty(btnText)) {
            this.btnText = btnText;
        }

        mPaintDes = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaintDes.setTextSize(Util.spToPixel(context, 14));
        mPaintDes.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        mPaintSource = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mPaintSource.setTextSize(Util.spToPixel(context, 12));

        mPaintButton = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintButton.setTextSize(Util.spToPixel(context, 13));
        mPaintButton.setColor(context.getResources().getColor(R.color.blue));
        mPaintButton.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));

        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);

        setThemeColor(context);

        calculateImgRect(context, width, height, bitmap.getWidth(), bitmap.getHeight());
        calculateButtonRect(context, width);
//        calculateSourceRect(context, width);
//        calculateDesRect(context, width);

        mRectBg = new RectF();
        mRectBg.left = 0;
        mRectBg.top = 0;
        mRectBg.right = width;
        mRectBg.bottom = height;

        setBounds(0, 0, width, height);

    }

    private void calculateDesRect(Context context, int width) {
        mRectDes = new Rect();
        int maxWidth = width - (mRectSource.left + Util.dipToPixel(context, 20));
        int left = mRectSource.left;
        int top = mRectImg.top + Util.dipToPixel(context, 3);
        slDes = new StaticLayout(des, mPaintDes, maxWidth, Layout.Alignment.ALIGN_NORMAL, 1f, Util.dipToPixel(context, 2), false);
        mRectDes.set(left, top, 0, 0);
    }

    private void calculateButtonRect(Context context, int width) {
        mRectBtn = new Rect();
        int length = (int) mPaintButton.measureText(btnText);
        int right = width - Util.dipToPixel(context, 20);
        int bottom = mRectImg.bottom;
        int left = right - length;
        int top = (int) (bottom - (mPaintButton.descent() - mPaintButton.ascent()));
        mRectBtn.set(left, top, right, bottom);
    }

    private void calculateSourceRect(Context context, int width) {
        mRectSource = new Rect();
        sourceText = "广告  " + source;
        int length = (int) mPaintSource.measureText(sourceText);
        int left = mRectImg.right + Util.dipToPixel(context, 14);
        int bottom = mRectImg.bottom;
        int right = left + length;
        int top = (int) (bottom - (mPaintSource.descent() - mPaintSource.ascent()));
        int maxLength = width - left - (width - mRectBtn.left) - 36;
        if (length > maxLength) {
            substringAdSource(maxLength);
        }
        mRectSource.set(left, top, right, bottom);
    }

    private void substringAdSource(int maxLength) {
        sourceText = sourceText.substring(0, sourceText.length() - 1);
        if (mPaintSource.measureText(sourceText) > maxLength) {
            substringAdSource(maxLength);
        } else {
            sourceText += "...";
        }
    }

    private void calculateImgRect(Context context, int width, int height, int bmWidth, int bmHeight) {
        mRectImg = new Rect();
        int imgWidth = Util.dipToPixel(context, 90);
        int imgHeight = imgWidth * bmHeight / bmWidth;
        bitmap = scaleBitmap(bitmap, imgWidth, imgHeight);
        bitmap = getRoundBitmap(bitmap);
        mRectBM = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        int left = Util.dipToPixel(context, 20);
        int top = (height - imgHeight) / 2 - Util.dipToPixel(context, 4);
        int right = left + imgWidth;
        int bottom = top + imgHeight;
        mRectImg.set(left, top, right, bottom);
    }

    private Bitmap getRoundBitmap(Bitmap mBitmap) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, radius, radius, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;

    }

    private void setThemeColor(Context context) {
        mPaintDes.setColor(context.getResources().getColor(R.color.item_h1_text_color));
        mPaintSource.setColor(context.getResources().getColor(R.color.item_h3_text_color));
        mPaintBg.setColor(context.getResources().getColor(R.color.zydark_color_banner_ad_background));
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin    原图
     * @param newWidth  新图的宽
     * @param newHeight 新图的高
     * @return new Bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        return Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawRoundRect(mRectBg, radius, radius, mPaintBg);
        canvas.drawBitmap(bitmap, mRectBM, mRectImg, mPaintBg);
        canvas.save();
//        canvas.translate(mRectDes.left, mRectDes.top);
//        slDes.draw(canvas);
        canvas.restore();
//        canvas.drawText(sourceText, mRectSource.left, mRectSource.top - mPaintSource.ascent(), mPaintSource);
//        canvas.drawText(btnText, mRectBtn.left, mRectBtn.top - mPaintButton.ascent(), mPaintButton);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
