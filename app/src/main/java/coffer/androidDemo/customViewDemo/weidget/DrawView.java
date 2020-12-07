package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import coffer.util.Util;
import coffer.util.global.ScreenSizeUtil;

/**
 * @author：张宝全
 * @date：2020/12/2
 * @Description：绘制练习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class DrawView extends View {

    private static final String TAG = "DrawView_tag";

    /**
     * 雷达的中心坐标
     */
    private int mRadarCenterX, mRadarCenterY;
    /**
     * 雷达网格最大半径
     */
    private float mRadarRadius;
    /**
     * 雷达中间网格的层数
     */
    private int mRadarCount;
    /**
     * 雷达图里的绘制6边行的角度
     */
    private float mRadarAngle;
    /**
     * 雷达画笔
     */
    private Paint mRadarPaint;
    /**
     * 雷达数据画笔
     */
    private Paint mRadarDataPaint;
    /**
     * 模拟雷达数据
     */
    private double[] mRadarData = {2, 5, 1, 3, 6, 4};
    /**
     * 雷达数里的最大值
     */
    private float mRadarMaxValue = 6;

    /**
     * 圆形图像的bitmap
     */
    private Bitmap mCircleBitmap;
    /**
     * 圆形图像路径
     */
    private Path mmCirclePath;
    /**
     * 圆形图像画笔
     */
    private Paint mCirclePaint;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        initRadar();
        initCircleImg();
    }

    /**
     * 雷达图初始化参数
     */
    private void initRadar() {
        mRadarCenterX = ScreenSizeUtil.getScreenWidth() / 2;
        mRadarCenterY = ScreenSizeUtil.getScreenHeight() / 2;
        mRadarRadius = mRadarCenterX * 0.8f;
        mRadarCount = 6;
        mRadarAngle = (float) (Math.PI * 2 / mRadarCount);
        // 配置线条画笔
        mRadarPaint = new Paint();
        mRadarPaint.setColor(Color.RED);
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setStrokeWidth(5);
        mRadarPaint.setAntiAlias(true);
        // 配置数据区域画笔
        mRadarDataPaint = new Paint();
        mRadarDataPaint.setColor(Color.BLUE);
    }

    /**
     * 初始化圆形头像的配置
     */
    private void initCircleImg() {
        // 禁用硬件加速
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mCircleBitmap = getBitmap();
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLACK);
        mmCirclePath = new Path();
        // 设置头像大小
        float w = mCircleBitmap.getWidth();
        float h = mCircleBitmap.getHeight();
        // 添加头像路径
        mmCirclePath.addCircle(w / 2 + 100,
                h / 2 +100, w / 2, Path.Direction.CW);
    }

    private Bitmap getBitmap() {
        return Util.drawableToBitmap(getContext().getResources().getDrawable(R.drawable.haruhi));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawCircle(canvas);
//        drawBg(canvas);
//        drawLineAndPoint(canvas);
//        drawRect(canvas);
//        drawRoundRect(canvas);
//        drawPath(canvas);
//        drawRadar(canvas);
//        drawText(canvas);
//        drawRegion(canvas);
        // 画布练习
//        drawCanvas(canvas);
        // 绘制圆形头像
        drawCircleImage(canvas);
    }

    /*****   这一部分主要是说明Paint   *****/

    /**
     * 绘制各种圆形
     *
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {
        Paint paint = new Paint();
        float radius = 50;
        // 普通的圆
        paint.setColor(Color.RED);
        // 设置抗锯齿
        paint.setAntiAlias(true);
        canvas.drawCircle(100, 100, radius, paint);

        // 描边
        paint.setStyle(Paint.Style.STROKE);
        // 设置画笔宽度，仅在设置STROKE 和 FILL_AND_STROKE 起作用
        paint.setStrokeWidth(20);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(220, 100, radius, paint);

        // 填充
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(350, 100, radius, paint);

        // 描边且填充
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(500, 100, radius, paint);

    }

    /*****   这一部分是画布的练习    *****/

    /**
     * 绘制背景
     *
     * @param canvas
     */
    private void drawBg(Canvas canvas) {
        // 设置画布背景要在其他图形绘制前设置，否则设置好的背景色会覆盖原有的图形。
        canvas.drawColor(Color.DKGRAY);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(100, 100, 100, paint);
    }


    /**
     * 绘制直线 & 点
     * 直线、点的宽度或者大小取决于 Paint 的 setStrokeWith(width) 中传入的宽度。
     * 绘制直线需要注意的是，只有当 Style 是 STROKE、FILL_AND_STROKE 时绘制才有效。
     *
     * @param canvas
     */
    private void drawLineAndPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.RED);
        canvas.drawLine(50, 50, 200, 200, paint);

        paint.setStrokeWidth(20);
        canvas.drawPoint(300, 300, paint);
    }

    /**
     * 绘制矩形
     * Android 提供了 Rect 和 RectF 类用于存储矩形数据结构
     * Rect 和 RectF 区别就是前者存储的是int 类型，后者是float类型
     * <p>
     * 补充：
     * drawRect 和  drawPoint 都可以绘制矩形，他们的区别是：
     * 1、形状：
     * drawPoint() 只能指定矩形中心的坐标，只能画出正方形。
     * drawRect()  需要指定矩形左上和右下两个点的位置，可以是长方形或者正方形。
     * <p>
     * 2、样式：
     * drawPoint() 只能是填充样式。
     * drawRect()  可以自己选择样式，可以是描边也可以是填充。
     * <p>
     * 结论：绘制矩形首选drawRect。
     *
     * @param canvas
     */
    private void drawRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);
        // 描边矩形
        paint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect(100, 100, 200, 200);
        canvas.drawRect(rect, paint);

        // 填充矩形
        paint.setStyle(Paint.Style.FILL);
        RectF rectF = new RectF(300.0f, 300.0f, 500.0f, 500.0f);
        canvas.drawRect(rectF, paint);
    }

    /**
     * 绘制圆角矩形
     * 圆角矩形不支持，Rect，仅支持RectF
     *
     * @param canvas
     */
    private void drawRoundRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(20);
        // 圆角半径
        float radius = 30.0f;

        // 描边圆角矩形
        paint.setStyle(Paint.Style.STROKE);
        RectF rect = new RectF(100, 100, 200, 200);
        canvas.drawRoundRect(rect, radius, radius, paint);

        // 填充圆角矩形
        paint.setStyle(Paint.Style.FILL);
        RectF rectF1 = new RectF(300.0f, 300.0f, 500.0f, 500.0f);
        canvas.drawRoundRect(rectF1, radius, radius, paint);
    }

    /**
     * 绘制路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {
        /**  绘制直线路径部分   **/
