package coffer.androidDemo.messageDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static coffer.util.CONSTANT.COFFER_TAG;

/**
 * @author：张宝全
 * @date：2019-12-10
 * @Description： 放在单独进程：coffer，开启服务时，将新的进程唤起，进而初始化放在单独进程里的ContentProvider
 *                从而实现ContentProvider 延迟初始化的目的
 *                使用场景：由于ContentProvider初始化时机非常早，顺序是Application->attachBaseContext() >
 *                ContentProvider->onCreate() > Application->onCreate()。虽然掌阅内置插件的初始化也在
 *                Application->attachBaseContext()里做初始化，但是插件初始化的时间远大于ContentProvider初始化时间
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class BridgeService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(COFFER_TAG,"BridgeService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.d(COFFER_TAG,"BridgeService onCreate");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
