package coffer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.util.APP;
import coffer.util.global.ContactWindowUtil;

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
        setCurrAcvitity();
        initView();
        initData();
        ContactWindowUtil contactWindowUtil = new ContactWindowUtil(this);
        contactWindowUtil.setDialogListener(new ContactWindowUtil.ContactWindowListener() {
            @Override
            public void onDataCallBack(String str) {
                Toast.makeText(BaseDefaultActivity.this,"啦啦",Toast.LENGTH_SHORT).show();
            }
        });
        contactWindowUtil.showContactView();
    }

    public abstract void initView();
    public abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        setCurrAcvitity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setCurrAcvitity() {
        if (getParent() == null) {
            APP.setCurrActivity(this);
        }
    }

    /**
     * 清除APP的当前activity
     */
    private void cleanCurrActivity() {
        // 只有当前activity是自己时，才能清除，因为在acivity切换过程中设置和清除方法可能交替调用多次
        if (APP.getCurrActivity() == this) {
            APP.setCurrActivity(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        setCurrAcvitity();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
