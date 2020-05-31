package networkDemo;

import android.content.Intent;
import android.view.View;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description： 网络demo
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class NetWorkActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_networl_main);
        findViewById(R.id.okhttp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NetWorkActivity.this, OkHttpActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NetWorkActivity.this, RetrofitActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }
}
