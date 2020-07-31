package coffer.androidDemo.animdemo;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/7/27
 * @Description  补间动画
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class TweenAnimActivity extends BaseDefaultActivity {

    private static final String TAG = "AnimActivity2_tag";


    @Override
    public void initView() {
        setContentView(R.layout.activity_tween_anim_main);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translateAnim(v);
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleAnim(v);
            }
        });
        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateAnim(v);
            }
        });
        findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listAnim(v);
            }
        });

    }

    @Override
    public void initData() {

    }

    /**
     * 平移动画
     */
    private void translateAnim(View view){
        // 方式一、使用XML的方式，向右下角移动500，然后返回
//        Animation translate = AnimationUtils.loadAnimation(this,R.anim.tween_translate);
//        view.startAnimation(translate);

        // 使用Java的方式
        Animation translation = new TranslateAnimation(0,100,0,
                100);
        translation.setFillAfter(false);
        translation.setDuration(3000);
        view.startAnimation(translation);
    }

    /**
     * 缩放动画
     */
    private void scaleAnim(View view){
        // 使用java 的方式
        // 设置缩放的位置是在相对于View自身的左下角（0，1），若设置中心位置则（0.5f,0.5f）
        Animation scale = new ScaleAnimation(0,2,0,2,Animation.RELATIVE_TO_SELF,
                0,Animation.RELATIVE_TO_SELF,1);
        scale.setFillAfter(false);
        scale.setDuration(3000);
        view.startAnimation(scale);
    }

    /**
     * 旋转动画
     */
    private void rotateAnim(View view){
        // 使用java 的方式
        // 设置旋转中心的位置是在相对于View自身的左下角（0，1），若设置中心位置则（0.5f,0.5f）
        Animation rotate = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotate.setFillAfter(false);
        rotate.setDuration(3000);
        view.startAnimation(rotate);
    }

    /**
     * 动画集合
     */
    private void listAnim(View view){
        // 结合：平移、缩放、旋转、透明动画

        // 1、设置组合动画对象为true
        AnimationSet animationSet = new AnimationSet(true);
        // 2、配置基本属性
        animationSet.setDuration(3000);
        animationSet.setFillAfter(false);

        Animation translation = new TranslateAnimation(0,100,0,
                100);
        Animation scale = new ScaleAnimation(0,2,0,2,Animation.RELATIVE_TO_SELF,
                0,Animation.RELATIVE_TO_SELF,1);
        Animation rotate = new RotateAnimation(0,360,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        Animation alpha = new AlphaAnimation(0,1);

        // 按照顺序添加动画
        animationSet.addAnimation(translation);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(rotate);
        animationSet.addAnimation(alpha);
        view.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
