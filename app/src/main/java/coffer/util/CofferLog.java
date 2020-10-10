package coffer.util;

import android.util.Log;

import coffer.androidjatpack.BuildConfig;

/**
 * @author：张宝全
 * @date：2020/6/2
 * @Description：日志
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferLog {
    private static boolean sDebuggable = BuildConfig.DEBUG;

    private CofferLog(){
    }

    public static void D(String tag, String msg) {
        if (sDebuggable && msg != null) {
            Log.d(tag, msg);
        }
    }

    public static void I(String tag, String msg) {
        if (sDebuggable && msg != null) {
            Log.i(tag, msg);
        }
    }

    public static void e(Throwable tr) {
        if (sDebuggable && tr != null) {
            Log.e(CONSTANT.COFFER_TAG, "error is ", tr);
        }
    }

}
