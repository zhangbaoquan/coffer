package coffer.reflectdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2019-05-15
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ReflectActivity extends AppCompatActivity {

    private static final String TAG = "reflect-tag";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_main);

        // 1、获取 目标类型的Class对象
        // 2、通过 Class 对象分别获取Constructor类对象、Method类对象 & Field 类对象
        // 3、通过 Constructor类对象、Method类对象 & Field类对象分别获取类的构造函数、方法&属性的具体信息，并进行后续操作

        // 利用反射获取类的属性 & 赋值
        test1();

        // 利用反射调用类的构造函数
        test2();

        // 利用反射调用类对象的方法
        test3();
    }

    /**
     * 利用反射获取类的属性 & 赋值
     */
    private void test1(){
        try {
            // 1、获取Person类的Class对象
            Class personClass = Class.forName("coffer.reflectdemo.Person");
            // 2、通过Class对象创建Person对象
            Object person = personClass.newInstance();
            // 3、通过Class对象获取Person类的name属性
            Field field = personClass.getDeclaredField("name");
            // 4、设置私有访问权限
            field.setAccessible(true);
            // 5、对新创建的person对象设置name值
            field.set(person,"啦啦啦");

            Log.e(TAG,field.get(person)+"");

        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    /**
     * 利用反射调用类的构造函数
     */
    private void test2(){
        try {
            Class personClass = Class.forName("coffer.reflectdemo.Person");
            // 通过Class对象获取Constructor类对象，从而调用无参构造方法
            Object obj1 = personClass.getConstructor().newInstance();

            // 通过Class对象获取Constructor类对象（传入参数类型），从而调用有参构造方法
            Object obj2 = personClass.getConstructor(String.class).newInstance("欢欢");

        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    /**
     * 利用反射调用类对象的方法
     */
    private void test3(){
        try {
            Class personClass = Class.forName("coffer.reflectdemo.Person");
            Object person = personClass.newInstance();

            // 调用无参方法
            Method method1 = personClass.getDeclaredMethod("setName1");
            // 这里反射调用的private 方法，所以需要打开权限
            method1.setAccessible(true);
            method1.invoke(person);

            // 调用有参数方法
            Method method2 = personClass.getDeclaredMethod("setName2",String.class);
            method2.invoke(person,"嘿嘿");

        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

    private void step1(){
        // 方式一、使用Object.getClass()返回一个Class类型的实例
        Activity coffer = this;
        Class<?> classType1 = coffer.getClass();
        Log.e(TAG,classType1+"");

        // 方式二、T.class ,T = 任意Java类型
        Class<?> classType2 = Intent.class;
        Log.e(TAG,classType2+"");

        // 方式三、Class.forName，里面传入类所在的包路径
        try {
            Class<?> classType3 = Class.forName("java.lang.Boolean");
            Log.e(TAG,classType3+"");
        } catch (ClassNotFoundException e) {
            Log.e(TAG,e.getMessage());
        }
    }

    private void step2(){
//        <-- 1. 获取类的构造函数（传入构造函数的参数类型）->>
//        // a. 获取指定的构造函数 （公共 / 继承）
//        Constructor<T> getConstructor(Class<?>... parameterTypes)
//        // b. 获取所有的构造函数（公共 / 继承）
//        Constructor<?>[] getConstructors();
//        // c. 获取指定的构造函数 （ 不包括继承）
//        Constructor<T> getDeclaredConstructor(Class<?>... parameterTypes)
//        // d. 获取所有的构造函数（ 不包括继承）
//        Constructor<?>[] getDeclaredConstructors();
//// 最终都是获得一个Constructor类对象
//
//// 特别注意：
//        // 1. 不带 "Declared"的方法支持取出包括继承、公有（Public） & 不包括有（Private）的构造函数
//        // 2. 带 "Declared"的方法是支持取出包括公共（Public）、保护（Protected）、默认（包）访问和私有（Private）的构造方法，但不包括继承的构造函数
//        // 下面同理

//        <--  2. 获取类的属性（传入属性名） -->
//                // a. 获取指定的属性（公共 / 继承）
//                Field getField(String name) ;
//        // b. 获取所有的属性（公共 / 继承）
//        Field[] getFields() ;
//        // c. 获取指定的所有属性 （不包括继承）
//        Field getDeclaredField(String name) ；
//        // d. 获取所有的所有属性 （不包括继承）
//        Field[] getDeclaredFields() ；
//// 最终都是获得一个Field类对象
//
//<-- 3. 获取类的方法（传入方法名 & 参数类型）-->
//                // a. 获取指定的方法（公共 / 继承）
//                Method getMethod(String name, Class<?>... parameterTypes) ；
//        // b. 获取所有的方法（公共 / 继承）
//        Method[] getMethods() ；
//        // c. 获取指定的方法 （ 不包括继承）
//        Method getDeclaredMethod(String name, Class<?>... parameterTypes) ；
//        // d. 获取所有的方法（ 不包括继承）
//        Method[] getDeclaredMethods() ；
//// 最终都是获得一个Method类对象
//
//<-- 4. Class类的其他常用方法 -->
//                getSuperclass();
//// 返回父类
//
//        String getName();
//// 作用：返回完整的类名（含包名，如java.lang.String ）
//
//        Object newInstance();
//// 作用：快速地创建一个类的实例
//// 具体过程：调用默认构造器（若该类无默认构造器，则抛出异常
//// 注：若需要为构造器提供参数需使用java.lang.reflect.Constructor中的newInstance（）


    }

    private void step3(){
//        // 即以下方法都分别属于`Constructor`类、`Method`类 & `Field`类的方法。
//
//<-- 1. 通过Constructor 类对象获取类构造函数信息 -->
//                String getName()；// 获取构造器名
//        Class getDeclaringClass()；// 获取一个用于描述类中定义的构造器的Class对象
//        int getModifiers()；// 返回整型数值，用不同的位开关描述访问修饰符的使用状况
//        Class[] getExceptionTypes()；// 获取描述方法抛出的异常类型的Class对象数组
//        Class[] getParameterTypes()；// 获取一个用于描述参数类型的Class对象数组
//
//<-- 2. 通过Field类对象获取类属性信息 -->
//                String getName()；// 返回属性的名称
//        Class getDeclaringClass()； // 获取属性类型的Class类型对象
//        Class getType()；// 获取属性类型的Class类型对象
//        int getModifiers()； // 返回整型数值，用不同的位开关描述访问修饰符的使用状况
//        Object get(Object obj) ；// 返回指定对象上 此属性的值
//        void set(Object obj, Object value) // 设置 指定对象上此属性的值为value
//
//<-- 3. 通过Method 类对象获取类方法信息 -->
//                String getName()；// 获取方法名
//        Class getDeclaringClass()；// 获取方法的Class对象
//        int getModifiers()；// 返回整型数值，用不同的位开关描述访问修饰符的使用状况
//        Class[] getExceptionTypes()；// 获取用于描述方法抛出的异常类型的Class对象数组
//        Class[] getParameterTypes()；// 获取一个用于描述参数类型的Class对象数组
//
//<--额外：java.lang.reflect.Modifier类 -->
//// 作用：获取访问修饰符
//
//        static String toString(int modifiers)
//// 获取对应modifiers位设置的修饰符的字符串表示
//
//        static boolean isXXX(int modifiers)
//// 检测方法名中对应的修饰符在modifiers中的值
    }
}
