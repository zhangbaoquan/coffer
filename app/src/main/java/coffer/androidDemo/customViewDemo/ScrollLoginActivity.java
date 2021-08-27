package coffer.androidDemo.customViewDemo;

import android.widget.Toast;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.widget.TouchPictureCheckView;

/**
 * @author：张宝全
 * @date：2020/6/20
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class ScrollLoginActivity extends BaseDefaultActivity {


    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        TouchPictureCheckView touchPictureCheckView = findViewById(R.id.v1);
        touchPictureCheckView.setViewResultListener(new TouchPictureCheckView.ResultListener() {
            @Override
            public void onResult() {
                Toast.makeText(ScrollLoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {

    }
}
