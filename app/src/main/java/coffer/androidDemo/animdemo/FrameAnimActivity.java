package coffer.androidDemo.animdemo;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/7/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class FrameAnimActivity extends BaseDefaultActivity {

    private static final String TAG = "AnimActivity2_tag";

    private ImageView mIv;
    private AnimationDrawable mAnimationDrawable;

    @Override
    public void initView() {
        setContentView(R.layout.activity_frame_anim_main);
        mIv = findViewById(R.id.iv);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void start() {
        mIv.setImageDrawable(getResources().getDrawable(R.drawable.guide_smile_anim));
        mAnimationDrawable = (AnimationDrawable) mIv.getDrawable();
        mAnimationDrawable.start();
    }

    private void stop() {
        mAnimationDrawable = (AnimationDrawable) mIv.getDrawable();
        mAnimationDrawable.stop();
    }
}
