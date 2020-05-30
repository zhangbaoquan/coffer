package coffer.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author：张宝全
 * @date：2020-03-25
 * @Description： 动画辅助类
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class AnimUtils {

    public static final String TAG = "AnimationUtils_tag";

    /**
     * 这个是ValueAnimator的Demo
     * 将TextView的宽度从150变成500的动画
     */
    public static void animDemo1(final View view){
        // ofInt（）作用有两个
        // 1. 创建动画实例
        // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
        // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
        // 关于自定义插值器我将在下节进行讲解
        // 下面看看ofInt()的源码分析 ->>关注1
        ValueAnimator animator = ValueAnimator.ofInt(view.getLayoutParams().height,0);

        // 步骤2：设置动画的播放各种属性
        // 设置动画运行的时长
        animator.setDuration(500);

        // 设置动画延迟播放时间
        animator.setStartDelay(500);

        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复
        animator.setRepeatCount(0);

        // 设置重复播放动画模式
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        // 步骤3：将改变的值手动赋值给对象的属性值：通过动画的更新监听器
        // 设置 值的更新监听器
        // 即：值每次改变、变化一次,该方法就会被调用一次
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 获得改变后的值
                int currentValue = (int) animation.getAnimatedValue();
                Log.i(TAG,"currentValue : "+currentValue);

                // 步骤4：将改变后的值赋给对象的属性值
                view.getLayoutParams().height = currentValue;

                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                view.requestLayout();
            }
        });
        animator.start();
    }

    /**
     * 这个是ObjectAnimator的Demo
     * alpha
     * rotation
     * translationX、translationY
     * scaleX、scaleY
     * 设置View的透明度
     */
    public static  void animDemo2(View view){
        ObjectAnimator animator = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
        // 表示的是:
        // 动画作用对象是mButton
        // 动画作用的对象的属性是透明度alpha
        // 动画效果是:常规 - 全透明 - 常规
        animator.setDuration(5000);
        animator.start();
    }

    /**
     * 组合动画
     * 透明、缩放、平移、旋转
     * 这里仅实现平移和透明
     */
    public static  void animDemo3(View view){
        // 平移：从当前位置向右移动300，然后再回到原来的位置。这里
        float curTranslationX = view.getTranslationX();
        ObjectAnimator translation = ObjectAnimator.ofFloat(view,"translationX",curTranslationX,300,curTranslationX);
        translation.setInterpolator(new BounceInterpolator());
        translation.setDuration(3000);

        // 透明度
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view,"alpha",1f,0f,1f);
        alpha.setDuration(2000);

        // 创建组合动画对象1，这里将两个动画组合，即动画的时间是由各自的动画时间相加
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playSequentially(translation,alpha);
        animatorSet1.start();
    }

    /**
     * 组合动画2
     * 这里的使用PropertyValuesHolder，可以将任意动画效果复合
     * 例如：下面的例子就是将平移动画、透明动画融合在一起。即两个动画一起执行。
     * 注意和上面的动画的区别。
     * 组合1的效果，其实还是两个动画按照顺序执行。
     */
    public static  void animDemo5(View view){
        // 将平移动画、透明动画整合
        PropertyValuesHolder[] propertyValuesHolder = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("translationX",view.getTranslationX(),300,view.getTranslationX()) ,
                PropertyValuesHolder.ofFloat("alpha",1f,0f,1f)
        };
        ObjectAnimator animator = ObjectAnimator
                .ofPropertyValuesHolder(view,propertyValuesHolder)
                .setDuration(5000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator,animator);
        animatorSet.start();
    }

    /**
     * 使用ViewPropertyAnimator的动画效果，这个是属性动画的简写
     */
    public static  void animDemo4(final View view){
        final float x = view.getX();
        final float y = view.getY();
        view.animate().alpha(0f);
        view.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());
        view.animate().alpha(0f).x(500).y(500);
        view.animate().setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d(TAG,"animDemo4动画结束了");
                        // 动画结束时，将位置和状态还原回去
                        view.animate().x(x).y(y).alpha(1f);
                    }
                });
            }
        });
    }

    /**
     * 气泡动画（属性动画实现版）
     * 1、气泡从小到大时间为0.4秒
     * 2、气泡停留4秒
     * 3、气泡从大到消失时间为0.6秒
     * 这里使用了PropertyValuesHolder，PropertyValuesHolder这个类可以先将动画属性和值暂时的存储起来，后一起执行，在有些时候可以使用替换掉AnimatorSet
     */
    public static  void animDemo6(View view){
        // 看到这个描述
        // 1、因为需要放大缩小，所以动画属性是scale动画，
        // 2、需要放大和缩小，因此就需要View的X、Y方向上同时操作，这就需要将scaleX、scaleY 复合
        // 3、需要放大完成后再缩小，所以需要将放大、缩小两个动画组合

        // 第一阶段，将气泡从小放大
        PropertyValuesHolder[] enlarge = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX",0f,1f),
                PropertyValuesHolder.ofFloat("scaleY",0f,1f)
        };

        // 第二阶段，不变
        PropertyValuesHolder[] normal = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX",1f,1f),
                PropertyValuesHolder.ofFloat("scaleY",1f,1f)
        };

        // 第三阶段，将气泡从大变小
        PropertyValuesHolder[] shrink = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX",1f,0f),
                PropertyValuesHolder.ofFloat("scaleY",1f,0f)
        };
        ObjectAnimator step1 = ObjectAnimator.ofPropertyValuesHolder(view,enlarge);
        step1.setDuration(400);
        ObjectAnimator step2 = ObjectAnimator.ofPropertyValuesHolder(view,normal);
        step2.setDuration(4000);
        ObjectAnimator step3 = ObjectAnimator.ofPropertyValuesHolder(view,shrink);
        step3.setDuration(600);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(step1,step2,step3);
        animatorSet.start();

    }

    /**
     * 使用关键帧方式
     */
    public static void animDemo7(View view){
        //keyframe 这里使用关键帧
        Keyframe keyframe1 = Keyframe.ofFloat(0.0f,0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.25f,-30);
        Keyframe keyframe3 = Keyframe.ofFloat(0.5f,0);
        Keyframe keyframe4 = Keyframe.ofFloat(0.75f, 30);
        Keyframe keyframe5 = Keyframe.ofFloat(1.0f,0);
        // 这个动画就是实现View的左右两边上下抖动的效果，抖动有旋转的特征，所以这里使用的是"rotation"属性
        // 这里分五帧实现。
        // 第一帧是在执行的时间的内保持水平位置
        // 第二帧是在执行的时间的1/4区间内，向上旋转30度
        // 第三帧是在执行的时间的1/4区间内，回到水平位置
        // 第四帧是在执行的时间的1/4区间内，向下旋转30度
        // 第五帧是在执行的时间的1/4区间内，回到水平位置
        PropertyValuesHolder rotation = PropertyValuesHolder.ofKeyframe("rotation", keyframe1, keyframe2, keyframe3, keyframe4,keyframe5);

        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha",1.0f,0.2f,1.0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX",1.0f,0.2f,1.0f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY",1.0f,0.2f,1.0f);
        PropertyValuesHolder color = PropertyValuesHolder.ofInt("BackgroundColor", 0XFFFFFF00, 0XFF0000FF);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, alpha, scaleX, scaleY,color,rotation);
        animator.setInterpolator(new OvershootInterpolator());
        animator.setDuration(5000).start();

    }

    public static  void animDemo8(View view){
        Log.e(TAG,"getTranslationY : "+ view.getTranslationY());
        // 将平移动画、透明动画整合
        PropertyValuesHolder[] propertyValuesHolder = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("translationY",0.0f,300,0.0f) ,
                PropertyValuesHolder.ofFloat("alpha",0f,1f,0f)
        };
        ObjectAnimator animator = ObjectAnimator
                .ofPropertyValuesHolder(view,propertyValuesHolder)
                .setDuration(5000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator);
        animatorSet.start();
    }

    /**
     * 将一个 view 渐影消失
     * @param view view 实例
     * @param animTime 动画时长
     * @param endRunnable onAnimationEnd() 执行后的操作
     */
    public static void fadeOut(@NonNull final View view,
                               final int animTime,
                               @Nullable final WeakReference<Runnable> endRunnable) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        objectAnimator.setDuration(animTime);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) { }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setAlpha(1);
                if (endRunnable != null && endRunnable.get() != null) {
                    endRunnable.get().run();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) { }

            @Override
            public void onAnimationRepeat(Animator animation) { }
        });
        objectAnimator.start();
    }


    /**
     * 将一个 view 渐影消失
     * @param view view 实例
     */
    public static void fadeOut(@NonNull final View view) {
        fadeOut(view, 2000, null);
    }

}
