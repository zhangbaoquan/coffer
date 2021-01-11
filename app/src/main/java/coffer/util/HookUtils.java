package coffer.util;

import android.app.Application;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author：张宝全
 * @date：2020/8/22
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class HookUtils {

    private static final String TAG = "Hooker";

    public static void hookInstrumentation(Application application){
        Class<?> activityThread = null;
        try {
            activityThread =Class.forName("android.app.ActivityThread");
            Method currentActivityThread = activityThread.getDeclaredMethod("currentActivityThread");
            currentActivityThread.setAccessible(true);

            // 获取当前的系统的ActivityThread 对象
            Object activityThreadObject = currentActivityThread.invoke(activityThread);

            // 获取Instrumentation 对象
            Field mInstrumentation = activityThread.getDeclaredField("mInstrumentation");
            mInstrumentation.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentation.get(activityThreadObject);
            CofferInstrumentation customInstrumentation = new CofferInstrumentation(instrumentation,application.getPackageManager());
            // 将我们自定义的 Instrumentation 把系统的替换掉
            mInstrumentation.set(activityThreadObject,customInstrumentation);
        }catch (Exception e){
            e.getMessage();
        }
    }
}
