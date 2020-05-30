package coffer.animDemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import coffer.androidjatpack.R;
import coffer.util.AnimUtils;
import coffer.util.Util;


/**
 * @author：张宝全
 * @date：2019-05-04
 * @Description： 平移动画
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
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;

    private LinearLayout mParent;

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate_main);
        mParent = findViewById(R.id.parent);
        initView();

    }

    private void initView(){
        // 改变View的宽度大小
        tv1 = findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo1(tv1);
            }
        });

        // View的透明度
        tv2 = findViewById(R.id.tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo2(tv2);
                Util.setNavVisibility(false,AnimActivity.this);
            }
        });

        // 组合动画
        tv3 = findViewById(R.id.tv3);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo3(tv3);
                Util.setNavVisibility(true,AnimActivity.this);
            }
        });

        // ViewPropertyAnimator
        tv4 = findViewById(R.id.tv4);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo4(tv4);
            }
        });

        // 组合动画2
        tv5 = findViewById(R.id.tv5);
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo5(tv5);
            }
        });

        // 气泡动画
        tv6 = findViewById(R.id.tv6);
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo6(tv6);
            }
        });

        // z
        tv7 = findViewById(R.id.tv7);
        tv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo7(tv7);
            }
        });

        tv8 = findViewById(R.id.tv8);
        final Button linearLayout = new Button(this);
        linearLayout.setText("锚点测试");
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(200,100));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.blue));

        linearLayout.setX(100);
        linearLayout.setY(100);
        mParent.addView(linearLayout);
        tv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.animDemo8(linearLayout);
            }
        });

        tv9 = findViewById(R.id.tv9);
        tv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimUtils.fadeOut(tv9);
            }
        });

        //TODO 模拟内存泄露
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

            }
        };

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(1);
            }
        },10000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
