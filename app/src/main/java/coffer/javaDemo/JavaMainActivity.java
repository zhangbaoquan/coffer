package coffer.javaDemo;

import android.content.Intent;
import android.view.View;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.javaDemo.fileDemo.FileActivity;
import coffer.javaDemo.hookDemo.InvokeActivity;
import coffer.javaDemo.reflectdemo.ReflectActivity;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description： 反射、文件读写（IO）、多线程、线程池、死锁、集合、代理（静态、动态）
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class JavaMainActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_java_main);

        // 反射
        findViewById(R.id.ref).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JavaMainActivity.this, ReflectActivity.class);
                startActivity(intent);
            }
        });

        // 文件读写
        findViewById(R.id.file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JavaMainActivity.this, FileActivity.class);
                startActivity(intent);
            }
        });

        // 动态代理
        findViewById(R.id.proxy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JavaMainActivity.this, InvokeActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void initData() {

    }
}
