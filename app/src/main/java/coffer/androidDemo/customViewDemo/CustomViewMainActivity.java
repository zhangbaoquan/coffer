package coffer.androidDemo.customViewDemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： 自定义View练习系列
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CustomViewMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view_main);

        // 绘制练习
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, DrawViewActiviy.class);
                startActivity(intent);
            }
        });

        // 自定义View的滑动和绘制
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ScrollActiviy2.class);
                startActivity(intent);
            }
        });

        // 弹性RecycleView 的实现
        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, FlexibleViewActiviy.class);
                startActivity(intent);
            }
        });

        // 自定义ViewGroup 的实现
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ArrangeViewActivity.class);
                startActivity(intent);
            }
        });

        // 自定义ViewGroup 的实现
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ArrangeViewActivity.class);
                startActivity(intent);
            }
        });

        // 画廊 的实现
        findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, GalleryActivity.class);
                startActivity(intent);
            }
        });

        // 左右弹性阻尼的View 的实现
        findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, DampViewActivity.class);
                startActivity(intent);
            }
        });

        // ListView
        findViewById(R.id.b7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ListViewActiviy.class);
                startActivity(intent);
            }
        });

        // 多布局样式
        findViewById(R.id.b8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, MutiTypeActivity.class);
                startActivity(intent);
            }
        });

        // Banner 系列
        findViewById(R.id.b9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, BannerActivity.class);
                startActivity(intent);
            }
        });

        // Banner 系列
        findViewById(R.id.b10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ScrollLoginActivity.class);
                startActivity(intent);
            }
        });

        // 滑动事件练习
        findViewById(R.id.b11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, ScrollContainerActivity.class);
                startActivity(intent);
            }
        });

        // 绘制练习2
        findViewById(R.id.b12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomViewMainActivity.this, DrawViewActiviy.class);
                startActivity(intent);
            }
        });

    }


}
