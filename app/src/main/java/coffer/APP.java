package coffer;

import android.content.Context;

/**
 * @author：张宝全
 * @date：2019-06-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class APP {

    /**
     * 获取application context
     *
     * @return
     */
    public static Context getAppContext() {
        return CofferApplication.getInstance();
    }
}
