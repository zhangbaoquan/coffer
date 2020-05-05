package coffer.customViewDemo.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author：张宝全
 * @date：2020/5/5
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class GalleryScrollListener extends RecyclerView.OnScrollListener {

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            int left = child.getLeft();
            int paddingStart = recyclerView.getPaddingStart();
            // 遍历recyclerView子项，以中间项左侧偏移量为基准进行缩放
            float bl = Math.min(1,Math.abs(left - paddingStart) * 1f / child.getWidth());
            float scale = 1 - bl * (1 - 0.5f);
            child.setScaleY(scale);
        }
    }
}
