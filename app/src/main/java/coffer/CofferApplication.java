package coffer;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

/**
 * @author：张宝全
 * @date：2019-06-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferApplication extends Application {

    protected static CofferApplication instance = null;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
        MultiDex.install(this);
    }

    public static CofferApplication getInstance() {
        return instance;
    }
}