//        drawLinePath(canvas);
        /**  绘制弧线路径部分   **/
//       drawArcPath(canvas);

        /**  绘制复杂路径   **/
//        drawAddPath(canvas);
        /**  填充路径  **/
        drawFillPath(canvas);
    }

    /**
     * 绘制直线路径
     *
     * @param canvas
     */
    private void drawLinePath(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);

        // 创建路径
        Path path = new Path();
        // 第一条线的起点
        path.moveTo(100, 100);
        // 第一条线的终点，第二条线的起点
        path.lineTo(400, 300);
        // 第二条线的终点
        path.lineTo(100, 300);
        // 绘制第三条线，形成闭环。如果连续画了几条直线，但没有形成闭环，
        // 调用 close() 函数会把路径首尾连接起来形成闭环，相当于是帮我们画多一条直线，
        // 如果只画了一条直线，那 close() 方法是不会起作用的。
        path.close();
        // 绘制直线路径
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制弧线路径
     * Path 的 arcTo() 方法可用于绘制弧线，弧线在这里指的是椭圆上截取的一部分。
     * 在这里我们要注意的是，弧线默认是填充的，更准确的来说 drawArc() 方法是切出椭圆中的一块,
     * 如果我们只想要一条线的话，就要自己设置描边样式和描边宽度（例如圆形进度）
     * startAngle  是的起始角度，位置在圆形的右边。
     * sweepAngle  表示扫描角度，值为负数表示逆时针，值为正数表示顺时针。
     * forceMoveTo 重置起点，把绘制弧线的起点从 moveTo 的坐标重置到 startAngle 的位置。
     *
     * @param canvas
     */
    private void drawArcPath(Canvas canvas) {
        // 弧线画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        // 背景画笔
        Paint ovalPaint = new Paint();
        ovalPaint.setColor(Color.YELLOW);

        Path path = new Path();
        RectF rectF = new RectF(300, 100, 500, 300);
        // 指定角度
        path.arcTo(rectF, 0, -90);
        // 绘制背景
        canvas.drawOval(rectF, ovalPaint);
        // 绘制弧线
        canvas.drawPath(path, paint);
    }

    /**
     * 绘制复杂路径
     * 这里使用Path里addXX 系列方法
     *
     * @param canvas
     */
    private void drawAddPath(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        // 1、先绘制一条直线
        path.moveTo(100, 100);
        path.lineTo(200, 200);

        // 2、绘制一个弧度
        RectF rectF = new RectF(110, 90, 190, 120);
        path.addArc(rectF, 0, 180);

        // 绘制路径
        canvas.drawPath(path, paint);

    }

    /**
     * 填充路径
     *
     * @param canvas
     */
    private void drawFillPath(Canvas canvas) {
        // 1、填充路径内部区域
//        drawFillPathImpl(canvas, Path.FillType.WINDING, 100, 100);
        // 2、填充路径外部区域
//        drawFillPathImpl(canvas, Path.FillType.INVERSE_WINDING, 100, 500);
        // 3、填充路径相交的区域
//        drawFillPathImpl(canvas, Path.FillType.EVEN_ODD, 450, 100);
        // 4、填充路径相交和外部区域
//        drawFillPathImpl(canvas, Path.FillType.INVERSE_EVEN_ODD, 450, 500);
    }

    /**
     * 填充路径的具体实现
     * 这里使用矩形 + 圆形
     * Direction.CW 表示顺时针
     *
     * @param canvas
     * @param fillType 填充类型
     * @param left     左边的间距
     * @param top      顶部的间距
     */
    private void drawFillPathImpl(Canvas canvas, Path.FillType fillType, int left, int top) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        Path path = new Path();
        // 1、先绘制一个矩形，顺时针
        RectF rectF = new RectF(left, top, left + 150, top + 150);
        path.addRect(rectF, Path.Direction.CW);

        // 2、绘制一个圆形，顺时针
        path.addCircle(left + 150, top + 150, 100, Path.Direction.CW);

        // 设置填充类型
        path.setFillType(fillType);
        // 绘制路径
        canvas.drawPath(path, paint);

    }

    /**
     * 绘制雷达图
     *
     * @param canvas
     */
    private void drawRadar(Canvas canvas) {
        drawRadarPolygon(canvas);
        drawRadarLine(canvas);
        drawRadarRegion(canvas);
    }

    /**
     * 绘制雷达蜘蛛网格
     *
     * @param canvas
     */
    private void drawRadarPolygon(Canvas canvas) {
        Path path = new Path();
        // 每个蛛丝之间的间距
        float gap = mRadarRadius / (mRadarCount - 1);
        CofferLog.D(TAG, "gap : " + gap);
        // 绘制5个六边形，即绘制5次，中心不用绘制
        for (int i = 1; i < mRadarCount; i++) {
            // 当前半径
            float curR = gap * i;
            // 重置路径
            path.reset();

            // 从最里层的开始绘制，绘制一个六边形需要绘制6次直线
            for (int j = 0; j < mRadarCount; j++) {
                if (j == 0) {
                    // 这里是绘制的起点
                    path.moveTo(mRadarCenterX + curR, mRadarCenterY);
                } else {
                    // 顺时针依次绘制剩下的6条线段，这里需要用到三角函数，计算剩下的5个点的坐标
                    float x = (float) (mRadarCenterX + curR * Math.cos(mRadarAngle * j));
                    float y = (float) (mRadarCenterY + curR * Math.sin(mRadarAngle * j));
                    path.lineTo(x, y);
                }
            }
            // 连接最后一条线，形成一个环状
            path.close();
            canvas.drawPath(path, mRadarPaint);
        }
    }

    /**
     * 绘制雷网格中线
     *
     * @param canvas
     */
    private void drawRadarLine(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mRadarCount; i++) {
            path.reset();
            path.moveTo(mRadarCenterX, mRadarCenterY);
            float x = (float) (mRadarCenterX + mRadarRadius * Math.cos(mRadarAngle * i));
            float y = (float) (mRadarCenterY + mRadarRadius * Math.sin(mRadarAngle * i));
            path.lineTo(x, y);
            canvas.drawPath(path, mRadarPaint);
        }
    }

    /**
     * 绘制雷达数据区域
     *
     * @param canvas
     */
    private void drawRadarRegion(Canvas canvas) {
        Path path = new Path();
        for (int i = 0; i < mRadarCount; i++) {
            // 计算数据值的比重
            double percent = mRadarData[i] / mRadarMaxValue;
            // 计算每一个点的坐标, 这个和计算绘制蜘蛛网的点坐标差不多，只是需要加上占比值
            float x = (float) (mRadarCenterX + mRadarRadius * Math.cos(mRadarAngle * i) * percent);
            float y = (float) (mRadarCenterY + mRadarRadius * Math.sin(mRadarAngle * i) * percent);
            if (i == 0) {
                path.moveTo(x, mRadarCenterY);
            } else {
                path.lineTo(x, y);
            }
            // 绘制数据所在位置的小圆点
            mRadarDataPaint.setAlpha(255);
            canvas.drawCircle(x, y, 10, mRadarDataPaint);
        }
        // 绘制填充区域
        mRadarDataPaint.setStrokeWidth(10);
        mRadarDataPaint.setAlpha(127);
        mRadarDataPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mRadarDataPaint);
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        // 默认填充
//        drawStyleText(canvas, null, null, 100, false);
        // 描边样式
