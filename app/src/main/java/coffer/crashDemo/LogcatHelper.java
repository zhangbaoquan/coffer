package coffer.crashDemo;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import coffer.util.FileUtils;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description： Log 日志统计保存
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class LogcatHelper {

    /**
     * 务必使用volatile 避免指令重排序
     */
    private volatile static LogcatHelper instance;
    /**
     * 写日志线程
     */
    private LogDumper mLogDumper = null;
    /**
     * 记录进程ID
     */
    private int mPId;
    private Context mContext;

    public static LogcatHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (LogcatHelper.class){
                if (instance == null){
                    instance = new LogcatHelper();
                    instance.mContext = context;
                }
            }
        }
        return instance;
    }

    private LogcatHelper() {
//        mPId = android.os.Process.myPid();
    }

    public void setPid(int pid) {
        mPId = pid;
    }

    public void start() {
        if (mLogDumper == null){
            mLogDumper = new LogDumper(String.valueOf(mPId), getLogFile());
        }
        mLogDumper.start();
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private File mFile;

    /**
     * 获取一个日志文件
     * @return 日志文件
     */
    public File getLogFile() {
        if (mFile == null) {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String path = FileUtils.getCrashPath() + "/"+sdf.format(date)+".txt";
            mFile = FileUtils.createFile(path);
        }
        return mFile;
    }

    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private String mPID;
        private FileOutputStream out = null;

        public LogDumper(String pid, File file) {
            mPID = pid;
            try {
                // 初始化文件输出流，可追加内容
                out = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */

            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            // cmds = "logcat  | grep \"(" + mPID + ")\"";//打印所有日志信息
            // cmds = "logcat -s way";//打印标签过滤信息
            cmds = "logcat *:d *:i";

        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                // 执行 查询日志的adb 命令
                logcatProc = Runtime.getRuntime().exec(cmds);
                // 获取进程的输入流
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null && line.contains(mPID)) {
                        // 仅将和当前进程相关的日志写入
                        out.write((line + "\n").getBytes());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }
            }
        }
    }
}
