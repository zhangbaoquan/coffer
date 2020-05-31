package coffer.pluginDemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ireader.plug.api.IreaderPlugApi;
import com.ireader.plug.tools.Tools;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020-01-09
 * @Description： 基于4.4 版本多进程掌阅插件
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ZyOldMainActivity extends AppCompatActivity {

    private Button mNovelBtn;
    private TextView mErrorTv;
    private TextView mDownloadInfoTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zy_main);
        initView();
        setupListener();
        loadData();
    }

    private void initView(){
        mNovelBtn = (Button)findViewById(R.id.novel);
        mErrorTv = (TextView) findViewById(R.id.error_info);
        mDownloadInfoTv = (TextView)findViewById(R.id.download_info);
    }

    private void setupListener(){
        mNovelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNovel();
            }
        });
    }

    private void clickNovel(){
        IreaderPlugApi.jump2BookShelfPage(this, mOnLaunchPluginCallBack);
    }

    public void loadData(){
        installPluginAndCheckUpdate();
    }


    protected IreaderPlugApi.OnLaunchPluginCallback mOnLaunchPluginCallBack = new IreaderPlugApi.OnLaunchPluginCallback() {
        @Override
        public int[] onGetLaunchAnimResIdArr() {
            return null;
        }

        @Override
        public void onPluginNotInstall() {
            Toast.makeText(ZyOldMainActivity.this, "插件未安装",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int code, String msg) {
            Toast.makeText(ZyOldMainActivity.this, msg,Toast.LENGTH_SHORT).show();
        }
    };

    private void installPluginAndCheckUpdate(){
        IreaderPlugApi.installPluginAndCheckUpdate(this, new IreaderPlugApi.OnPluginInstallListener() {
            @Override
            public void onInstall(boolean isDone) {
                String msg = "安装中";
                if(isDone){
                    msg = "小说";
                }
                mNovelBtn.setText(msg);
            }

            @Override
            public void onError(int code, String msg) {
                mErrorTv.setText(msg);
                mNovelBtn.setText("小说");
            }

            @Override
            public void onHasNoPluginFile() {
                mErrorTv.setText("没有插件文件");
                mNovelBtn.setText("小说");
            }
        }, new IreaderPlugApi.OnPluginUpdateListener() {
            @Override
            public void onCheckUpdateStart() {
                mDownloadInfoTv.setText("插件检查更新开始");
            }

            @Override
            public void onHasUpdate(int version) {
                mDownloadInfoTv.setText("插件有更新，目标版本：" + version);
            }

            @Override
            public void onNoUpdate() {
                mDownloadInfoTv.setText("插件没有更新");
            }

            @Override
            public void onDownloadStart() {
                mDownloadInfoTv.setText("插件下载开始");
            }

            @Override
            public void onProgressChange(long fileSize, long downloadedSize) {
                String total = Tools.FormetFileSize(fileSize);
                String rate = Tools.getRate(downloadedSize, fileSize);
                mDownloadInfoTv.setText("插件更新 total: " + total + " rate: " + rate);
            }

            @Override
            public void onDownloadSuccess() {
                mDownloadInfoTv.setText("插件下载完成");
            }

            @Override
            public void onError(int code, String error) {
                mDownloadInfoTv.setText(error);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        IreaderPlugApi.onRequestPermissionsResult(requestCode, grantResults);
    }
}
