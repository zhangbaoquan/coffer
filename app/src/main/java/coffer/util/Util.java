package coffer.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import coffer.CofferApplication;
import coffer.androidDemo.customViewDemo.recycleView.PauseOnScrollListener;


/**
 * @author：张宝全
 * @date：2019-11-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class Util {

    public static int dipToPixel(Context context, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1);
    }

    public static float dipToPixel(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1;
    }

    public static int dipToPixel(Resources r, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics()) + 1);
    }

    public static int spToPixel(Context context, int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public static void setNavVisibility(final boolean visible, Activity activity) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        if (!visible) {
            newVis = 256 //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | 512 //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | 1024 //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | 4 //View.SYSTEM_UI_FLAG_FULLSCREEN
                    | 2 //View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | 4096 //View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | 2048; //View.SYSTEM_UI_FLAG_IMMERSIVE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; //View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | 4096; //View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            activity.getWindow().getDecorView().setSystemUiVisibility(newVis);
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity.getWindow().getDecorView().setSystemUiVisibility(newVis);
        }
    }

    /**
     * setPauseOnScrollListener:设置滚动暂停监听. <br/>
     *
     * @param view
     * @param customScrollListener
     * @author huangyahong
     */
    public static void setPauseOnScrollListener(RecyclerView view, RecyclerView.OnScrollListener customScrollListener) {
        if (view == null)
            throw new IllegalArgumentException("view 不能为空");
        PauseOnScrollListener pauseOnScrollListener = null;
        int level = AppMemeryLevelUtils.getAppMemeryLevel(CofferApplication.getInstance());
        if (level == AppMemeryLevelUtils.HIGHT_MEMERY) {
            // 高运行内存，滚动不暂停
            pauseOnScrollListener =
                    new PauseOnScrollListener(false, false, customScrollListener);
        } else if (level == AppMemeryLevelUtils.MIDDLE_MEMERY) {
            // 中运行内存，快速滚动不暂停图片加载
            pauseOnScrollListener =
                    new PauseOnScrollListener(false, true, customScrollListener);
        } else if (level == AppMemeryLevelUtils.LOW_MEMERY) {
            // 中运行内存，滚动和快速滚动暂停图片加载
            pauseOnScrollListener =
                    new PauseOnScrollListener(true, true, customScrollListener);
        }
        view.addOnScrollListener(pauseOnScrollListener);
    }

    /**
     * 将颜色的十进制转换成RGB 数组
     *
     * @return
     */
    public static int[] colorsConvertToRgb(int color) {
        int rgb[] = new int[3];

        int b = color & 0xff;

        int g = (color >> 8) & 0xff;

        int r = (color >> 16) & 0xff;

        rgb[0] = r;

        rgb[1] = g;

        rgb[2] = b;

        return rgb;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 计算两个点之间的距离
     *
     * @param startPoint
     * @param endPoint
     * @return
     */
    public static int calculateA2B(Point startPoint, Point endPoint) {
        int xDist = startPoint.x - endPoint.x;
        int yDist = startPoint.y - endPoint.y;
        return (int) Math.sqrt(xDist * xDist + yDist * yDist);
    }

    /**
     * 计算两个点相对于屏幕坐标的夹角
     *
     * @param startPoint
     * @param endPoint
     * @return
     */
    public static float calculateGradient(Point startPoint, Point endPoint) {
        int xDist = startPoint.x - endPoint.x;
        int yDist = startPoint.y - endPoint.y;
        return (float) xDist / (float) yDist;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 获取底部高斯模糊的阴影
     *
     * @param srcBitmapHeight 高斯模糊的高度（从底部算）
     * @param blurLRWidth     左右模糊宽度
     */
    public static Bitmap createBottomGraphicBitmap(Bitmap cover, int width, int height, int srcBitmapHeight, int blurLRWidth) {
        if (height <= 0 || width <= 0) {
            return null;
        }
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        RectF dstRect = new RectF(0, 0, width, height);
        Bitmap finalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(finalBitmap);
        if (Util.isRecycle(cover)){
            paint.setColor(0x33222222);
            canvas.drawRect(dstRect, paint);
        } else {
            int left = (int) ((cover.getWidth() - width) * 0.5f);
            Rect srcRect = new Rect(left, cover.getHeight() - srcBitmapHeight, width + left, cover.getHeight());
            canvas.drawBitmap(cover, srcRect, dstRect, paint);
            paint.setColor(0x33222222);
            canvas.drawRect(dstRect, paint);
            Bitmap b = Util.blur(finalBitmap, 64, getLegalRadius(10), true);
            if (b != null && !b.isRecycled()) {
                paint.setAlpha(255);
                canvas.drawBitmap(b, null, dstRect, paint);
            } else {
                paint.setColor(0x33222222);
                canvas.drawRect(dstRect, paint);
            }
        }
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        paint.setColor(0xFF222222);
        //左边模糊
        paint.setShader(new LinearGradient(0, 0, blurLRWidth, 0, 0xFF222222, 0x00222222, Shader.TileMode.CLAMP));
        dstRect.set(0, 0, blurLRWidth, height);
        canvas.drawRect(dstRect, paint);
        //右边模糊
        paint.setShader(new LinearGradient(width - blurLRWidth, 0, width, 0, 0x00222222, 0xFF222222, Shader.TileMode.CLAMP));
        dstRect.set(width - blurLRWidth, 0, width, height);
        canvas.drawRect(dstRect, paint);
        //底部模糊
        paint.setShader(new LinearGradient(0, 0, 0, height, new int[]{0x00222222, 0xFF222222},
                new float[]{0.5f, 1f}, Shader.TileMode.CLAMP));
        dstRect.set(0, 0, width, height);
        canvas.drawRect(dstRect, paint);
        //底部再模糊一次
        paint.setShader(new LinearGradient(0, 0, 0, height, new int[]{0x00222222, 0xAA222222},
                new float[]{0.6f, 1f}, Shader.TileMode.CLAMP));
        canvas.drawRect(dstRect, paint);
        return finalBitmap;
    }

    /**
     * 判断图片是否释放
     *
     * @param bitmap
     * @return
     */
    public static boolean isRecycle(Bitmap bitmap) {
        if (bitmap == null) return true;
        return bitmap.isRecycled();
    }

    public static int getLegalRadius(int radius) {
        if (radius <= 0) {
            return 1;
        }
        long maxMemory = Runtime.getRuntime().freeMemory();
        int temp = (radius + radius + 2) >> 1;
        temp *= temp;
        //short大小：2bytes
        long blurDvArraySize = 256 * temp * 2;
        if (blurDvArraySize >= maxMemory) {
            return getLegalRadius(radius - 10);
        } else {
            return radius;
        }
    }

    /**
     * StackBlur By Java Bitmap
     *
     * @param original         Original Image
     * @param radius           Blur radius
     * @param canReuseInBitmap Can reuse In original Bitmap
     * @return Image Bitmap
     */
    public static Bitmap blur(Bitmap original, int size, int radius, boolean canReuseInBitmap) {

        if (radius < 1 ||  original == null) {
            return (null);
        }

        Bitmap src = buildBitmap(original, canReuseInBitmap);

        Bitmap bitmap = Bitmap.createScaledBitmap(src, size, size, false);
        // Return this none blur
        if (radius == 1) {
            return bitmap;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        // get array
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        // run Blur
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        short r[] = new short[wh];
        short g[] = new short[wh];
        short b[] = new short[wh];
        int rSum, gSum, bSum, x, y, i, p, yp, yi, yw;
        int vMin[] = new int[Math.max(w, h)];

        int divSum = (div + 1) >> 1;
        divSum *= divSum;

        short dv[] = new short[256 * divSum];
        for (i = 0; i < 256 * divSum; i++) {
            dv[i] = (short) (i / divSum);
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackPointer;
        int stackStart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routSum, goutSum, boutSum;
        int rinSum, ginSum, binSum;

        for (y = 0; y < h; y++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rbs = r1 - Math.abs(i);
                rSum += sir[0] * rbs;
                gSum += sir[1] * rbs;
                bSum += sir[2] * rbs;
                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }
            }
            stackPointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rSum];
                g[yi] = dv[gSum];
                b[yi] = dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (y == 0) {
                    vMin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vMin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = (p & 0x0000ff);

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[(stackPointer) % div];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinSum = ginSum = binSum = routSum = goutSum = boutSum = rSum = gSum = bSum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rSum += r[yi] * rbs;
                gSum += g[yi] * rbs;
                bSum += b[yi] * rbs;

                if (i > 0) {
                    rinSum += sir[0];
                    ginSum += sir[1];
                    binSum += sir[2];
                } else {
                    routSum += sir[0];
                    goutSum += sir[1];
                    boutSum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackPointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rSum] << 16) | (dv[gSum] << 8) | dv[bSum];

                rSum -= routSum;
                gSum -= goutSum;
                bSum -= boutSum;

                stackStart = stackPointer - radius + div;
                sir = stack[stackStart % div];

                routSum -= sir[0];
                goutSum -= sir[1];
                boutSum -= sir[2];

                if (x == 0) {
                    vMin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vMin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinSum += sir[0];
                ginSum += sir[1];
                binSum += sir[2];

                rSum += rinSum;
                gSum += ginSum;
                bSum += binSum;

                stackPointer = (stackPointer + 1) % div;
                sir = stack[stackPointer];

                routSum += sir[0];
                goutSum += sir[1];
                boutSum += sir[2];

                rinSum -= sir[0];
                ginSum -= sir[1];
                binSum -= sir[2];

                yi += w;
            }
        }

        // set Bitmap
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return (bitmap);
    }

    private static Bitmap buildBitmap(Bitmap original, boolean canReuseInBitmap) {
        // First we should check the original
        if (original == null)
            throw new NullPointerException("Blur bitmap original is null");

        Bitmap.Config config = original.getConfig();
        if (config != Bitmap.Config.ARGB_8888 && config != Bitmap.Config.RGB_565) {
            throw new RuntimeException("Blur bitmap only supported Bitmap.Config.ARGB_8888 and Bitmap.Config.RGB_565.");
        }

        // If can reuse in bitmap return this or copy
        Bitmap rBitmap;
        if (canReuseInBitmap) {
            rBitmap = original;
        } else {
            rBitmap = original.copy(config, true);
        }
        return (rBitmap);
    }

    /**
     * drawable转bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

}
