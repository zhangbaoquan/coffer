package coffer.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import coffer.androidjatpack.R;
import coffer.util.CofferLog;

/**
 * author      : coffer
 * date        : 8/26/21
 * description :
 * Reviewer    :
 */
public class CofferEditText extends androidx.appcompat.widget.AppCompatEditText {
    public CofferEditText(Context context) {
        super(context);
        init(context);
    }

    public CofferEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CofferEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.addTextChangedListener(new CofferEditTextWatcher());
        this.setOnFocusChangeListener(new CofferEditTextFocusChangedListener());
    }

    private class CofferEditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            CofferLog.D("c_edit", "beforeTextChanged charSequence : " + charSequence.toString() +
                    " ,start : " + start + " ,after : " + after + " ,count : " + count);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            CofferLog.D("c_edit", "onTextChanged charSequence : " + charSequence.toString() +
                    " ,start : " + start + " ,before : " + before + " ,count : " + count);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            CofferLog.D("c_edit", "afterTextChanged editable : " + editable.toString());
        }
    }

    /**
     * OnFocusChangeListener接口用来处理控件焦点发生改变的事件，如果注册了该接口，
     * 当某个控件失去焦点或者获得焦点的时候都会出发该接口中的回调方法.
     */
    private class CofferEditTextFocusChangedListener implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            CofferLog.D("c_edit", "hasFocus : " + hasFocus);
            // 可以根据焦点 + 当前EditText里的文案长度 来判断clear图标是否显示
        }
    }
}
