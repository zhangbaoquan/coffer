package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.androidDemo.customViewDemo.drawable.BannerAdDrawable;
import coffer.androidjatpack.R;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/10/14
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class XmodeView extends View {
    public XmodeView(Context context) {
        super(context);
        init(context);
    }

    public XmodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XmodeView(Context context, @Nullable AttributeSet attrs,int defStyle) {
        super(context, attrs,defStyle);
        init(context);
    }

    private void init(Context context){
//        Drawable drawable = context.getResources().getDrawable(R.drawable.img_guide_smile_1);
        Drawable drawable = context.getResources().getDrawable(R.drawable.bilibili);

        Drawable drawable1 = new BannerAdDrawable(context, Util.drawableToBitmap(drawable),
                "啦啦","哈哈","嘿嘿",
                Util.dipToPixel(context,50),Util.dipToPixel(context,50));
        // 给这个图片设置一个颜色的蒙层（遮罩）
//        drawable.setColorFilter(0x7712b7f5,PorterDuff.Mode.SRC_ATOP);
//        drawable1.setColorFilter(0x7712b7f5,PorterDuff.Mode.SRC_IN);
        setBackground(drawable1);
    }
}
