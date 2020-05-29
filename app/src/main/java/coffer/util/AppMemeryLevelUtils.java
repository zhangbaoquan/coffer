/**
 * File Name:AppMemeryLevelUtils.java <br/>
 * Package Name:com.zhangyue.iReader.cache.extend <br/>
 * Date:2015年6月2日<br/>
 * Copyright (c) 2015, zy All Rights Reserved.
 */

package coffer.util;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ClassName:AppMemeryLevelUtils <br/>
 * <p>
 * 运行时内存情况工具
 * <p>
 * Date: 2015年6月2日 <br/>
 * @author huangyahong
 */
public class AppMemeryLevelUtils {

    private static final String TAG = AppMemeryLevelUtils.class.getSimpleName();

    /** 低内存运行模式 **/
    public static final int LOW_MEMERY = 1;

    /** 中内存运行模式 **/
    public static final int MIDDLE_MEMERY = 2;

    /** 高内存运行模式 **/
    public static final int HIGHT_MEMERY = 3;

    /** 最小系统可用内存，用来判断运行时内存情况 **/
    private static final int MINIMUM_SYSTEM_AVAIALBE_MEMORY = 512;

    /** 最小应用可用内存，用来判断运行时内存情况 **/
    private static final int MINIMUM_APP_AVAIALBE_MEMORY = 96;

    // 判断运行时内存情况是 应用可用内存/系统可用内存 最小比例
    private static final float MINIMUM_RATIO = 0.3f;
    /** 低于值表明内存不充足，系统剩余内存占总内存的比值*/
    private static final float MINIMUM_RATIO1 = 0.2f;
    /** 高于此值表明内存充足，系统剩余内存占总内存的比值*/
    private static final float MAXIMUM_RATIO1 = 0.25f;
    /**
     * 获取APP内存模式
     * @author huangyahong
     * @return {@link #LOW_MEMERY} ,{@link #MIDDLE_MEMERY} ,{@link #HIGHT_MEMERY}
     */
    public static int getAppMemeryLevel(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            //系统最大可用内存
            final int avaialbeMemory = activityManager.getMemoryClass();
            activityManager.getRunningAppProcesses();
            final long avaialbeMemorySize = avaialbeMemory * 1024 * 1024;
            // 获得MemoryInfo对象
            MemoryInfo memoryInfo = new MemoryInfo();
            // 获得系统可用内存，保存在MemoryInfo对象上
            activityManager.getMemoryInfo(memoryInfo);
            // 获得系统内存信息
            final long systemAvaialbeMemorySize = memoryInfo.availMem;
            // 应用可用最大内存/系统剩余内存
            final float ratio = (float)((float)avaialbeMemorySize / (float)systemAvaialbeMemorySize);
            //系统剩余内存/系统总内存
            float ratio1 = 1.0f;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ratio1 = (float)systemAvaialbeMemorySize / (float)memoryInfo.totalMem;
            }
            // 是否是小屏幕
            boolean smallScreen = false;
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            if(displayMetrics.widthPixels <= 800) {
                smallScreen = true;
            }

//        Log.d(TAG, "totalMemory::" + Math.round((float)((float)getTotalMemory() / (float)(1024 << 20))) + "   1G::" + (1024 << 20) + "  memorySize::" + avaialbeMemory
//            + "  systemAvaialbeMemorySize::" + systemAvaialbeMemory + "  ratio::" + ratio);
             if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || (!smallScreen &&
                     (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
                             memoryInfo.totalMem / (1024 << 10) >= 1800 &&
                             ratio1 > MAXIMUM_RATIO1))) {
                // 分辨率大于720P并且大于2G总内存并且android 5.0以上
//            Log.d(TAG, "HIGHT_MEMERY");
                return HIGHT_MEMERY;
            } else if(memoryInfo.lowMemory ||
                     (ratio > MINIMUM_RATIO && ratio1 < MINIMUM_RATIO1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)) {
//            Log.d(TAG, "LOW_MEMERY");
                 //小于android 5.0 系统或者剩余内存占比低于0.2并剩余内存不足以启动3个同样的应用
                return LOW_MEMERY;
            }
//             else if(systemAvaialbeMemory > MINIMUM_SYSTEM_AVAIALBE_MEMORY && avaialbeMemory > MINIMUM_APP_AVAIALBE_MEMORY) {
//                 return MIDDLE_MEMERY;
//            }
//        Log.d(TAG, "MIDDLE_MEMERY");
            return MIDDLE_MEMERY;
        } catch(Exception e) {
            Log.e(TAG, "getAppMemeryLevel :: error", e);
        }
        
        return MIDDLE_MEMERY;
    }

    /**
     * isLowMemoryDevice:是否是低内存手机. <br/>
     * @author huangyahong
     * @return
     */
    public static boolean isLowMemoryDevice() {
        final int sdkInt = Build.VERSION.SDK_INT;
        return sdkInt < Build.VERSION_CODES.KITKAT || (sdkInt >= 19 && isLowRamDevice());
    }

    /**
     * isLowRamDevice:是否是低内存手机,4.4以上有效. <br/>
     * @author huangyahong
     * @return
     */
    private static boolean isLowRamDevice() {
        try {
            Class<?> SystemProperties = Class.forName("android.os.SystemProperties");
            Method method = SystemProperties.getMethod("get", new Class[]{String.class, String.class});
            String value = (String)method.invoke(null, "ro.config.low_ram", "false");
            return TextUtils.equals("true", value);
        } catch(IllegalArgumentException e) {
            Log.e(TAG, "isLowRamDevice ::IllegalArgumentException error", e);
        } catch(ClassNotFoundException e) {
            Log.e(TAG, "isLowRamDevice ::ClassNotFoundException error", e);
        } catch(NoSuchMethodException e) {
            Log.e(TAG, "isLowRamDevice ::NoSuchMethodException error", e);
        } catch(IllegalAccessException e) {
            Log.e(TAG, "isLowRamDevice ::IllegalAccessException error", e);
        } catch(InvocationTargetException e) {
            Log.e(TAG, "isLowRamDevice ::InvocationTargetException error", e);
        }
        return false;

    }

    /**
     * 获取手机内存大小
     * @return
     */
    private static long getTotalMemory() {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long totalMemory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
            // for(String num: arrayOfString) {
            // Log.i(str2, num + "\t");
            // }
            totalMemory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            // Log.d(TAG, "initial_memory::" + initial_memory);
            localBufferedReader.close();
        } catch(NumberFormatException e) {
            Log.e(TAG, "getTotalMemory ::NumberFormatException error", e);

        } catch(FileNotFoundException e) {
            Log.e(TAG, "getTotalMemory ::FileNotFoundException error", e);

        } catch(IOException e) {
            Log.e(TAG, "getTotalMemory ::IOException error", e);

        }

        return totalMemory;
    }

}
