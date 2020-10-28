package coffer.crashDemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;
import xcrash.XCrash;

/**
 * @author：张宝全
 * @date：2020/10/28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class TestCrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_main);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type != null) {
            if (type.equals("native")) {
                XCrash.testNativeCrash(false);
            } else if (type.equals("java")) {
                XCrash.testJavaCrash(false);
            }
        }
    }
}

