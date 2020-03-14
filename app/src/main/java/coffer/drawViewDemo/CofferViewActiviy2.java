package coffer.drawViewDemo;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;

import coffer.androidjatpack.R;
import coffer.widget.AdTipView;
import coffer.widget.BannerContentLayout;
import coffer.widget.DragImageView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferViewActiviy2 extends AppCompatActivity {

    private BannerContentLayout mBanner;
    private Button mReset;
    WindowManager wm;
    WindowManager.LayoutParams layoutParams;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_main);
//        mClose = findViewById(R.id.close);
        mReset = findViewById(R.id.reset);
//        mBody = findViewById(R.id.body);
        mBanner = findViewById(R.id.banner);

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
//
//        mClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                close();
//            }
//        });
        createBannerContentView();

    }

    private void open(){
        wm = ((WindowManager) getSystemService(WINDOW_SERVICE));
        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,0,0, PixelFormat.TRANSPARENT);
//        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
////                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;

        final Button button = new Button(this);
        button.setText("小浮窗");
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE: {
                        layoutParams.x = rawX;
                        layoutParams.y = rawY;
                        wm.updateViewLayout(button, layoutParams);
                        break;
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        wm.addView(button,layoutParams);
    }

    private void close(){

    }

    private void createBannerContentView(){
        for (int i = 0; i < 3; i++) {
            mBanner.addView(getView(i));
        }
    }

    private View getView(int i){
        View adView = LayoutInflater.from(CofferViewActiviy2.this).inflate(R.layout.activity_view_item, null);
        adView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        View body = adView.findViewById(R.id.body);
        ImageView ivAd = adView.findViewById(R.id.iv_ad);
        TextView tvTitle = adView.findViewById(R.id.tv_title);
        TextView tvDesc = adView.findViewById(R.id.tv_desc);
        TextView tvBtn = adView.findViewById(R.id.tv_btn);

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CofferViewActiviy2.this,"点啦",Toast.LENGTH_SHORT).show();
            }
        });

        if (i == 0){
            tvTitle.setText("啦啦");
            tvDesc.setText("凉宫春日的忧郁");
            ImageLoader.getInstance().displayImage("http://img15.3lian.com/2015/f3/08/d/02.jpg",ivAd);
        }else if (i == 1){
            tvTitle.setText("哈哈");
            tvDesc.setText("凉宫春日的烦闷");
            ImageLoader.getInstance().displayImage("http://pic2.16pic.com/00/15/80/16pic_1580359_b.jpg",ivAd);
        }else {
            tvTitle.setText("露露");
            tvDesc.setText("凉宫春日的动摇");
            ImageLoader.getInstance().displayImage("http://img15.3lian.com/2015/f3/08/d/02.jpg",ivAd);
        }

        return adView;
    }

}
