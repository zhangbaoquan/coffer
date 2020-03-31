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
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import com.dianping.logan.Logan;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import coffer.BaseActivity;
import coffer.adDemo.ViewPagerBannerActivity;
import coffer.androidjatpack.R;
import coffer.animDemo.AnimActivity;
import coffer.drawViewDemo.CofferViewActiviy;
import coffer.drawViewDemo.CofferViewActiviy2;
import coffer.drawViewDemo.CofferViewActiviy3;
import coffer.fileDemo.FileActivity;
import coffer.hookDemo.InvokeActivity;
import coffer.javaDemo.reflectdemo.ReflectActivity;
import coffer.messageDemo.BridgeService;
import coffer.messageDemo.MessageTestActivity;
import coffer.okhttpDemo.CofferCacheInterceptor;
import coffer.okhttpDemo.JobSchedulerService;
import coffer.okhttpDemo.OkHttpEventListener;
import coffer.pluginDemo.PluginMainActivity;
import coffer.zy.ZyMainActivity;
import coffer.zy.VivoAdBannerMainActivity;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends BaseActivity {

    private WindowManager wm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logan.w("onCreate",1);
        Logan.w("onCreate",1);
        Logan.w("onCreate",1);
        wm = getWindowManager();
        // 掌阅（浏览器插件4.4版本）
        findViewById(R.id.b0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ZyMainActivity.class);
                startActivity(intent);
            }
        });

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
        // 使用网络
        findViewById(R.id.b8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useOkHttp();
            }
        });

        // 消息机制
        findViewById(R.id.b9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessageTestActivity.class);
                startActivity(intent);
            }
        });

        // 延时初始化ContentProvider
        findViewById(R.id.b10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BridgeService.class);
                startActivity(intent);
            }
        });

        // Banner 广告
        findViewById(R.id.b11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPagerBannerActivity.class);
                startActivity(intent);
            }
        });

        // 掌阅（基于7.15多进程版）
        findViewById(R.id.b12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VivoAdBannerMainActivity.class);
                startActivity(intent);
            }
        });

        // 自定义View的滑动和绘制
        findViewById(R.id.b13).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CofferViewActiviy2.class);
                startActivity(intent);
            }
        });

        // 弹性RecycleView 的实现
        findViewById(R.id.b14).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CofferViewActiviy3.class);
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
        //缓存文件夹
        File cacheFile = new File(getExternalCacheDir().toString(),"cache");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        Cache cache = new Cache(cacheFile,cacheSize);

        final Request request = new Request.Builder().
                get().
                url("http://ah2.zhangyue.com/zycl/api/ad/config?zysid=2f9eff97ca116b8918dbbee8973a2cd1&usr=i1026460967&rgt=7&p1=WGfWIsb%2BpksDAJ%2BEB%2BQHjfEN&pc=10&p2=107180&p3=17180003&p4=501659&p5=19&p6=&p7=__629828015653455&p9=2&p12=&p16=OPPO+R11s&p21=3&p22=8.1.0&p25=62800&p26=27&pluginVersion=150&apiVersion=1&p2=107180&videoCode=FREE_SIGN_VIDEO%2CLOCALBOOK_CHAPTEREND%2CLOCAL_READ_TEXT%2CREAD_CPT_END%2CREAD_TEXT%2CVIDEO_CHAPTEREND%2CVIDEO_CHAPTEREND_FREE%2CVIDEO_EXITWINDOW%2CVIDEO_RECHARGE&usr=i1026460967&bizCode=BOTTOM_AD%2CBOY_BANNER%2CCOMIC_BANNER%2CFREE_BOOKDETAIL_AD%2CFREE_SIGN_VIDEO%2CGIRL_BANNER%2CINSERTCONTENT_BOTTOM%2CINSERTCONTENT_TOP%2CLISTENING_BANNER%2CLOCALBOOK_CHAPTEREND%2CLOCAL_READ_TEXT%2COPPO_PD%2COPPO_SJ%2CPAGES%2CPOPUP_BOOKSHELF%2CPOPUP_BOOKSTORE%2CPOPUP_WELFARE%2CPUBLISHING_BANNER%2CREAD_CPT_END%2CREAD_CPT_TEXT%2CREAD_CPT_TOP%2CREAD_END%2CREAD_TEXT%2CSCREEN%2CSEARCH%2CSELECT_BANNER%2CSIGN%2CVIDEO_CHAPTEREND%2CVIDEO_CHAPTEREND_FREE%2CVIDEO_EXITWINDOW%2CVIDEO_RECHARGE%2CWELFARE%2Cvideo_book%2Cvideo_bookshelf%2Cvideo_sign&sign=BH18%2FrttFmMGMIxz0exNOakU%2FHDVhBh0Fv3U0ftWfSSPC5G4oTaBlVyugqehxWUUr1LPSNMdabk4dVQF%2FcfFIe1qkOZkdG42nKyQt4rMl1Rc6mq0mxsUkjFNaoIHFijS6Wfz%2F94t1p54yti799MDRvvdy%2BVaijMeQAu8piLCIB4%3D&timestamp=1571020223121").
                addHeader("User-Agent","coffer").
                cacheControl(new CacheControl.Builder().maxAge(60,TimeUnit.SECONDS).build()).
//                addHeader("Cache-Control","max-age=60").
                addHeader("If-Modified-Since","Mon, 14 Oct 2019 13:47:08GMT").
                build();
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        // 配置自定义的拦截器、eventListener、cache等
        client.eventListenerFactory(OkHttpEventListener.FACTORY);
        client.addInterceptor(new CofferCacheInterceptor());
        client.cache(cache);

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
