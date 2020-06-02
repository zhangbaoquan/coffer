package coffer.crashDemo;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.BuildConfig;
import coffer.androidjatpack.R;
import coffer.provider.FileShareProvider;
import coffer.util.CONSTANT;

/**
 * @author：张宝全
 * @date：2020/5/31
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CrashCollectActivity extends BaseDefaultActivity {

    private TextView textView;

    @Override
    public void initView() {
        setContentView(R.layout.activity_crash_main);
        textView = findViewById(R.id.content);
        findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 模拟崩溃
                Log.e(CONSTANT.COFFER_TAG,"crash : "+1/0);
            }
        });
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // 分享崩溃日志
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 注意：需要提前设置自己的FileProvider 以此来适配Android 7.0
                    Uri crashTxt = FileProvider.getUriForFile(CrashCollectActivity.this,
                            BuildConfig.APPLICATION_ID +".provider",
                            LogcatHelper.getInstance(CrashCollectActivity.this).getLogFile());
                    intent.putExtra(Intent.EXTRA_STREAM,crashTxt);
                    intent.setType("*/*");
                    startActivity(Intent.createChooser(intent, "分享"));
                }catch (Exception e){
                    Log.e(CONSTANT.COFFER_TAG,e.getMessage());
                }
            }
        });
        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 读取崩溃日志

            }
        });
    }

    @Override
    public void initData() {

    }
}
