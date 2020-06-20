package main.java.coffer;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.dianping.logan.Logan;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import coffer.BaseActivity;
import coffer.androidDemo.AndroidMainActivity;
import coffer.androidjatpack.R;
import coffer.crashDemo.CrashCollectActivity;
import coffer.javaDemo.JavaMainActivity;
import coffer.jetpackDemo.JetpackMainDemo;
import coffer.pluginDemo.PluginMainActivity;
import coffer.util.CONSTANT;
import coffer.zy.NewTestMainActivity;
import networkDemo.NetWorkActivity;
import networkDemo.okhttpDemo.JobSchedulerService;

import static android.content.Intent.ACTION_DEFAULT;

public class MainActivity extends BaseActivity {

    private WindowManager wm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logan.w("onCreate",1);
        Log.i(CONSTANT.COFFER_TAG,"getFilesDir: "+getFilesDir());
        wm = getWindowManager();
        initView();

        // deeplink 跳转
        findViewById(R.id.b15).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "iqiyi://mobile/player?aid=239741901&tvid=14152064500&ftype=27&subtype=vivoqd_2843";
//                String url = "ireader://com.chaozh.iReader/readbook?bookid=11591589";
//                String url = "ireader://com.oppo.reader/openurl?url=https%3A%2F%2Fah2.zhangyue.com%" +
//                        "2Fzybook3%2Fapp%2Fapp.php%3Fca%3DChannel.Index%26pk%3Dqd%26key%3Dch_free%26a0%3Dbanner_oppo_sd_mfpd";
//                String url = "ireader://com.oppo.reader/openurl?url=https%3A%2F%2Fah2.zhangyue.com%" +
//                        "2Fzyvr%2Frender%3Fid%3D10608%26a0%3Dpush_oppo_sd_wbhs&fromname=应用商店&flags=4&from=com.oppo.market";
                String url = "ireader://com.oppo.reader/openurl?url=https%3A%2F%2Fah2.zhangyue.com%2Fzybook3%2Fapp%2" +
                        "Fapp.php%3Fca%3DChannel.Index%26pk%3Dqd%26key%3Dch_feature%26a0%3Dtoufang01s&fromname=浏览器&flags=4&" +
                        "from=com.heytap.browser";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(ACTION_DEFAULT, uri);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        registerLifecycleCallbacks();
    }

    private void initView(){
        // 插件
        findViewById(R.id.b0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PluginMainActivity.class);
                startActivity(intent);
            }
        });

        // java 综合
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JavaMainActivity.class);
                startActivity(intent);
            }
        });

        // android 综合
        findViewById(R.id.b5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AndroidMainActivity.class);
                startActivity(intent);
            }
        });

        // 崩溃日志抓取
        findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CrashCollectActivity.class);
                startActivity(intent);
            }
        });

        // 网络框架学习
        findViewById(R.id.b8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NetWorkActivity.class);
                startActivity(intent);
            }
        });

        // Jetpack 组件练习
        findViewById(R.id.b11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JetpackMainDemo.class);
                startActivity(intent);
            }
        });

        // 新功能测试
        findViewById(R.id.b12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewTestMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerLifecycleCallbacks(){
        AtomicInteger mCount = new AtomicInteger();
        Log.e(CONSTANT.COFFER_TAG, "mCount : " + mCount.toString());
        // 下面的这个监控方法可以写在BaseActivity 中
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                // 在前台
            }

            @Override
            public void onActivityPaused(Activity activity) {
                // 在后台
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        // 创建一个定时任务
        Executors.newScheduledThreadPool(1).schedule(new Runnable() {
            @Override
            public void run() {
                // 统计30s之间消耗的流量
                long netUse = getNetStats(System.currentTimeMillis() - 30 * 1000, System.currentTimeMillis());
                // 判断当前是前台还是后台
            }
        }, 30, TimeUnit.SECONDS);
    }

    /**
     * 获取当前的网络状态，监控流量，以WIFI为例这里面的参数，可以配置在CPS那，这样可以从服务端那配置
     *
     * @param startTime
     * @param endTime
     */
    private long getNetStats(long startTime, long endTime) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return 0;
        }
        // 接收
        long netDataRx = 0;
        // 发送
        long netDataTx = 0;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return 0;
        }
        String subId = telephonyManager.getSubscriberId();
        NetworkStatsManager manager = (NetworkStatsManager) getSystemService(Context.NETWORK_STATS_SERVICE);
        NetworkStats networkStats = null;
        NetworkStats.Bucket bucket = new NetworkStats.Bucket();
        try {
            networkStats = manager.querySummary(NetworkCapabilities.TRANSPORT_WIFI, subId, startTime, endTime);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket);
            int uid = bucket.getUid();
            // 最好先判断下当前消耗的流量是自己APP的，可以根据UID 来判断（根据包名来获取UID），然后对比当前的UID和uid是否相同

            netDataRx += bucket.getRxBytes();
            netDataTx += bucket.getTxBytes();
        }
        return netDataRx + netDataTx;

    }

    private void startJobScheduler(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(),
                    JobSchedulerService.class.getName()));
            // 环境在充电且WIFI下
            builder.setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED );
            jobScheduler.schedule(builder.build());

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        addWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            addWindow();
        }
    }

    /**
     * 新增一个Window，注意添加时机，必须要等activity的生命周期函数全部执行完毕之后，需要依附的View加载完成了才可以。
     */
    private void addWindow(){
        Button btn_click= new Button(this);
        btn_click .setText("浮窗");
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //Flag参数表示window的属性，通过这些选项控制Window的显示特性：
        //1.FLAG_NOT_FOCUSABLE:表示窗口不需要获取焦点，也不需要接收各种事件，这属性会同时启动FLAG_NOT_TOUCH_MODAL，最终事件会传递给下层的具体焦点的window
        //2.FLAG_NOT_TOUCH_MODAL:系统会将当前window区域以外的单击事件传递给底层的Window，当前的Window区域以内的单机事件自己处理，这个标记很重要，一般来说都需要开启，否则其他windows无法接受到点击事件。
        mParams.gravity = Gravity.CENTER;
        mParams.token = getWindow().getDecorView().getWindowToken();
        // 仅在当前Activity上显示
        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        //emm..这个是Type参数。
        mParams.x = 0;
        mParams.y = 0;
        wm.addView(btn_click, mParams);

    }
}
