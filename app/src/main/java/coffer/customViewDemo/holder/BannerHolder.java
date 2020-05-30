package coffer.customViewDemo.holder;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import coffer.customViewDemo.bean.MutiTypeData;
import coffer.customViewDemo.weidget.BannerViewLayout;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class BannerHolder extends BaseHolder {

    TextView textView;
    ImageView imageView;

    public BannerHolder(Context context) {
        super(new BannerViewLayout(context));
    }

    @Override
    protected void createHolder(View itemView) {
        super.createHolder(itemView);
        textView = ((BannerViewLayout)itemView).textView;
        imageView = ((BannerViewLayout)itemView).imageView;
    }

    @Override
    public void bindHolder(MutiTypeData.Data bean, int itemPosition) {
        super.bindHolder(bean, itemPosition);
        final ArrayList<MutiTypeData.Baby> items = bean.baby;
        final int adCount = items == null ? 0 : items.size();
        for (int i = 0; i < adCount; i++) {
            String content = items.get(i).title;
            if (TextUtils.isEmpty(content)){
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(content);
            }
            ImageLoader.getInstance().displayImage(items.get(i).url,imageView);
        }
    }

}
