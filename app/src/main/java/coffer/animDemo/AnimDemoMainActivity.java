package coffer.animDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： 动画练习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class AnimDemoMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_main);

        // 平移练习
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimDemoMainActivity.this, AnimActivity.class);
                startActivity(intent);
            }
        });

    }
}
