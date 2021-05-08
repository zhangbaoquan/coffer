package coffer.androidDemo.router.AInterceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * @author：张宝全
 * @date：5/8/21
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
@Interceptor(priority = 1)
public class UseARouterInterceptor implements IInterceptor {

    @Override
    public void init(Context context) {
        Log.i("hahan","ARouter 拦截器初始化");
    }

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String name = Thread.currentThread().getName();
        Log.i("hahan","拦截器执行，当前线程名字 ： "+name);
    }
}
