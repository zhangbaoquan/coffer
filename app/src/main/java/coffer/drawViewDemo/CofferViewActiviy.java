package coffer.drawViewDemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import coffer.androidjatpack.R;
import coffer.widget.AdTipView;
import coffer.widget.DragImageView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description： 仿头条评论动画
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferViewActiviy extends AppCompatActivity {

    private AdTipView mClose;
    private Button mReset;
    private DragImageView mBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main);
//        mClose = findViewById(R.id.close);
//        mReset = findViewById(R.id.reset);
//        mBody = findViewById(R.id.body);

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });

    }

    private void open(){

    }

    private void close(){

    }


}
