package coffer.androidDemo;

import android.content.Intent;
import android.view.View;

import coffer.BaseDefaultActivity;
import coffer.androidDemo.customViewDemo.CustomViewMainActivity;
import coffer.androidDemo.messageDemo.MessageTestActivity;
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
    }


    @Override
    public void initData() {

    }
}
