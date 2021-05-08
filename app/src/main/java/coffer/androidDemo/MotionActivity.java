package coffer.androidDemo;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.widget.MotionButtion;
import coffer.widget.MotionLinearLayout;

/**
 * @author：张宝全
 * @date：2021/2/18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class MotionActivity extends BaseDefaultActivity {

    private static final String TAG = "cocc";

    Button button1,button2;
    MotionLinearLayout myLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_motion_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        myLayout = findViewById(R.id.my_layout);
    }

    @Override
    public void initData() {
// 1.为ViewGroup布局设置监听事件
        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "点击了ViewGroup");
            }
        });

        myLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch");
                return false;
            }
        });

        // 2. 为按钮1设置监听事件
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "点击了button1");
            }
        });

        // 3. 为按钮2设置监听事件
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "点击了button2");
            }
        });

    }
}
