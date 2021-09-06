package coffer.androidDemo.Login;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import coffer.util.Util;
import coffer.widget.CofferEditText;
import coffer.widget.CofferTextInputLayout;

/**
 * author      : coffer
 * date        : 8/24/21
 * description :
 * Reviewer    :
 */
public class LoginActivity extends BaseDefaultActivity {

    private CofferEditText mCofferEditText;
    private EditText mEditText;
    private CofferTextInputLayout mInputNumber;
    private CofferTextInputLayout mInputName;
    private TextView mBtn;

    private PopupWindow mPop;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login_main);
        mEditText = findViewById(R.id.edit);
        mInputNumber = findViewById(R.id.c_input_number);
        mInputNumber.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mBtn = findViewById(R.id.bt2);
        String str = mInputNumber.getEditText().getText().toString();
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(LoginActivity.this, "haha", Toast.LENGTH_SHORT).show();
//                showPopWindow(mInputNumber.getFunIcon());
                mPop = showTipPopupWindow(mInputNumber.getFunIcon(), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPop.dismiss();
                    }
                });
            }
        });
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, LoginActivityA.class);
                startActivityForResult(intent, 100);
            }
        });
//        if (TextUtils.isEmpty(str)){
//            mBtn.setClickable(false);
//        }
//        showPopWindow(mInputNumber.getFunIcon());
//        mPop = showTipPopupWindow(mInputNumber.getFunIcon(), new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        mInputNumber.setTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString();
                mBtn.setClickable(!TextUtils.isEmpty(content));

            }
        });
        mInputNumber.setUseFun(true);
        mInputName = findViewById(R.id.c_input_name);
    }

    @Override
    public void initData() {

    }

    public PopupWindow showTipPopupWindow(final View anchorView, final View.OnClickListener onClickListener) {
        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.popuw_content_top_arrow_layout, null);
        TextView textView = contentView.findViewById(R.id.tip_text);
        textView.setText("Address book");
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        //  获取测量后的宽度
        int popupWidth = contentView.getMeasuredWidth();
        //  获取测量后的高度
        int popupHeight = contentView.getMeasuredHeight();
        final PopupWindow popupWindow = new PopupWindow(contentView,
                popupWidth, popupHeight, false);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                onClickListener.onClick(v);
            }
        });

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(false);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        popupWindow.showAsDropDown(anchorView, popupWidth,
                -(popupHeight + anchorView.getHeight() + Util.dipToPixel(this,6)));
        return popupWindow;
    }

    @Override
    public void onBackPressed() {
        if (mPop != null && mPop.isShowing()) {
            mPop.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CofferLog.D("login_tag","LoginActivity onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CofferLog.D("login_tag","LoginActivity onActivityResult");
        CofferLog.D("login_tag","requestCode : "+requestCode + " , resultCode : "+resultCode);
        Bundle bundle = data.getBundleExtra("data");
        String content = bundle.getString("my_data");
        CofferLog.D("login_tag","content : "+content);
    }
}
