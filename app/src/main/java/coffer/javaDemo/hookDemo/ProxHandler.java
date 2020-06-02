package coffer.javaDemo.hookDemo;

import android.view.View;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author：张宝全
 * @date：2019-07-07
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ProxHandler implements InvocationHandler {

    private View.OnClickListener mListener;

    public ProxHandler(View.OnClickListener listener){
        this.mListener = listener;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 这个地方可以做数据埋点等操作
        return method.invoke(mListener,args);
    }
}
