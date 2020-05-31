package networkDemo.okhttpDemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {
        // 此处执行在主线程
        // 模拟一些处理：批量的网络请求，APM日志上报
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
