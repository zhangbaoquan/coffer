package coffer.adDemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;

import static android.view.View.OVER_SCROLL_NEVER;

/**
 * @author：张宝全
 * @date：2020-01-18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ViewPagerBannerActivity extends AppCompatActivity {

    private RelativeLayout mTitle;
    private ViewPager mViewPager;
    private List<AdData> mData;
    private CofferPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_main);
        initData();
        initView();
        initListener();
    }

    private void initView(){
        mTitle = findViewById(R.id.titleTab);
        mViewPager = findViewById(R.id.vp_content);
        adapter = new CofferPagerAdapter(mData,this);
    }

    private void initData(){
        mData = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            AdData adData = new AdData();
            adData.title = "欢欢" + i;
            adData.content = "啦啦" +i;
            mData.add(adData);
        }
    }

    private void initListener(){
        mViewPager.setOverScrollMode(OVER_SCROLL_NEVER);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(adapter);
        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 增加广告
                AdData adData = new AdData();
                adData.haveAd = true;
                mData.add(adData);
                if (mViewPager.getAdapter() != null) {
                    mViewPager.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    class CofferPagerAdapter extends PagerAdapter{

        private List<AdData> data;
        private Context context;

        CofferPagerAdapter(List<AdData> data,Context context){
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View child = getPageView(data.get(position), position);
            container.addView(child);
            return child;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull View container, int position, @NonNull Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        private View getPageView(AdData adData,int position){
            LinearLayout parent = new LinearLayout(context);
            parent.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            parent.setOrientation(LinearLayout.VERTICAL);
            parent.setBackgroundColor(Color.BLUE);

            TextView title = new TextView(context);
            title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            title.setText(adData.title);
            parent.addView(title);

            TextView content = new TextView(context);
            content.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            content.setText(adData.content);
            parent.addView(content);
            if (adData.haveAd){
                LinearLayout parent2 = new LinearLayout(context);
                parent2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                parent2.setOrientation(LinearLayout.VERTICAL);
                parent2.setBackgroundColor(Color.GREEN);
                return parent2;
            }else {
                return parent;
            }

        }
    }
}
