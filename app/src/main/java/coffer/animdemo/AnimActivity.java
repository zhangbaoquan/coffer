package coffer.animdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.j2objc.annotations.Property;

import coffer.androidjatpack.R;


/**
 * @author：张宝全
 * @date：2019-05-04
 * @Description： 动画
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class AnimActivity extends AppCompatActivity {

    private static final String TAG = "anim_demo";
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_main);

        initView();

    }

    private void initView(){
        // 改变View的宽度大小
        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo1();
            }
        });

        // View的透明度
        tv2 = findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo2();
            }
        });

        // 组合动画
        tv3 = findViewById(R.id.tv3);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo3();
            }
        });

        // ViewPropertyAnimator
        tv4 = findViewById(R.id.tv4);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo4();
            }
        });

        // 组合动画2
        tv5 = findViewById(R.id.tv5);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo5();
            }
        });

        // 气泡动画
        tv6 = findViewById(R.id.tv6);
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo6();
            }
        });
    }

    /**
     * 这个是ValueAnimator的Demo
     * 将TextView的宽度从150变成500的动画
     */
    private void animDemo1(){
        // ofInt（）作用有两个
        // 1. 创建动画实例
        // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
        // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
        // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值
        // 关于自定义插值器我将在下节进行讲解
        // 下面看看ofInt()的源码分析 ->>关注1
        ValueAnimator animator = ValueAnimator.ofInt(tv1.getLayoutParams().width,500);

        // 步骤2：设置动画的播放各种属性
        // 设置动画运行的时长
        animator.setDuration(3000);

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
                Log.e(TAG,"currentValue : "+currentValue);

                // 步骤4：将改变后的值赋给对象的属性值
                tv1.getLayoutParams().width = currentValue;

                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                tv1.requestLayout();
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
    private void animDemo2(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv2,"alpha",1f,0f,1f);
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
    private void animDemo3(){
        // 平移：从当前位置向右移动300，然后再回到原来的位置。这里
        float curTranslationX = tv3.getTranslationX();
        ObjectAnimator translation = ObjectAnimator.ofFloat(tv3,"translationX",curTranslationX,300,curTranslationX);
        translation.setInterpolator(new BounceInterpolator());
        translation.setDuration(3000);

        // 透明度
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tv3,"alpha",1f,0f,1f);
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
    private void animDemo5(){
        // 将平移动画、透明动画整合
        PropertyValuesHolder[] propertyValuesHolder = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("translationX",tv5.getTranslationX(),300,tv5.getTranslationX()) ,
                PropertyValuesHolder.ofFloat("alpha",1f,0f,1f)
        };
        ObjectAnimator animator = ObjectAnimator
                .ofPropertyValuesHolder(tv5,propertyValuesHolder)
                .setDuration(5000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator);
        animatorSet.start();
    }

    /**
     * 使用ViewPropertyAnimator的动画效果，这个是属性动画的简写
     */
    private void animDemo4(){
        final float x = tv4.getX();
        final float y = tv4.getY();
        tv4.animate().alpha(0f);
        tv4.animate().alpha(0f).setDuration(5000).setInterpolator(new BounceInterpolator());
        tv4.animate().alpha(0f).x(500).y(500);
        tv4.animate().setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Log.d(TAG,"animDemo4动画结束了");
                        // 动画结束时，将位置和状态还原回去
                        tv4.animate().x(x).y(y).alpha(1f);
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
     */
    private void animDemo6(){
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
        ObjectAnimator step1 = ObjectAnimator.ofPropertyValuesHolder(tv6,enlarge);
        step1.setDuration(400);
        ObjectAnimator step2 = ObjectAnimator.ofPropertyValuesHolder(tv6,normal);
        step2.setDuration(4000);
        ObjectAnimator step3 = ObjectAnimator.ofPropertyValuesHolder(tv6,shrink);
        step3.setDuration(600);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(step1,step2,step3);
        animatorSet.start();

    }

    /**
     * 一个圆从一个点 移动到 另外一个点
     */
    private void animDemo7(){

    }
}
