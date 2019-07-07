package coffer.hook;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Proxy;

import coffer.util.CONSTANT;

/**
 * @author：张宝全
 * @date：2019-07-07
 * @Description： 使用动态代理
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class InvokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout root = new RelativeLayout(this);
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(root);
        Button button = new Button(this);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        root.addView(button);
        button.setText("点我动态代理输出日志");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 下面的 hookListener 方法一旦被执行，这个方法里的逻辑就作废
                Toast.makeText(InvokeActivity.this,"拉拉",Toast.LENGTH_SHORT).show();
            }
        });
        hookListener(button);

        showAppVersion();

    }

    /**
     * hook View 的 OnClickListener方法
     * @param view
     */
    private void hookListener(View view){
        View.OnClickListener listener = (View.OnClickListener)Proxy.newProxyInstance(
                view.getClass().getClassLoader(),
                new Class[]{View.OnClickListener.class},
                new ProxHandler(new MyProxClickListener()));
        view.setOnClickListener(listener);
    }

    private void showAppVersion(){
        try {
            PackageInfo packageInfo = this.getPackageManager().
                    getPackageInfo(this.getPackageName(),0);
            Log.e(CONSTANT.TAG,"version name : "+packageInfo.versionName);
            Log.e(CONSTANT.TAG,"version code : "+packageInfo.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
