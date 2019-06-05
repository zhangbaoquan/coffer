package coffer.javademo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author：张宝全
 * @date：2019-05-18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MapDemoActivity extends AppCompatActivity {
    private static final String TAG = "MapDemoActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hashMaotest();
    }

    private void hashMaotest(){
        Map<String,Integer> map = new HashMap<>();
        map.put("Android",1);
        map.put("Java",2);
        map.put("Python",3);

        Log.e(TAG,"android的值: "+map.get("Android"));

        // 遍历hashMap,首先key-value的Set集合
        Set<Map.Entry<String,Integer>> entries = map.entrySet();
        // 然后遍历Set集合
        for (Map.Entry<String,Integer> entry : entries){
            Log.e(TAG,entry.getKey());
            Log.e(TAG,entry.getValue()+"");
        }

        // 也可以使用迭代器遍历
        Iterator iterator = entries.iterator();
        while (iterator.hasNext()){
            String key = iterator.toString();
            Log.e(TAG,key);
            Log.e(TAG,map.get(key)+"");
        }
    }
}
