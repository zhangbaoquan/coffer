package coffer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author：张宝全
 * @date：2020/5/12
 * @Description： 基础业务类
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public abstract class BaseDefaultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    public abstract void initView();
    public abstract void initData();
}
