package coffer.customViewDemo;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.widget.DampLayout;

/**
 * @author：张宝全
 * @date：2020/5/12
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class DampViewActivity extends BaseDefaultActivity {

    private static final String TAG = "DampViewActivity_tag";

    private DampLayout mDampLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_damp_main);
        mDampLayout = findViewById(R.id.damp);
        mDampLayout.setListener(new DampLayout.Listener() {
            @Override
            public void click() {
                Log.i(TAG,"show 啦");
            }
        });
        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DampViewActivity.this,"😋",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void initData() {

    }
}
