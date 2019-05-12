package coffer.animdemo;

import android.animation.TypeEvaluator;

/**
 * @author：张宝全
 * @date：2019-05-04
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class PointEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        // 在这里写入动画过度逻辑
        // 将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point start = (Point) startValue;
        Point end = (Point) endValue;

        // 根据fraction来计算当前动画的x和y的值
        float x = start.getX() + fraction * (end.getX() - start.getX());
        float y = start.getY() + fraction * (end.getY() - start.getY());

        // 将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point(x,y);
        return point;
    }
}