//        drawStyleText(canvas, Paint.Style.STROKE, Paint.Align.CENTER, 200, true);
        // 描边且填充
//        drawStyleText(canvas, Paint.Style.FILL_AND_STROKE, Paint.Align.RIGHT, 300, false);
        // 根据路径绘制文字
//        drawPathText(canvas);
        // 在文字下绘制下划线，这里考察文字的测量
//        drawTextLine(canvas);
        // 在文字上绘制一个矩形，这里考察文字的测量
//        drawTextRect(canvas);
    }

    /**
     * 绘制文字样式、对齐、下划线
     *
     * @param canvas
     * @param style  文字样式
     * @param align  文字对齐方式
     * @param line   是否需要下划线
     * @param top
     */
    private void drawStyleText(Canvas canvas, Paint.Style style, Paint.Align align,
                               float top, boolean line) {
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setTextSize(Util.spToPixel(getContext(), 30));
        // 设置文字样式
        if (style != null) {
            paint.setStyle(style);
        }
        if (align != null) {
            paint.setTextAlign(align);
        }
        if (line) {
            // 设置下划线
            paint.setUnderlineText(true);
        } else {
            // 设置删除线，很像价格
            paint.setStrikeThruText(true);
        }
        // 绘制文字
        canvas.drawText("哈哈", 300, top, paint);
    }

    /**
     * 根据路径绘制文字
     *
     * @param canvas
     */
    private void drawPathText(Canvas canvas) {
        // 无偏移
        drawCirclePathText(canvas, 100, 200, 0, 0);
        // 正向水平偏移
        drawCirclePathText(canvas, 350, 200, 20, 0);
        // 反向水平偏移
        drawCirclePathText(canvas, 600, 200, -20, 0);
        // 正向垂直偏移
        drawCirclePathText(canvas, 200, 500, 0, 30);
        // 反向垂直偏移
        drawCirclePathText(canvas, 500, 500, 0, -20);


    }

    /**
     * 在圆形上绘制文字
     *
     * @param canvas
     * @param centerX          圆心的横坐标
     * @param centerY          圆心的纵坐标
     * @param horizontalOffset 水平方向上的偏移值
     * @param verticalOffset   垂直方向上的偏移值
     */
    private void drawCirclePathText(Canvas canvas, int centerX, int centerY,
                                    int horizontalOffset, int verticalOffset) {
        Path path = new Path();

        Paint paint = new Paint();
        paint.setTextSize(Util.spToPixel(getContext(), 12));
        paint.setColor(Color.BLUE);
        paint.setAlpha(127);
        paint.setAntiAlias(true);

        path.addCircle(centerX, centerY, 80, Path.Direction.CW);
        canvas.drawPath(path, paint);

        // 在路径上绘制文字
        canvas.drawTextOnPath("哈哈", path, horizontalOffset, verticalOffset, paint);

    }

    /**
     * 在文字下绘制下划线，这里考察文字的测量
     *
     * @param canvas
     */
    private void drawTextLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(Util.spToPixel(getContext(), 16));
        canvas.drawText("哈哈哈", mRadarCenterX, mRadarCenterY, paint);

        int textWidth = (int) paint.measureText("哈哈哈");
        CofferLog.D(TAG, "textWidth : " + textWidth);
        // 绘制下划线
        Paint paintLine = new Paint();
        paintLine.setColor(Color.BLUE);
        paintLine.setStrokeWidth(10);
        paintLine.setStyle(Paint.Style.STROKE);
        canvas.drawLine(mRadarCenterX, mRadarCenterY + 40, mRadarCenterX + textWidth,
                mRadarCenterY + 40, paintLine);
    }

    /**
     * 在文字上绘制一个矩形，这里考察文字的测量
     *
     * @param canvas
     */
    private void drawTextRect(Canvas canvas) {
        String content = "哈哈哈";
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(Util.spToPixel(getContext(), 16));
        canvas.drawText(content, mRadarCenterX, mRadarCenterY, paint);

        // 文字区域
        Rect rect = new Rect();
        // 使用getTextBounds 获取文字的大小，这里需要转换成对应的位置坐标
        paint.getTextBounds(content, 0, content.length(), rect);
        rect.left = mRadarCenterX - rect.left;
        rect.top = mRadarCenterY - rect.bottom - rect.top;
        rect.right = mRadarCenterX + rect.right + 10;
        rect.bottom = mRadarCenterY + rect.bottom + 10;
        // 绘制矩形
        Paint paintRect = new Paint();
        paintRect.setColor(Color.BLUE);
        paintRect.setStrokeWidth(5);
        paintRect.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, paintRect);
    }

    /**
     * 绘制区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        // 创建一个正方形
//        Rect rect = new Rect(600,100,700,150);
//        // 创建一个区域
//        Region region = new Region(600,100,650,300);
//        // 合并区域
//        region.union(rect);
//        drawRegionImpl(canvas,region,paint);

        // 区域裁剪
//        drawRegionClip(canvas);
        // 区域的集合运算
        drawRegionOp(canvas);
    }

    /**
     * 区域裁剪
     *
     * @param canvas
     */
    private void drawRegionClip(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);

        // 椭圆路径
        Path ovalPath = new Path();
        // 设定椭圆范围
        RectF rectF = new RectF(400, 50, 550, 500);
        // 添加椭圆，这里使用逆时针
        ovalPath.addOval(rectF, Path.Direction.CCW);
        // 原始区域
        Region originRegion = new Region();
        // 设置裁剪区域
        Region clip = new Region(400, 50, 550, 200);
        // 设置裁剪路径，setPath 方法是将两个区域相交的部分保留
        originRegion.setPath(ovalPath, clip);
        // 绘制区域
        drawRegionImpl(canvas, originRegion, paint);
    }

    /**
     * 区域集合运算，即两个区域的交集、并集、异或、替换等
     *
     * @param canvas
     */
    private void drawRegionOp(Canvas canvas) {
        // 补集
        drawRegionOpImpl(canvas, Region.Op.DIFFERENCE, 100, 300);
        // 反转补集
        drawRegionOpImpl(canvas, Region.Op.REVERSE_DIFFERENCE, 300, 300);
        // 交集
        drawRegionOpImpl(canvas, Region.Op.INTERSECT, 500, 300);
        // 并集
        drawRegionOpImpl(canvas, Region.Op.UNION, 100, 600);
        // 异或
        drawRegionOpImpl(canvas, Region.Op.XOR, 300, 600);
        // 替换原有区域
        drawRegionOpImpl(canvas, Region.Op.REPLACE, 500, 600);
    }

    /**
     * 区域集合运算实现
     *
     * @param canvas
     * @param op     运算模式
     * @param left
     * @param top
     */
    private void drawRegionOpImpl(Canvas canvas, Region.Op op, int left, int top) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        // 绘制黄色的矩形
        Rect yellowRect = new Rect(left, top, left + 50, top + 150);
        paint.setColor(Color.BLUE);
        canvas.drawRect(yellowRect, paint);

        // 绘制绿色的矩形
        Rect greenRect = new Rect(left - 50, top + 50, left + 100, top + 100);
        paint.setColor(Color.GREEN);
        canvas.drawRect(greenRect, paint);

        // 进行区域集合运算
        Region yellowRegion = new Region(yellowRect);
        Region greenRegion = new Region(greenRect);
        yellowRegion.op(greenRegion, op);

        // 绘制运算结果
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        drawRegionImpl(canvas, yellowRegion, paint);
    }

    /**
     * 绘制区域的实现
     *
     * @param canvas
     * @param region
     * @param paint
     */
    private void drawRegionImpl(Canvas canvas, Region region, Paint paint) {
        // 绘制合并后的区域
        RegionIterator iterator = new RegionIterator(region);
        Rect r = new Rect();
        // 遍历矩形中所有的子矩形区域
        while (iterator.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

    /**
     * 画布练习
     * <p>
     * 画布的原始状态是以左上角为圆点，向右是 X 轴正方向，向下是 Y 轴正方向。
     * 由于画布的左上角是坐标轴的原点(0, 0)，所以平移画布后，坐标系也会被平移。
     *
     * @param canvas
     */
    private void drawCanvas(Canvas canvas) {
        // 画布平移
//        drawCanvasTranslate(canvas);
        // 画布的裁剪
//        drawCanvasClip(canvas);
        // 画布的保存和恢复
        drawCanvasSaveRestore(canvas);
    }

    /**
     * 画布平移
     * <p>
     * 注意：
     * 1、生成新的图层：
     * 每次调用绘制方法 drawXXX 时，都会产生一个新的 Canvas 透明图层。
     * 2、操作不可逆：
     * 调用了绘制方法前，平移和旋转等函数对 Canvas 进行了操作，那么这个操作是不可逆的，
     * 每次产生的画布的最新位置都是这些操作后的位置。
     * 3、超出不显示：
     * 在 Canvas 图层与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
     *
     * @param canvas
     */
    private void drawCanvasTranslate(Canvas canvas) {
        // 绘制一个绿色矩形
        drawRectImpl(canvas, Color.GREEN);
        // 平移画布。当我们绘制红色矩形时，会产生另一个新的 Canvas 透明图层，此时画布坐标改变了
        // 由于 Canvas 已经平移了 350 像素，所以画图时是以新原点来产生视图的，然后再合成到屏幕上。
        canvas.translate(350, 350);
        // 绘制一个红色矩形
        drawRectImpl(canvas, Color.RED);

    }

    /**
     * 绘制一个矩形的实现
     *
     * @param canvas
     * @param color
     */
    private void drawRectImpl(Canvas canvas, int color) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(color);
        Rect rect = new Rect(50, 50, 300, 300);
        canvas.drawRect(rect, paint);
    }

    /**
     * 画布的裁剪
     *
     * @param canvas
     */
    private void drawCanvasClip(Canvas canvas) {
        canvas.drawColor(Color.RED);
        // 设置裁剪范围
        Rect rect = new Rect(100, 100, 200, 200);
        // 裁剪
        canvas.clipRect(rect);
        // 裁剪后
        canvas.drawColor(Color.GREEN);
    }

    /**
     * 画布的保存和恢复
     * <p>
     * 由于画布的操作是不可逆的，画布状态改变后又影响了后面的绘制效果。
     * 因此，画布提供了保存和恢复功能，这两个功能对应的方法是 save() 和 restore()，每调用一次 save() 就会把当前画布状态保存到栈中。
     *
     * @param canvas
     */
    private void drawCanvasSaveRestore(Canvas canvas) {
        // 1、绘制红色全屏
        canvas.drawColor(Color.RED);
        // 记录当前图层的位置
        int canvasState1 = canvas.save();
        // 2、绘制绿色700
        canvas.clipRect(new RectF(100, 100, 700, 700));
        canvas.drawColor(Color.GREEN);
        int canvasState2 = canvas.save();
        // 3、绘制蓝色400
        canvas.clipRect(new RectF(200, 200, 600, 600));
        canvas.drawColor(Color.BLUE);
        int canvasState3 = canvas.save();
        // 4、绘制黑色200
        canvas.clipRect(new RectF(300, 300, 500, 500));
        canvas.drawColor(Color.BLACK);
        int canvasState4 = canvas.save();
        // 5、绘制白色100
        canvas.clipRect(new RectF(350, 350, 450, 450));
        canvas.drawColor(Color.WHITE);
        int canvasState5 = canvas.save();

        // 两次出栈，相当于白色、黑色失效,使用黄色覆盖到黑色失效区域
//        canvas.restore();
//        canvas.restore();
//        canvas.drawColor(Color.YELLOW);

        // 指定到蓝色出栈，相当于执行了3次canvas.restore
        canvas.restoreToCount(canvasState3);
        canvas.drawColor(Color.YELLOW);
    }

    /**
     * 绘制圆形头像
     *
     * @param canvas
     */
    private void drawCircleImage(Canvas canvas) {
        // 裁剪, clipPath 是有锯齿的
//        canvas.clipPath(mmCirclePath);
//        canvas.drawBitmap(mCircleBitmap,100,100,mCirclePaint);

        // 可以用 PorterDuffXfermode 来实现无锯齿的圆形图片
        int bitmapWidth = mCircleBitmap.getWidth();
        // 1、设置源范围
        Rect src = new Rect(0,0,bitmapWidth,bitmapWidth);
        // 2、设置目标范围
        Rect dst = new Rect(0,0,bitmapWidth,bitmapWidth);
        // 3、绘制一个空白图片
        Bitmap outBitmap = Bitmap.createBitmap(bitmapWidth,bitmapWidth,Bitmap.Config.RGB_565);
        // 4、设置空画布
        Canvas canvas1 = new Canvas(outBitmap);
        Paint xferPaint = new Paint();
        // 5、绘制圆形
        int radius = bitmapWidth /2;
        canvas1.drawRoundRect(new RectF(dst),radius,radius,xferPaint);
        // 6、设置模式
        xferPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 7、进行混合
        canvas1.drawBitmap(mCircleBitmap,src,dst,xferPaint);
        // 8、绘制结果
        canvas.drawBitmap(outBitmap,0,0,mCirclePaint);
        // 9、清除模式
        xferPaint.setXfermode(null);

    }
}
