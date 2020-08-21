package coffer.androidDemo.customViewDemo;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.androidDemo.customViewDemo.weidget.ContainerView1;
import coffer.androidDemo.customViewDemo.weidget.ContainerView2;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： 自定义View练习系列
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ScrollContainerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_container_main);
        final FrameLayout frameLayout = findViewById(R.id.body);
        final ContainerView1 containerView1 = new ContainerView1(ScrollContainerActivity.this);
        containerView1.setX(1080);
//        final ContainerView2 containerView2 = new ContainerView2(ScrollContainerActivity.this);
//        containerView2.setX(1080);
        frameLayout.addView(containerView1);
//        frameLayout.addView(containerView2);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerView1.show();
            }
        });
        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                containerView2.show();
                frameLayout.addView(new ContainerView2(ScrollContainerActivity.this));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
