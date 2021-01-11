package coffer.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.List;

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
    private PackageManager mPackageManager;

    private String TARGET_INTENT_NAME;


    public CofferInstrumentation(Instrumentation mBase, PackageManager packageManager) {
        this.mBase = mBase;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {
        List<ResolveInfo> infos = mPackageManager.queryIntentActivities(intent,PackageManager.MATCH_ALL);
        if (infos == null || infos.size() == 0){
            // 将要启动的Activity 先存储起来，方便后面替换
            intent.putExtra(TARGET_INTENT_NAME,intent.getComponent().getClassName());
            // 将要送去AMS验证的Activity换成占坑的
            intent.setClassName(who,"com.coffer.subActivity");
        }
        try {
            // 反射调用execStartActivity
            @SuppressLint("DiscouragedPrivateApi") Method execMethod = Instrumentation.class.getDeclaredMethod("execStartActivity",
                    Context.class,IBinder.class,IBinder.class,Activity.class,Intent.class,int.class,Bundle.class);
            return (ActivityResult) execMethod.invoke(mBase,who,contextThread,token,target,intent,requestCode,options);
        }catch (Exception e){

        }

        return null;
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
        String intentName = intent.getStringExtra(TARGET_INTENT_NAME);
        if (TextUtils.isEmpty(intentName)){
            return super.newActivity(cl, intentName, intent);
        }
        return super.newActivity(cl, className, intent);
    }
}
