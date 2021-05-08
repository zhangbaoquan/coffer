//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//
//import com.zhangyue.iReader.app.APP;
//import com.zhangyue.iReader.app.IreaderApplication;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import coffer.CofferApplication;
//
///**
// * SharedPreferences 本身就是线程安全的，不需要额外使用synchronized
// * <p> 注意事项</p>
// * <pre>
// *     1、如果同一时刻需要多次写SP，建议将多次提交合并成一个，避免多次提交影响性能，可使用如下方法:
// *     {@link #setLongMap(HashMap,boolean),#setStringMap(HashMap,boolean),#setIntMap(HashMap,boolean),#setBooleanMap(HashMap,boolean),#setBooleanMap(HashMap,boolean)}
// * </pre>
// * @作者：chao.ch
// * @创建时间 2012 2012-12-24 下午2:19:42
// */
//public class SPHelperTemp {
//
//    public final static String SHAREPREFERENCES_NAME = "com.zhangyue.iReader.SharedPreferences.temp";
//    private static com.zhangyue.iReader.DB.SPHelperTemp mSPTHelper = null;
//    private SharedPreferences mSPT;
//    private Editor mEditor;
//
//    private SPHelperTemp(){
//        init();
//    }
//
//    public static com.zhangyue.iReader.DB.SPHelperTemp getInstance() {
//        if (mSPTHelper == null) {
//            mSPTHelper = new com.zhangyue.iReader.DB.SPHelperTemp();
//        }
//        return mSPTHelper;
//    }
//
//    public SharedPreferences getSharedPreferences() {
//        return mSPT;
//    }
//
//    public void init() {
//        open();
//    }
//
//    public void init(Context context) {
//        open(context);
//    }
//
//    @SuppressLint("CommitPrefEdits")
//    private void open(Context context) {
//        if (mSPT == null) {
//            mSPT = context.getSharedPreferences(SHAREPREFERENCES_NAME, APP.getPreferenceMode());
//            mEditor = mSPT.edit();
//        }
//    }
//
//    @SuppressLint("CommitPrefEdits")
//    private void open() {
//        if (mSPT == null) {
//            mSPT = CofferApplication.getInstance().getSharedPreferences(SHAREPREFERENCES_NAME, APP.getPreferenceMode());
//            mEditor = mSPT.edit();
//        }
//    }
//
//    public String getString(String key, String defValue) {
//        open();
//        return mSPT.getString(key, defValue);
//    }
//
//    public void setString(String key, String value) {
//        open();
//        mEditor.putString(key, value);
//        try {
//            mEditor.commit();
//        }catch (Exception e){
//
//        }
//    }
//
//    public int getInt(String key, int defValue) {
//        open();
//        return mSPT.getInt(key, defValue);
//    }
//
//    public void setInt(String key, int value) {
//        open();
//        mEditor.putInt(key, value);
//        mEditor.commit();
//    }
//
//    public long getLong(String key, long defValue) {
//        open();
//        return mSPT.getLong(key, defValue);
//    }
//
//    public void setLong(String key, long value) {
//        open();
//        mEditor.putLong(key, value);
//        mEditor.commit();
//    }
//
//    public float getFloat(String key, float defValue) {
//        open();
//        return mSPT.getFloat(key, defValue);
//    }
//
//    public void seFloat(String key, float value) {
//        open();
//        mEditor.putFloat(key, value);
//        mEditor.commit();
//    }
//
//    public boolean getBoolean(String key, boolean defValue) {
//        open();
//        boolean flag = mSPT.getBoolean(key, defValue);
//        return mSPT.getBoolean(key, defValue);
//    }
//
//    public void setBoolean(Context context, String key, boolean value) {
//        open(context);
//        mEditor.putBoolean(key, value);
//        mEditor.commit();
//    }
//
//    public void setBoolean(String key, boolean value) {
//        open();
//        mEditor.putBoolean(key, value);
//        mEditor.commit();
//    }
//
//    /*****  扩展   ****/
//    public void setBooleanMap(HashMap<String, Boolean> map) {
//        setBooleanMap(map,false);
//    }
//
//    public void setBooleanMap(HashMap<String, Boolean> map, boolean needAsync) {
//        if (map == null || map.size() == 0){
//            return;
//        }
//        open();
//        Iterator<HashMap.Entry<String, Boolean>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Boolean> entry = iterator.next();
//            if (entry != null){
//                mEditor.putBoolean(entry.getKey(), entry.getValue());
//            }
//        }
//        if (needAsync){
//            mEditor.commit();
//        }else {
//            mEditor.apply();
//        }
//    }
//
//    public void setStringMap(HashMap<String, String> map) {
//        setStringMap(map,false);
//    }
//
//    public void setStringMap(HashMap<String, String> map, boolean needAsync) {
//        if (map == null || map.size() == 0){
//            return;
//        }
//        open();
//        Iterator<HashMap.Entry<String, String>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            HashMap.Entry<String, String> entry = iterator.next();
//            if(entry != null){
//                mEditor.putString(entry.getKey(), entry.getValue());
//            }
//        }
//        if (needAsync){
//            mEditor.commit();
//        }else {
//            mEditor.apply();
//        }
//    }
//
//    public void setIntMap(HashMap<String, Integer> map) {
//        setIntMap(map,false);
//    }
//
//    public void setIntMap(HashMap<String, Integer> map, boolean needAsync) {
//        if (map == null || map.size() == 0){
//            return;
//        }
//        open();
//        Iterator<HashMap.Entry<String, Integer>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            HashMap.Entry<String, Integer> entry = iterator.next();
//            if (entry != null){
//                mEditor.putInt(entry.getKey(), entry.getValue());
//            }
//        }
//        if (needAsync){
//            mEditor.commit();
//        }else {
//            mEditor.apply();
//        }
//    }
//
//    public void setFloatMap(HashMap<String, Float> map) {
//        setFloatMap(map,false);
//    }
//
//    public void setFloatMap(HashMap<String, Float> map, boolean needAsync) {
//        if (map == null || map.size() == 0){
//            return;
//        }
//        open();
//        Iterator<Map.Entry<String, Float>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Float> entry = iterator.next();
//            if (entry != null){
//                mEditor.putFloat(entry.getKey(),entry.getValue());
//            }
//        }
//        if (needAsync){
//            mEditor.commit();
//        }else {
//            mEditor.apply();
//        }
//    }
//
//    public void setLongMap(HashMap<String, Long> map) {
//        setLongMap(map,false);
//    }
//
//    public void setLongMap(HashMap<String, Long> map, boolean needAsync) {
//        if (map == null || map.size() == 0){
//            return;
//        }
//        open();
//        Iterator<Map.Entry<String, Long>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            Map.Entry<String, Long> entry = iterator.next();
//            if (entry != null){
//                mEditor.putLong(entry.getKey(), entry.getValue());
//            }
//        }
//        if (needAsync){
//            mEditor.commit();
//        }else {
//            mEditor.apply();
//        }
//    }
//}
