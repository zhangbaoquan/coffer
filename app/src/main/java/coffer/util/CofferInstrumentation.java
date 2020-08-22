package coffer.util;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

import coffer.androidDemo.animdemo.PropertyAnimActivity;

/**
 * @author：张宝全
 * @date：2020/8/22
 * @Description： 将要启动的Activity替换成我们自定义的Activity.
 *
 *  应用在启动一个新的Activity时，会执行ActivityThread # performLaunchActivity 方法。在这个方法中有
 *             java.lang.ClassLoader cl = appContext.getClassLoader();
 *             activity = mInstrumentation.newActivity(
 *                     cl, component.getClassName(), r.intent);
 *             StrictMode.incrementExpectedActivityCount(activity.getClass());
 *             r.intent.setExtrasClassLoader(cl);
 *             r.intent.prepareToEnterProcess();
 *             if (r.state != null) {
 *                 r.state.setClassLoader(cl);
 *             }
 *
 *  这里要做的就是重写 Instrumentation{{@link #newActivity(ClassLoader, String, Intent)}}方法
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class CofferInstrumentation extends Instrumentation{

    private Instrumentation mBase;


    public CofferInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        try {
            //这里需要setExtrasClassLoader 不然的话，getParecleable 对象可能会拿不到
            //很多hook Instrumentation的人都不知道。
            // 这里try catch 是防止恶意攻击  导致android.os.BadParcelableException: ClassNotFoundException when unmarshalling
            intent.setExtrasClassLoader(cl);
        }catch (Exception e){
            e.printStackTrace();
        }
        // 这里直接让它跳转到属性动画的Activity，这里设置自己的跳转逻辑，将占坑的替换掉，通过解析intent 里的数据
//        if (true){
//            return super.newActivity(cl, PropertyAnimActivity.class.getName(), intent);
//        }
        return super.newActivity(cl, className, intent);
    }
}
