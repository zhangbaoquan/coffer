package coffer.firebaseDemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * author      : coffer
 * date        : 8/19/21
 * description : 获取FCM注册令牌
 * Firebase Cloud Messaging (FCM) 是一种跨平台消息传递解决方案，可供您可靠地传递消息。
 * Reviewer    :
 */
public class FcmUtil {
    public FcmUtil() {
    }

    public static void saveFcmToken(Context context, String token) {
        if (!TextUtils.equals(getFcmToken(context), token)) {
            saveSyncStatus(context, false);
        }

        SharedPreferences sp = context.getSharedPreferences("fcm_sp", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public static String getFcmToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("fcm_sp", 0);
        return sp.getString("token", "");
    }

    public static void saveSyncStatus(Context context, boolean isSuccessful) {
        SharedPreferences sp = context.getSharedPreferences("fcm_sp", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("sync_status", isSuccessful);
        editor.commit();
    }

    public static boolean getSyncStatus(Context context) {
        SharedPreferences sp = context.getSharedPreferences("fcm_sp", 0);
        return sp.getBoolean("sync_status", false);
    }
}
