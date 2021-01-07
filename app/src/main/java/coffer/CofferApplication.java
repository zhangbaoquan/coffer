package coffer;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
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
import com.tencent.mmkv.MMKV;
import com.vivo.mobilead.manager.VivoAdManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import coffer.crashDemo.LogService;
import coffer.util.CONSTANT;
import coffer.util.CofferLog;
import coffer.util.FileUtils;
import coffer.util.HookUtils;
import coffer.util.Util;
import xcrash.ICrashCallback;
import xcrash.TombstoneParser;
import xcrash.XCrash;

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

    private static final String TAG = "CofferApplication_tag";
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
        MMKV.initialize(this);
        HookUtils.hookInstrumentation();
    }

    public static CofferApplication getInstance() {
        return instance;
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
        Log.d(COFFER_TAG,"CofferApplication attachBaseContext processName : "+ Util.getCurProcessName(base));
        Intent intent = new Intent(instance, LogService.class);
        intent.putExtra("pid", android.os.Process.myPid());
        instance.startService(intent);
        MultiDex.install(this);
        IreaderPlugApi.initPlugWhenAPPAttachBaseContext(this, base);
//        CrashHandler.getInstance().init(base);
        initXCrash();
    }

    private void initXCrash(){
        ICrashCallback callback = new ICrashCallback() {
            @Override
            public void onCrash(String logPath, String emergency) throws Exception {
                CofferLog.D(TAG,"logPath : "+logPath+" ,emergency : "+emergency);
                CofferLog.D(TAG,"getFilesDir : "+getApplicationContext().getFilesDir());
                String log = new JSONObject(TombstoneParser.parse(logPath, emergency)).toString();
                CofferLog.D(TAG,"log : "+log);
                // 拿到这个日志，可以上传给服务器
                saveCrash(log);
            }
        };

//        XCrash.init(this,new XCrash.InitParameters()
//                .setAppVersion("1.2.3-beta456-patch789")
//                .setJavaRethrow(true)
//                .setJavaLogCountMax(10)
//                .setJavaDumpAllThreadsWhiteList(new String[]{"^main$", "^Binder:.*", ".*Finalizer.*"})
//                .setJavaDumpAllThreadsCountMax(10)
//                .setJavaCallback(callback)
//                .setNativeRethrow(true)
//                .setNativeLogCountMax(10)
//                .setNativeDumpAllThreadsWhiteList(new String[]{"^xcrash\\.sample$", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"})
//                .setNativeDumpAllThreadsCountMax(10)
//                .setNativeCallback(callback)
//                .setAnrRethrow(true)
//                .setAnrLogCountMax(10)
//                .setAnrCallback(callback)
//                .setPlaceholderCountMax(3)
//                .setPlaceholderSizeKb(512)
//                .setLogFileMaintainDelayMs(1000)
//        );
        XCrash.init(this, new XCrash.InitParameters()
                .enableAnrCrashHandler()        // 开启ANR异常捕获；捕获disableAnrCrashHandler()
                .setAnrCheckProcessState(true)  // 是否设置anr的状态标志给进程状态
                .setAnrRethrow(true)            // 是否抛出anr异常。默认为true
                .setAnrLogCountMax(100)         // anr日志最大保留文件数量
                .setAnrLogcatSystemLines(100)
                .setAnrLogcatEventsLines(100)
                .setLogDir(FileUtils.getCrashPath())
                .setAnrLogcatMainLines(100)
                .setAnrDumpFds(true)            // 是否输出app进程的下打开的文件描述符
                .setAnrCallback(callback));     // 发生anr异常的应用回调

    }

    private void saveCrash(String log){
        // Parse and save the crash info to a JSON file for debugging.
        OutputStreamWriter writer = null;
        FileOutputStream fos = null;
        try {
            File file = new File(FileUtils.getCrashPath());
            if (!file.exists()) {
                file.mkdirs();
            }
            File outPutFile = new File(file,  "xCrash_log.txt");
            if (outPutFile.exists()) {
                outPutFile.delete();
            }
            outPutFile.createNewFile();
            fos = new FileOutputStream(outPutFile);
            writer = new OutputStreamWriter(fos, "UTF-8");
            writer.write(log);
            writer.flush();
        } catch (Exception e) {
            Log.d(TAG, "debug failed", e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception ignored) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    /**
     * 应用是否进入后台
     * @param context
     * @return
     */
    public static boolean isAppGoToBackground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
