package coffer.animDemo;

/**
 * @author：张宝全
 * @date：2019-05-03
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * 签到按钮，在有需要时可以显示呼吸动画，即放大-缩小-放大-缩小-放大回原大小
 * */
public class ScaleAnimatedTextView extends AppCompatTextView {

    private Context context;

    /** 动画阶段1: 0-166ms中scale，从1.0到1.15*/
    private PropertyValuesHolder[] animationStep1Properties;
    private ObjectAnimator animationStep1;

    /** 动画阶段2: 166ms-432ms中scale，从1.15到0.94*/
    private PropertyValuesHolder[] animationStep2Properties;
    private ObjectAnimator animationStep2;

    /** 动画阶段3: 432ms-698ms中scale，从0.94到1.05*/
    private PropertyValuesHolder[] animationStep3Properties;
    private ObjectAnimator animationStep3;

    /** 动画阶段4: 698ms-964ms中scale，从1.05到0.98*/
    private PropertyValuesHolder[] animationStep4Properties;
    private ObjectAnimator animationStep4;

    /** 动画阶段5: 964ms-1190ms中scale，从0.98到1.0*/
    private PropertyValuesHolder[] animationStep5Properties;
    private ObjectAnimator animationStep5;

    private AnimatorSet animation;

    public ScaleAnimatedTextView(Context context){
        super(context);
        init(context);
    }

    public ScaleAnimatedTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    public ScaleAnimatedTextView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        animationStep1Properties = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.15f), PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.15f)
        };
        animationStep2Properties = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX", 1.15f, 0.94f), PropertyValuesHolder.ofFloat("scaleY", 1.15f, 0.94f)
        };
        animationStep3Properties = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX", 0.94f, 1.05f), PropertyValuesHolder.ofFloat("scaleY", 0.94f, 1.05f)
        };
        animationStep4Properties = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX", 1.05f, 0.98f), PropertyValuesHolder.ofFloat("scaleY", 1.05f, 0.98f)
        };
        animationStep5Properties = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX", 0.98f, 1.0f), PropertyValuesHolder.ofFloat("scaleY", 0.98f, 1.0f)
        };

        animationStep1 = ObjectAnimator.ofPropertyValuesHolder(this, animationStep1Properties).setDuration(166);
        animationStep2 = ObjectAnimator.ofPropertyValuesHolder(this, animationStep2Properties).setDuration(266);
        animationStep3 = ObjectAnimator.ofPropertyValuesHolder(this, animationStep3Properties).setDuration(266);
        animationStep4 = ObjectAnimator.ofPropertyValuesHolder(this, animationStep4Properties).setDuration(266);
        animationStep5 = ObjectAnimator.ofPropertyValuesHolder(this, animationStep5Properties).setDuration(266);

        animation = new AnimatorSet();
    }

    public void startAnimation(){
        if(animation.isStarted() || !isShown() || getParent() == null){
            return;
        }
        animation.playSequentially(animationStep1, animationStep2, animationStep3, animationStep4, animationStep5);
        animation.start();
    }
}

