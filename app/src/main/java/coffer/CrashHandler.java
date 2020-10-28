package coffer;

import android.content.Context;
import android.os.FileObserver;

import androidx.annotation.Nullable;

import coffer.util.CofferLog;

import static android.os.FileObserver.CLOSE_WRITE;

/**
 * @author：张宝全
 * @date：2020/10/27
 * @Description： 自定义异常捕获处理，含ANR、Java Crash
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler_tag";
    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance;

    private Context mContext;

    private FileObserver mFileObserver;

    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        registerAnr();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        CofferLog.D(TAG,"啊 😢");
        mFileObserver.stopWatching();
        mFileObserver = null;
    }

    private void registerAnr(){
       mFileObserver = new FileObserver("/data/anr",CLOSE_WRITE){
            @Override
            public void onEvent(int event, @Nullable String path) {
                // 在Android 7.0以上的手机，该方法不会执行
                CofferLog.D(TAG,"kk");
                if (path != null){
                    String filepath = "/data/anr/" + path;
                    CofferLog.D(TAG,"filepath : "+filepath);
                    if (filepath.contains("trace")){
                        // 采集ANR 日志
                        CofferLog.D(TAG,"啦啦😋");
                    }
                }
            }
        };
       try {
           mFileObserver.startWatching();
       }catch (Exception e){
           mFileObserver = null;
           e.printStackTrace();
       }
    }
}
