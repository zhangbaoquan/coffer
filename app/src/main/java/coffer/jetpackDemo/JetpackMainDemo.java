package coffer.jetpackDemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： Jetpack 组件练习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class JetpackMainDemo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jetpack_main);

        // ViewPager2Activity
        findViewById(R.id.b0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("coffer.jetpackDemo.ViewPager2Activity");
            }
        });

        // MvvmActivity
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity("coffer.jetpackDemo.MvvmActivity");
            }
        });
    }

    private void openActivity(String activityClass){
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName(JetpackMainDemo.this,activityClass);
        intent.setComponent(componentName);
        startActivity(intent);
    }
}
