package coffer.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.recyclerview.widget.RecyclerView;

import coffer.CofferApplication;
import coffer.customViewDemo.recycleView.PauseOnScrollListener;


/**
 * @author：张宝全
 * @date：2019-11-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class Util {

    public static int dipToPixel(Context context, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1);
    }

    public static float dipToPixel(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1;
    }

    public static int dipToPixel(Resources r, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics()) + 1);
    }

    public static void setNavVisibility(final boolean visible, Activity activity) {
        int newVis = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

        if (!visible) {
            newVis = 256 //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | 512 //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | 1024 //View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | 4 //View.SYSTEM_UI_FLAG_FULLSCREEN
                    | 2 //View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                    | 4096 //View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | 2048; //View.SYSTEM_UI_FLAG_IMMERSIVE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; //View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | 4096; //View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            activity.getWindow().getDecorView().setSystemUiVisibility(newVis);
        }else{
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            activity.getWindow().getDecorView().setSystemUiVisibility(newVis);
        }
    }

    /**
     * setPauseOnScrollListener:设置滚动暂停监听. <br/>
     *
     * @param view
     * @param customScrollListener
     * @author huangyahong
     */
    public static void setPauseOnScrollListener(RecyclerView view, RecyclerView.OnScrollListener customScrollListener) {
        if (view == null)
            throw new IllegalArgumentException("view 不能为空");
        PauseOnScrollListener pauseOnScrollListener = null;
        int level = AppMemeryLevelUtils.getAppMemeryLevel(CofferApplication.getInstance());
        if (level == AppMemeryLevelUtils.HIGHT_MEMERY) {
            // 高运行内存，滚动不暂停
            pauseOnScrollListener =
                    new PauseOnScrollListener(false, false, customScrollListener);
        } else if (level == AppMemeryLevelUtils.MIDDLE_MEMERY) {
            // 中运行内存，快速滚动不暂停图片加载
            pauseOnScrollListener =
                    new PauseOnScrollListener(false, true, customScrollListener);
        } else if (level == AppMemeryLevelUtils.LOW_MEMERY) {
            // 中运行内存，滚动和快速滚动暂停图片加载
            pauseOnScrollListener =
                    new PauseOnScrollListener(true, true, customScrollListener);
        }
        view.addOnScrollListener(pauseOnScrollListener);
    }

}
