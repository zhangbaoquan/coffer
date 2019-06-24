package coffer.util;

import android.util.Log;
import java.text.SimpleDateFormat;
import coffer.androidjatpack.BuildConfig;

/**
 * @author：张宝全
 * @date：2019-06-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class LOG {

    /**
     * 类名
     */
    private static String sClassName;

    /**
     * 方法名
     */
    private static String sMethodName;

    /**
     * 行数
     */
    private static int sLineNumber;

    private static boolean sDebuggable = BuildConfig.DEBUG;

    private static boolean sDefaultTag = false;

    private static String sDefaultTagStr = "iReader_log";

    /** 如果要打账号相关的log，而且需要写入mt log并上传的话，用这个tag，tag写的奇怪一点，不容易被不知道的人偶然写成一样的*/
    public static final String MT_TAG_ACCOUNT = "mt_tag_account";

    /** 如果要打插件框架相关的log，而且需要写入mt log并上传的话，用这个tag，tag写的奇怪一点，不容易被不知道的人偶然写成一样的*/
    public static final String MT_TAG_PLUGIN = "mt_tag_plugin";

    /** mt log中的所有记录，都要有时间戳，这是时间戳的格式*/
    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private LOG() {
    }

    public static void useDefaultTag() {
        sDefaultTag = true;
    }

    public static void setDefaultTag(String defaultTag) {
        sDefaultTagStr = defaultTag;
    }

    public static void setDebuggable(boolean isDebuggable) {
        sDebuggable = isDebuggable;
    }

    private static String createLog(String log) {

        String buffer = "[" +
                sMethodName +
                ":" +
                sLineNumber +
                "]" +
                log;

        return buffer;
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        sClassName = sElements[1].getFileName();
        sMethodName = sElements[1].getMethodName();
        sLineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.e(sDefaultTagStr, createLog(message));
            } else {
                Log.e(sClassName, createLog(message));
            }
        }
    }

    public static void i(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.i(sDefaultTagStr, createLog(message));
            } else {
                Log.i(sClassName, createLog(message));
            }
        }
    }

    public static void d(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.d(sDefaultTagStr, createLog(message));
            } else {
                Log.d(sClassName, createLog(message));
            }
        }
    }

    public static void v(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.v(sDefaultTagStr, createLog(message));
            } else {
                Log.v(sClassName, createLog(message));
            }
        }
    }

    public static void w(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.w(sDefaultTagStr, createLog(message));
            } else {
                Log.w(sClassName, createLog(message));
            }
        }
    }

    public static void wtf(String message) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.wtf(sDefaultTagStr, createLog(message));
            } else {
                Log.wtf(sClassName, createLog(message));
            }
        }
    }

    public static void e(String message, Throwable tr) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.e(sDefaultTagStr, createLog(message));
            } else {
                Log.e(sClassName, createLog(message), tr);
            }
        }
    }

    public static void wtf(String message, Throwable tr) {
        if (sDebuggable) {
            getMethodNames(new Throwable().getStackTrace());
            if (sDefaultTag) {
                Log.wtf(sDefaultTagStr, createLog(message));
            } else {
                Log.wtf(sClassName, createLog(message), tr);
            }
        }
    }

    /******************** 一般调试 *********************/
    public static void V(String tag, String msg) {
        if (sDebuggable && msg != null) {
            Log.v(tag, msg);
        }
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

    public static void W(String tag, String msg) {
        if (sDebuggable && msg != null) {
            Log.w(tag, msg);
        }
    }

    public static void E(String tag, String msg) {
        if (sDebuggable && msg != null) {
            Log.e(tag, msg);
        }
//        monitorLog(tag, msg);
    }

    public static void E(String tag, String msg, Throwable tr) {
        if (sDebuggable && msg != null && tr != null) {
            Log.e(tag, msg, tr);
        }
    }

    public static void e(Throwable tr) {
        if (sDebuggable && tr != null) {
            Log.e("LOG", "error is ", tr);
        }
    }

    private static long time;
    private static long initTime;
    private static StringBuilder stringBuilder=new StringBuilder();

    public static void startRecord(String name) {
        initTime = System.currentTimeMillis();
        stringBuilder=new StringBuilder();
        time(name);
    }

    public static void time(String name) {
        long curTime = System.currentTimeMillis();
        if (initTime != 0) {
//            stringBuilder.append("\n").append(name + "\t" + (curTime - time) + "\t" + (curTime -
//                    initTime));
//            E("Chw", name + "\t" + (curTime - time) + "\t" + (curTime - initTime));
            time = curTime;
        }
    }
    public static void printTime(){
        E("Chw",stringBuilder.toString());
    }

    public static void WTF(String tag, String msg, Throwable tr) {
        if (sDebuggable && msg != null) {
            Log.wtf(tag, msg, tr);
        }
    }

}
