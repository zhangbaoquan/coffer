package coffer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.dianping.logan.Logan;
import com.dianping.logan.LoganConfig;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.ireader.plug.api.IreaderPlugApi;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.vivo.mobilead.manager.VivoAdManager;

import java.io.File;

import coffer.crashDemo.LogService;
import coffer.util.CONSTANT;

import static coffer.util.CONSTANT.COFFER_TAG;

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
    public void onCreate() {
        super.onCreate();
        DoraemonKit.install(this);
        IreaderPlugApi.initPlugWhenAPPOncreate(this);
        Log.d(COFFER_TAG,"CofferApplication onCreate ");
//        if (LeakCanary.isInAnalyzerProcess(this)){
//
//            return;
//        }
//        LeakCanary.install(this);
        // 缓存图片的配置，一般通用的配置
        initImageLoader(getApplicationContext());
        // 初始化美团的Logan 日志系统
        initLog();
        VivoAdManager.getInstance().init(this,"619d45fa3d654b5d9222743873eea72d");
    }

    public static CofferApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
        Log.d(COFFER_TAG,"CofferApplication attachBaseContext ");
        Intent intent = new Intent(instance, LogService.class);
        intent.putExtra("pid", android.os.Process.myPid());
        instance.startService(intent);
        MultiDex.install(this);
        IreaderPlugApi.initPlugWhenAPPAttachBaseContext(this, base);
    }

    private void initImageLoader(Context context) {
        // TODO Auto-generated method stub
        // 创建DisplayImageOptions对象
        DisplayImageOptions defaulOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        // 创建ImageLoaderConfiguration对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
                context).defaultDisplayImageOptions(defaulOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        // ImageLoader对象的配置
        ImageLoader.getInstance().init(configuration);
    }

    private void initLog(){
        LoganConfig config = new LoganConfig.Builder()
                .setCachePath(getApplicationContext().getFilesDir().getAbsolutePath())
                .setPath(getApplicationContext().getExternalFilesDir(null).getAbsolutePath()
                        + File.separator + "logan_v1")
                .setEncryptKey16("0123456789012345".getBytes())
                .setEncryptIV16("0123456789012345".getBytes())
                .build();

        Logan.init(config);
    }

}
