package coffer.util;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * @author：张宝全
 * @date：2019-08-23
 * @Description： 文件操作辅助类
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class FileUtils {

    private static String mSDCardDir = "";

    @SuppressLint("SdCardPath")
    public static String getSDCardDir(){
        if (!TextUtils.isEmpty(mSDCardDir)){
            return mSDCardDir;
        }
        if (hasSdcard()){
            mSDCardDir = Environment.getExternalStorageDirectory().toString();
        }else {
            mSDCardDir = "/sdcard";
        }
        Log.i(CONSTANT.COFFER_TAG,"mSDCardDir : "+mSDCardDir);
        return mSDCardDir;
    }

    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
           return true;
        }
        return false;
    }

    /**
     * 存储图片的路径
     * @return
     */
    public static String getPicPath(){
        String path = getSDCardDir() +"/coffer/pic/";
        Log.i(CONSTANT.COFFER_TAG,"getPicPath : "+path);
        return path;
    }

    /**
     * 存储apk的路径
     * @return
     */
    public static String getApkPath(){
        String path = getSDCardDir() +"/coffer/apk/";
        Log.i(CONSTANT.COFFER_TAG,"getApkPath : "+path);
        return path;
    }

    /**
     * 存储文档的路径
     * @return
     */
    public static String getTxtPath(){
        String path = getSDCardDir() +"/coffer/txt/";
        Log.i(CONSTANT.COFFER_TAG,"getTxtPath : "+path);
        return path;
    }

    /**
     * 崩溃日志路径
     * @return 路径
     */
    public static String getCrashPath(){
        String path = getSDCardDir() + "/coffer/crash/";
        Log.i(CONSTANT.COFFER_TAG,"getCrashPath : "+path);
        return path;
    }

    /**
     * 创建文件，如果文件已经存在不操作直接返回
     *
     * @param path 路径
     * @return File：创建成功创；null：建失败
     */
    public static File createFile(String path){
        File file = new File(path);
        if (file.exists()){
            // 如果文件存在，则直接返回
            return file;
        }

        try {
            File parentFile = file.getParentFile();
            boolean existDir = parentFile.exists();
            if (!existDir){
                existDir = parentFile.mkdirs();
            }
            if(existDir){
                boolean newFile = file.createNewFile();
                if (newFile){
                    return file;
                }else {
                    return null;
                }
            }
        }catch (IOException e){
            Log.e(CONSTANT.COFFER_TAG,"e.getMessage() : "+e.getMessage());
        }
        return file;
    }

}
