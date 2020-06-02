package coffer.javaDemo.hookDemo;

import android.util.Log;
import android.view.View;

import coffer.util.CONSTANT;

/**
 * @author：张宝全
 * @date：2019-07-07
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MyProxClickListener implements View.OnClickListener {


    @Override
    public void onClick(View v) {
        Log.e(CONSTANT.COFFER_TAG,"我动态代理啦");
    }
}
