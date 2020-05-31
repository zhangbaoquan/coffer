package coffer.pluginDemo;

import android.content.Intent;
import android.view.View;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2019-09-30
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class PluginMainActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_plugin_main);
        // 掌阅（浏览器插件4.4版本）
        findViewById(R.id.zy_old).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PluginMainActivity.this, ZyOldMainActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PluginMainActivity.this, TestPluginActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.zy_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initData() {

    }


}