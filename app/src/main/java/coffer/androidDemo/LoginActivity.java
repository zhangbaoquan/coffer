package coffer.androidDemo;


import android.widget.EditText;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * author      : coffer
 * date        : 8/24/21
 * description :
 * Reviewer    :
 */
public class LoginActivity extends BaseDefaultActivity {

    private EditText mEditText;

    @Override
    public void initView() {
        setContentView(R.layout.activity_login_main);
        mEditText = findViewById(R.id.edit);
    }

    @Override
    public void initData() {

    }
}
