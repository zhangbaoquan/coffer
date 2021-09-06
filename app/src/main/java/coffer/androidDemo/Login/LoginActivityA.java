package coffer.androidDemo.Login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;

/**
 * author      : coffer
 * date        : 8/24/21
 * description :
 * Reviewer    :
 */
public class LoginActivityA extends BaseDefaultActivity {


    @Override
    public void initView() {
        setContentView(R.layout.activity_login_a);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivityA.this, LoginActivityB.class);
                startActivityForResult(intent, 100);
//                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        CofferLog.D("login_tag","LoginActivityA onResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CofferLog.D("login_tag","LoginActivityA onActivityResult");
        CofferLog.D("login_tag","requestCode : "+requestCode + " , resultCode : "+resultCode);
        Bundle bundle = data.getBundleExtra("data");
        String content = bundle.getString("my_data");
        CofferLog.D("login_tag","content : "+content);
    }

}
