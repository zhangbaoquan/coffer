package coffer.androidDemo.customViewDemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.nostra13.universalimageloader.core.ImageLoader;

import coffer.androidDemo.customViewDemo.weidget.AirXfermodeCircleView;
import coffer.androidjatpack.R;
import coffer.widget.BannerContentLayout;
import coffer.widget.DrawGraphView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class DrawViewActiviy extends AppCompatActivity {
    private DrawGraphView mDrawGraphView;

    private BannerContentLayout mBanner;

    AirXfermodeCircleView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view_main);
        mBanner = findViewById(R.id.banner);
        mDrawGraphView = findViewById(R.id.drawView);
        createBannerContentView();


    }

    private void open(){

    }

    private void close(){

    }

    private void createBannerContentView(){
        for (int i = 0; i < 3; i++) {
            mBanner.addView(getView(i));
        }
    }

    private View getView(int i){
        View adView = LayoutInflater.from(DrawViewActiviy.this).inflate(R.layout.activity_view_item, null);
        adView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        View body = adView.findViewById(R.id.body);
        ImageView ivAd = adView.findViewById(R.id.iv_ad);
        TextView tvTitle = adView.findViewById(R.id.tv_title);
        TextView tvDesc = adView.findViewById(R.id.tv_desc);

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DrawViewActiviy.this,"点啦",Toast.LENGTH_SHORT).show();
            }
        });

        if (i == 0){
            tvTitle.setText("啦啦");
            tvDesc.setText("凉宫春日的忧郁");
            ImageLoader.getInstance().displayImage("http://img15.3lian.com/2015/f3/08/d/02.jpg",ivAd);
        }else if (i == 1){
            tvTitle.setText("哈哈");
            tvDesc.setText("凉宫春日的烦闷");
            ImageLoader.getInstance().displayImage("http://pic2.16pic.com/00/15/80/16pic_1580359_b.jpg",ivAd);
        }else {
            tvTitle.setText("露露");
            tvDesc.setText("凉宫春日的动摇");
            ImageLoader.getInstance().displayImage("http://img15.3lian.com/2015/f3/08/d/02.jpg",ivAd);
        }

        return adView;
    }

}
