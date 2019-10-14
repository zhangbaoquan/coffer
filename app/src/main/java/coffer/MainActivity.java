package main.java.coffer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.usage.NetworkStats;
import android.app.usage.NetworkStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import coffer.androidjatpack.R;
import coffer.animDemo.AnimActivity;
import coffer.drawViewDemo.CofferViewActiviy;
import coffer.fileDemo.FileActivity;
import coffer.hookDemo.InvokeActivity;
import coffer.javaDemo.reflectdemo.ReflectActivity;
import coffer.okhttpDemo.JobSchedulerService;
import coffer.okhttpDemo.OkHttpEventListener;
import coffer.pluginDemo.PluginMainActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * 声明一个数组将所有需要申请的权限都放入
     */
    private String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 创建一个mPermissionList，逐个判断哪些权限未授权，将未授权的权限存储到mPermissionList中
     */
    List<String> mPermissionList = new ArrayList<>();

    /**
     * 权限请求码
     */
    private final int mRequestCode = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
        // 属性动画
        findViewById(R.id.b1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AnimActivity.class);
                startActivity(intent);
            }
        });

        // 反射
        findViewById(R.id.b2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReflectActivity.class);
                startActivity(intent);
            }
        });

        // 自定义View 绘制
        findViewById(R.id.b3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CofferViewActiviy.class);
                startActivity(intent);
            }
        });

        // 动态代理
        findViewById(R.id.b4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InvokeActivity.class);
                startActivity(intent);
            }
        });

        // 文件操作
        findViewById(R.id.b6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(intent);
            }
        });
        // 加载SD卡上的APK
        findViewById(R.id.b7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PluginMainActivity.class);
                startActivity(intent);
            }
        });
        AtomicInteger mCount = new AtomicInteger();
        Log.e("ioioioii", "mCount : " + mCount.toString());
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
            JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(), JobSchedulerService.class.getName()));
            // 环境在充电且WIFI下
            builder.setRequiresCharging(true)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED );
            jobScheduler.schedule(builder.build());

        }
    }

    private void useOkHttp() {
        final Request request = new Request.Builder().
                get().
                url("www.baidu.com").
                build();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        // 配置自定义的拦截器、eventListener、cache等
        client.eventListenerFactory(OkHttpEventListener.FACTORY);

        OkHttpClient okHttpClient = client.build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    /***************   权限申请    **************/

    private void initPermission() {
        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);//添加还未授予的权限到mPermissionList中
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {//有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 6.不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "coffer.androidjatpack";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.
                                    ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            MainActivity.this.finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    /**
     * 5.请求权限后回调的方法
     *
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限
     *                     名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;//有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
        }
        if (hasPermissionDismiss) {//如果有没有被允许的权限
            showPermissionDialog();
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Toast.makeText(MainActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }
    }

}
