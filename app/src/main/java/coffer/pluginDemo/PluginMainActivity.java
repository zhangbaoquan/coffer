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
        // 掌阅4.4 插件的接入和新版7.15有资源合并冲突，不能同时存在，因此关于新插件的接入，在zy_plugin_7.15分支
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