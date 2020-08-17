package coffer.androidDemo.customViewDemo.weidget;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 *@作者：Super
 *@创建时间 2013 2013-12-5 上午10:52:17
 */
public class ZYViewPagerScroller extends Scroller {
    private int mDuration = 1000;
 
    public ZYViewPagerScroller(Context context) {
        super(context);
    }
 
    public ZYViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
 
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
 
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        // Ignore received duration, use fixed one instead
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
 
    public void setZYDuration(int time) {
        mDuration = time;
    }
 
    public int getZYDuration() {
        return mDuration;
    }
 
}
