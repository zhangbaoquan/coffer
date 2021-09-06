package coffer.androidDemo.animdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/7/31
 * @Description： 属性动画 ，包含插值器的使用
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class PropertyAnimActivity extends BaseDefaultActivity {
    private static final String TAG = "PropertyAnimActivity_tag";

    private ImageView mIv;

    @Override
    public void initView() {
        setContentView(R.layout.activity_property_anim_main);
        mIv = findViewById(R.id.iv);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeHeight();
            }
        });

        mIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PropertyAnimActivity.this,"啦啦",Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeftSide();
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorGroup();
            }
        });

        findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useFastAnim();
            }
        });

        findViewById(R.id.bt5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScale();
            }
        });
    }

    /**
     * 改变View 的高度
     */
    private void changeHeight(){
        // 方式一： 使用ValueAnimator

        // 初始高度为0，结束高度为100dp，ofFloat 内置了Float型估值器
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,
                Util.dipToPixel(this,100));
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new DecelerateAccelerateInterpolator());
        // 将属性值手动赋值给对象的属性
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue();
                // 每次值变化，手动赋值给View的属性
                mIv.getLayoutParams().height = currentValue;
                mIv.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                // 仅监听动画的开始
            }
        });
        valueAnimator.start();
    }

    /**
     * 改变View 缩放
     */
    private void changeScale(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIv,"scaleX",
                1,0.7f);
        mIv.setPivotX(0);
        mIv.setPivotY(0);
        objectAnimator.setDuration(3000);
        objectAnimator.start();
    }

    /**
     * 从左侧飞入
     */
    private void moveLeftSide(){
        // 方式二，使用
        mIv.setX(-Util.dipToPixel(this,100));
        CofferLog.D(TAG,"getTranslationX ： "+mIv.getTranslationX());
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mIv,"translationX",
                mIv.getTranslationX(),Util.dipToPixel(this,100));
        objectAnimator.setDuration(3000);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                CofferLog.D(TAG,"getTranslationX2 ： "+mIv.getTranslationX());
            }
        });
        objectAnimator.start();
    }

    /**
     * 组合动画
     */
    private void animatorGroup(){
        AnimatorSet animationSet = new AnimatorSet();

//        ObjectAnimator alpha = ObjectAnimator.ofFloat(mIv,"alpha",0,1);
//        ObjectAnimator rotate = ObjectAnimator.ofFloat(mIv,"rotation",0,360);
//        ObjectAnimator translate = ObjectAnimator.ofFloat(mIv,"translationX",mIv.getTranslationX(),500);
        //  这一句设置的意思是动画先执行透明，再执行旋转
//        animationSet.playSequentially(alpha,rotate);

        // 下面透明、旋转、平移一起执行
//        animationSet.play(alpha).with(rotate).with(translate);
        // 一起执行还有一种办法
        PropertyValuesHolder[] propertyValuesHolder = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("alpha",0f,1f),
                PropertyValuesHolder.ofFloat("rotation",0,360),
                PropertyValuesHolder.ofFloat("translationX",mIv.getTranslationX(),500)
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mIv,propertyValuesHolder);
        animationSet.play(objectAnimator);

        animationSet.setDuration(3000);
        animationSet.start();
    }

    /**
     * 使用快捷方式写动画
     */
    private void useFastAnim(){
        mIv.animate().setDuration(100).translationX(-Util.dipToPixel(this,100)).start();
    }


    @Override
    public void initData() {

    }
}
