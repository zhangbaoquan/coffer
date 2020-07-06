package coffer.util;

import android.annotation.SuppressLint;
import android.app.Activity;


/**
 * @author：张宝全
 * @date：2020/7/3
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class APP {

    /**
     * 当前activity
     */
    @SuppressLint("StaticFieldLeak")
    private static Activity mCurrActivity = null;

    /**
     * 设置当前activity和handler
     */
    public synchronized static void setCurrActivity(Activity activity) {
        mCurrActivity = activity;
    }

    /**
     * 使用的时候要判断是否为null
     *
     * @return mCurrActivity
     * @deprecated 能不用就不用，必须要用一定要判空
     */
    public static Activity getCurrActivity() {
        return mCurrActivity;
    }

}
