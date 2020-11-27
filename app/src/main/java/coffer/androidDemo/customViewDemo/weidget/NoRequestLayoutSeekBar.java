package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar;

import coffer.androidjatpack.R;
import coffer.util.Util;


/**
 * 避免读书会播放界面频繁layout
 * <p>
 * Created by qiwangming on 2017/5/17.
 */

public class NoRequestLayoutSeekBar extends androidx.appcompat.widget.AppCompatSeekBar {

    /**
     * fixBug： 拖动bar与进度同步问题
     */
    private int thumbOffset = Util.dipToPixel(getContext(), 8);

    public NoRequestLayoutSeekBar(Context context) {
        super(context);
    }

    public NoRequestLayoutSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoRequestLayoutSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestLayout() {
//        super.requestLayout();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
