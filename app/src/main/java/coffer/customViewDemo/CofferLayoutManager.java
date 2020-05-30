package coffer.customViewDemo;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author：张宝全
 * @date：2020/5/30
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class CofferLayoutManager extends LinearLayoutManager {

    /**
     * 是否允许滚动
     */
    private boolean mCanScroll = true;

    public CofferLayoutManager(Context context) {
        super(context);
    }

    public CofferLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CofferLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setCanScroll(boolean canScroll){
        this.mCanScroll = canScroll;
    }

    @Override
    public boolean canScrollVertically() {
        if (!mCanScroll){
            return false;
        }
        return super.canScrollVertically();
    }
}
