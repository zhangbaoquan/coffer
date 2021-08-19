package networkDemo;

import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import networkDemo.okhttpDemo.CofferCacheInterceptor;
import networkDemo.okhttpDemo.OkHttpEventListener;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description： OkHttp 业务学习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class OkHttpActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_okhttp_main);
        findViewById(R.id.use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useOkHttp();
            }
        });
    }

    @Override
    public void initData() {

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
                cacheControl(new CacheControl.Builder().maxAge(60, TimeUnit.SECONDS).build()).
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
                CofferLog.D("coffer_push","response : "+response.body().string());
            }
        });
    }
}
