package coffer.androidDemo.router;

import android.content.Intent;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import coffer.BaseDefaultActivity;
import coffer.androidDemo.router.annotation.HelloAnnotation;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;

/**
 * @author：张宝全
 * @date：5/8/21
 * @Description：HelloAnnotation 是自定义注解
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

/**
 * 界面路径的注解@Route(path = "/xx/xx") 路径至少要两层
 */
@Route(path = "/coffer/router/ARouterMainActivity")
@HelloAnnotation(say = "hello world")
public class ARouterMainActivity extends BaseDefaultActivity {

    private static final String TAG = "ARouterMainActivity";

    /**
     * Autowired 声明注解参数，coffer_key 是从别的界面跳转进来时使用的key 【这个注解现在无效了】，
     */
//    @Autowired(name = "coffer_key")
//    String coffer_key;

    @Override
    public void initView() {
        setContentView(R.layout.activity_arouter_main);

        // Autowired 注解无效了，还是使用getIntent 方式
        Intent intent = getIntent();
        if (intent != null){
            String str = intent.getStringExtra("coffer_key");
            CofferLog.D(TAG,str);
        }

    }

    @Override
    public void initData() {
        // 解析自定义注解，获取当前类上的注解对象
        HelloAnnotation annotation = ARouterMainActivity.class.getAnnotation(HelloAnnotation.class);
        // 调用注解方法
        Log.i("hahhoo",annotation.say());
    }
}
