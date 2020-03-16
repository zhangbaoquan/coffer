package coffer.drawViewDemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.androidjatpack.R;
import coffer.widget.ScrollerView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferViewActiviy2 extends AppCompatActivity {

    private ScrollerView mScrollerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main2);
        mScrollerView = findViewById(R.id.s1);
        mScrollerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

}
