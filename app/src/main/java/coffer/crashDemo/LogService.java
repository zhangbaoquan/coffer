package coffer.crashDemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class LogService extends Service {
    private boolean flag = true;
    public LogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        if (intent != null) {
            int pid = intent.getIntExtra("pid", 0);
            if (pid > 0 && flag) {
                LogcatHelper.getInstance(this).setPid(pid);
                LogcatHelper.getInstance(this).start();
                flag = false;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
