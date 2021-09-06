package coffer.androidDemo.Login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * author      : coffer
 * date        : 8/24/21
 * description :
 * Reviewer    :
 */
public class LoginActivityB extends BaseDefaultActivity {


    @Override
    public void initView() {
        setContentView(R.layout.activity_login_b);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("my_data","haha");
                intent.putExtra("data",bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
