package coffer.animDemo;

import android.animation.TypeEvaluator;

/**
 * @author：张宝全
 * @date：2019-05-04
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ObjectEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {

        // 参数说明
        // fraction：表示动画完成度（根据它来计算当前动画的值）
        // startValue、endValue：动画的初始值和结束值

        // 写入对象动画过渡的逻辑

        // 返回对象动画过渡的逻辑计算后的值
        return null;
    }
}
