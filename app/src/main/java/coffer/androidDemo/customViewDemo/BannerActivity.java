package coffer.androidDemo.customViewDemo;

import android.widget.Toast;

import java.util.ArrayList;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.widget.TextBannerView;

/**
 * @author：张宝全
 * @date：2020/6/2
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class BannerActivity extends BaseDefaultActivity {
    TextBannerView textBannerView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_banner_main);
        textBannerView = findViewById(R.id.textBanner);
    }

    @Override
    public void initData() {
        final ArrayList<String> textData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            textData.add("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦"+i);
        }
        textBannerView.setBannerString(textData);
        textBannerView.setOnBannerClickListener(new TextBannerView.OnBannerClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(BannerActivity.this,
                        textData.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
