package coffer.zy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ireader.plug.api.IreaderPlugApi;
import com.ireader.plug.tools.Tools;
import com.vivo.mobilead.banner.BannerAdParams;
import com.vivo.mobilead.banner.VivoBannerAd;
import com.vivo.mobilead.listener.IAdListener;
import com.vivo.mobilead.manager.VivoAdManager;
import com.vivo.mobilead.model.BackUrlInfo;
import com.vivo.mobilead.model.VivoAdError;

import coffer.CofferApplication;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020-01-09
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class VivoAdBannerMainActivity extends AppCompatActivity implements IAdListener {

    private Button btn;
    private FrameLayout container;
    private VivoBannerAd mBottomVivoBannerAd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vivo_banner);

        initView();


    }

    private void initView(){
        btn = findViewById(R.id.btn);
        container = findViewById(R.id.container);
        container.setVisibility(View.GONE);
        VivoAdManager.getInstance().init(CofferApplication.getInstance(), "619d45fa3d654b5d9222743873eea72d");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadVivoNativeExpressAd();
            }
        });
    }

    private BannerAdParams.Builder getBuilder() {
        BannerAdParams.Builder builder = new BannerAdParams.Builder("f347a6a5bec74c34ae2f439999fade6c");
        builder.setRefreshIntervalSeconds(15);
        BackUrlInfo backUrlInfo = new BackUrlInfo("", "我是Banner的");
        builder.setBackUrlInfo(backUrlInfo);
        return builder;
    }

    private void loadVivoNativeExpressAd() {
        mBottomVivoBannerAd = new VivoBannerAd(this, getBuilder().build(), this);
        // 获取Banner广告View。
        View adView = mBottomVivoBannerAd.getAdView();
        // 注意：只有adView不等于null时，才能把View添加到你的布局中显示。 创建完广告必须调用addView添加广告视图，不然会导致曝光率低。
        if (null != adView) {
            container.addView(adView);
        }
    }


    @Override
    public void onAdShow() {
        Log.i("uuiuiu","onAdShow");
    }

    @Override
    public void onAdFailed(VivoAdError vivoAdError) {
        Log.i("uuiuiu",vivoAdError.getErrorCode() + ", "+vivoAdError.getErrorMsg());
    }

    @Override
    public void onAdReady() {
        Log.i("uuiuiu","onAdReady");
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAdClick() {

    }

    @Override
    public void onAdClosed() {

    }
}
