package main.java.coffer;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.dianping.logan.Logan;
import com.tencent.mmkv.MMKV;

import coffer.BaseActivity;
import coffer.BaseDefaultActivity;
import coffer.androidDemo.AndroidMainActivity;
import coffer.androidjatpack.R;
import coffer.crashDemo.CrashCollectActivity;
import coffer.javaDemo.JavaMainActivity;
import coffer.jetpackDemo.JetpackMainDemo;
import coffer.pluginDemo.PluginMainActivity;
import coffer.util.CONSTANT;
import coffer.util.CofferLog;
import coffer.zy.NewTestMainActivity;
import networkDemo.NetWorkActivity;
import static android.content.Intent.ACTION_DEFAULT;

public class MainActivity extends BaseActivity {

    @Override
    public void initView(){
        setContentView(R.layout.activity_main);
        Logan.w("onCreate",1);
        Log.i(CONSTANT.COFFER_TAG,"getFilesDir: "+getFilesDir());
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

    @Override
    public void initData() {
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
//                String url = "ireader://com.oppo.reader/openurl?url=https%3A%2F%2Fah2.zhangyue.com%2Fzybook3%2Fapp%2" +
//                        "Fapp.php%3Fca%3DChannel.Index%26pk%3Dqd%26key%3Dch_feature%26a0%3Dtoufang01s&fromname=浏览器&flags=4&" +
//                        "from=com.heytap.browser";
//                String url = "ireaderplugin://com.coloros.browser/readbook?bookid=11006182";
//                String url = "ireaderplugin://com.coloros.browser/maintab?nightmode=100&tabindex=1";
//                String url = "dididriver://com.sdu.didi.gsui.DidiMsgActivity";
                String url = "unidriver://web?url=https://page.didiglobal.com/driver-page/mid-page/";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(ACTION_DEFAULT, uri);
                try {
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        useMkv();
    }

    Handler handler = new Handler();
    /**
     *  使用MKV 存储小数据，替换SharedPreferences
     */
    private void useMkv(){
        MMKV mmkv = MMKV.defaultMMKV();
        // 不用再如SharedPreferences一样调用apply或commit：非常方便
        mmkv.encode("bool",true);
        mmkv.encode("int",1);
        mmkv.decodeBool("hehe");
        CofferLog.I("lalal","info: "+mmkv.decodeBool("bool"));
        CofferLog.I("lalal","info: "+mmkv.decodeInt("int"));
        // 删除数据
        mmkv.clear();
        // 查询数据是否存在
        boolean hasBool = mmkv.containsKey("bool");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CofferLog.D("coffer_tag","嘿嘿");
            }
        },500);
        Message message = Message.obtain();
        message.what = 1;
        message.obj = "coffer";
        handler.sendMessage(message);
    }

    @Override
    protected void onPause() {
        super.onPause();
        CofferLog.D("coffer_tagM","onPause");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        CofferLog.D("coffer_tagM","onAttachedToWindow");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CofferLog.D("coffer_tagM","onDetachedFromWindow");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 当前界面在在前后台状态改变时会触发，TRUE表示前台，FALSE是后台
        CofferLog.D("coffer_tagM","onWindowFocusChanged hasFocus : "+hasFocus);
    }
}
