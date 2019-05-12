package coffer.animdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_main);
        textView = findViewById(R.id.tv1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animDemo1();
            }
        });

    }

    /**
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
        ValueAnimator animator = ValueAnimator.ofInt(textView.getLayoutParams().width,500);

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
                textView.getLayoutParams().width = currentValue;

                // 步骤5：刷新视图，即重新绘制，从而实现动画效果
                textView.requestLayout();
            }
        });
        animator.start();
    }

    /**
     * 一个圆从一个点 移动到 另外一个点
     */
    private void animDemo2(){

    }
}
