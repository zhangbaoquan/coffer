package coffer.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import coffer.androidjatpack.R;
import coffer.util.Util;

/**
 * author      : coffer
 * date        : 8/26/21
 * description : 实现交互类似于{@link com.google.android.material.textfield.TextInputLayout}的效果，
 *               TextInputLayout 存在的问题是对外暴露修改的API太少，留给开发者自定义扩展的场景也少，
 *               例如默认提示文案的文字大小、颜色，选中后的颜色，动画的时间等。
 * Reviewer    :
 */
public class CofferTextInputLayout extends FrameLayout {

    private static final String TAG = "C_INPUT";

    /**
     * 自定义焦点发生改变的回调
     */
    private OnFocusChangeListener mOnFocusChangeListener;
    /**
     * 自定义输入框里的内容状态监听
     */
    private TextWatcher mTextWatcher;
    /**
     * 提示文案默认颜色（在输入框里）
     */
    private int mHintTextDefaultColor;
    /**
     * 提示文案真正提示状态的颜色(在输入框外)
     */
    private int mHintTextTipColor;
    /**
     * 是否使用清空内容
     */
    private boolean mUseClean;
    /**
     * 当前EditText 是否有焦点
     */
    private boolean mHasFocus = false;

    private EditText mEditText;
    private TextView mTvTip;
    private ImageView mIvClose;
    private ImageView mIvFun;

    public CofferTextInputLayout(Context context) {
        this(context,null);
    }

    public CofferTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CofferTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.text_input_edit_layout, this);
        mEditText = findViewById(R.id.edit);
        mTvTip = findViewById(R.id.tip);
        mIvClose = findViewById(R.id.close);
        mIvFun = findViewById(R.id.fun);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CofferTextInputLayout);
        String hintText = typedArray.getString(R.styleable.CofferTextInputLayout_hintTipText);
        mHintTextTipColor = typedArray.getColor(R.styleable.CofferTextInputLayout_hintSelectTextColor,
                context.getResources().getColor(R.color.blue));
        mHintTextDefaultColor = typedArray.getColor(R.styleable.CofferTextInputLayout_hintTextDefaultColor,
                context.getResources().getColor(R.color.text_color_cr_reduce));
        mUseClean = typedArray.getBoolean(R.styleable.CofferTextInputLayout_useClean,false);
        mTvTip.setText(hintText);
        mTvTip.setTextColor(mHintTextDefaultColor);
        typedArray.recycle();
        // 设定属性，绑定相关的监听
        mEditText.addTextChangedListener(new DefaultTextWatcher());
        mEditText.setOnFocusChangeListener(new DefaultOnFocusChangeListener());
        mIvClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                if (!mHasFocus){
                    showNoFocusAnim();
                }
            }
        });
    }

    public void setHintTextDefaultColor(int defaultColor){
        mHintTextDefaultColor = defaultColor;
    }

    public void setHintTextTipColor(int color){
        mHintTextTipColor = color;
    }

    public EditText getEditText(){
        return mEditText;
    }

    /**
     * 获取输入框最右边的功能图标
     * @return view
     */
    public ImageView getFunIcon(){
        return mIvFun;
    }

    /**
     * 设置输入框最右边的功能图标
     */
    public void setFunIcon(int drawableId){
        mIvFun.setImageResource(drawableId);
    }

    /**
     * 是否设置显示清空内容，功能图标和清空按钮不能同时显示
     * @param state 默认不显TRUE 表示使用
     */
    public void setUseClean(boolean state){
        mUseClean = state;
    }

    /**
     * 是否设置显示功能图标，功能图标和清空按钮不能同时显示
     * @param state TRUE 表示使用
     */
    public void setUseFun(boolean state){
        if (state){
            mIvFun.setVisibility(VISIBLE);
        }else {
            mIvFun.setVisibility(GONE);
        }
    }

    /**
     * 给外面自定义设置
     * @param textWatcher 内容监听
     */
    public void setTextWatcher(TextWatcher textWatcher){
        mTextWatcher = textWatcher;
    }

    /**
     * 给外面自定义设置
     * @param onFocusChangeListener 焦点监听
     */
    public void settOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener){
        mOnFocusChangeListener = onFocusChangeListener;
    }

    private class DefaultTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            if (mTextWatcher != null){
                mTextWatcher.beforeTextChanged(charSequence,start,count,after);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            if (mTextWatcher != null){
                mTextWatcher.onTextChanged(charSequence,start,before,count);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String content = editable.toString();
            if (mUseClean){
                if (TextUtils.isEmpty(content)){
                    mIvClose.setVisibility(GONE);
                }else {
                    mIvClose.setVisibility(VISIBLE);
                }
            }
            if (mTextWatcher != null){
                mTextWatcher.afterTextChanged(editable);
            }
        }
    }

    private class DefaultOnFocusChangeListener implements OnFocusChangeListener{

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            String content = "";
            if (view instanceof EditText){
                content = ((EditText) view).getText().toString();
            }
            if (TextUtils.isEmpty(content)){
                if (hasFocus){
                    // 有焦点，给提示文案做右上角缩放动画
                    showFocusAnim();
                }else {
                    // 没有焦点，上面的动画反过来
                    showNoFocusAnim();
                }
            }
            mHasFocus = hasFocus;

            if (mOnFocusChangeListener != null){
                mOnFocusChangeListener.onFocusChange(view,hasFocus);
            }
        }
    }

    /**
     * 给提示文案做动画
     * 动画效果：
     * 进行：1、View从左下角上移，2、View变小。变化时间200ms
     * 结束：1、文字颜色变化为输入框之外的颜色，2、文字字体变细（Normal）
     */
    private void showFocusAnim(){
        PropertyValuesHolder[] focus = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX",1f,0.67f),
                PropertyValuesHolder.ofFloat("scaleY",1f,0.67f),
                PropertyValuesHolder.ofFloat("translationY",0,
                        -Util.dipToPixel(getContext(),19))
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTvTip, focus);
        objectAnimator.setDuration(200);
        mTvTip.setPivotX(0);
        mTvTip.setPivotY(0);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvTip.setTypeface(null,Typeface.NORMAL);
                mTvTip.setTextColor(mHintTextTipColor);
            }
        });
        objectAnimator.start();
    }

    /**
     * 给提示文案做动画
     * 动画效果：
     * 进行：1、View从左上角下移，2、View变大。变化时间200ms
     * 结束：1、文字颜色变化为默认颜色，2、文字字体变粗（Bold）
     */
    private void showNoFocusAnim(){
        PropertyValuesHolder[] noFocus = new PropertyValuesHolder[]{
                PropertyValuesHolder.ofFloat("scaleX",0.67f,1f),
                PropertyValuesHolder.ofFloat("scaleY",0.67f,1f),
                PropertyValuesHolder.ofFloat("translationY",mTvTip.getTranslationY(),0)
        };
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTvTip, noFocus);
        objectAnimator.setDuration(200);
        mTvTip.setPivotX(0);
        mTvTip.setPivotY(0);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvTip.setTypeface(null,Typeface.BOLD);
                mTvTip.setTextColor(mHintTextDefaultColor);
            }
        });
        objectAnimator.start();
    }

}
