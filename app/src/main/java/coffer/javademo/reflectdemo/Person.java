package coffer.javademo.reflectdemo;

import android.util.Log;

/**
 * @author：张宝全
 * @date：2019-05-15
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class Person {

    private static final String TAG = "reflect-tag";

    private String name;

    public Person(){
        Log.e(TAG,"创建了一个无参Person实例");
    }

    public Person(String name){
        Log.e(TAG,"创建了有参"+name);
    }

    private void setName1(){
        Log.e(TAG,"调用了无参数方法");
    }

    protected void setName2(String name){
        Log.e(TAG,"调用了有参："+name);
    }
}
