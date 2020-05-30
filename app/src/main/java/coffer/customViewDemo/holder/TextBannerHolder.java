package coffer.customViewDemo.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import coffer.customViewDemo.bean.MutiTypeData;
import coffer.customViewDemo.weidget.BannerViewLayout;
import coffer.widget.CofferFlowLayout;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description： 轮播文字广告位
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class TextBannerHolder extends BaseHolder {

    CofferFlowLayout cofferFlowLayout;
    Context context;
    ArrayList<String> tag;

    public TextBannerHolder(Context context) {
        super(new CofferFlowLayout(context));
        this.context = context;
    }

    @Override
    protected void createHolder(View itemView) {
        super.createHolder(itemView);
        cofferFlowLayout = ((CofferFlowLayout) itemView);
    }

    @Override
    public void bindHolder(MutiTypeData.Data bean, int itemPosition) {
        super.bindHolder(bean, itemPosition);
        final ArrayList<MutiTypeData.Baby> items = bean.baby;
        final int adCount = items == null ? 0 : items.size();
        if (tag == null) {
            tag = new ArrayList<>();
        }
        tag.clear();
        for (int i = 0; i < adCount; i++) {
            tag.add(items.get(i).title);
        }
        cofferFlowLayout.setTag(tag, new CofferFlowLayout.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(context, tag.get(position),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }
}

