package coffer.androidDemo;

import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import coffer.BaseDefaultActivity;
import coffer.androidDemo.customViewDemo.CustomViewMainActivity;
import coffer.androidDemo.messageDemo.MessageTestActivity;
import coffer.androidDemo.behaviorDemo.BottomSheetBehaviorDemoActivity;
import coffer.androidDemo.router.AInterceptor.UseARouterInterceptor;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class AndroidMainActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_android_main);
        // 动画练习系列
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidMainActivity.this, coffer.animDemo.AnimDemoMainActivity.class);
                startActivity(intent);
            }
        });

        // 自定义View 系列
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidMainActivity.this, CustomViewMainActivity.class);
                startActivity(intent);
            }
        });

        // 消息机制
        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidMainActivity.this, MessageTestActivity.class);
                startActivity(intent);
            }
        });

        // BottomSheetBehavior
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidMainActivity.this, BottomSheetBehaviorDemoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidMainActivity.this, MotionActivity.class);
                startActivity(intent);
            }
        });

        ARouter.getInstance().inject(this);
        // ARouter 原理
        findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到 ARouterMainActivity，可以携带参数
                ARouter.getInstance().build("/coffer/router/ARouterMainActivity")
                        .setProvider(new UseARouterInterceptor())
                        .withString("coffer_key","哈哈")
                        .navigation();
            }
        });

        // DRouter 原理
        findViewById(R.id.b7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public void initData() {
        String content = "今日剩余"+10+"次机会";
        SpannableString spannableString = new SpannableString(content);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#5c6273"));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#D81B60"));
        ForegroundColorSpan colorSpa1 = new ForegroundColorSpan(Color.parseColor("#0099EE"));
        spannableString.setSpan(colorSpan2, 0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpa1, 4,4+2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan, 4+2,content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        TextView textView = findViewById(R.id.t1);
        textView.setText(spannableString);

    }
}
