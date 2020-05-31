package coffer.androidDemo.customViewDemo.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public abstract class AbsAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 填充数据，刷新列表
     * @param data 新数据
     */
    public abstract void setData(List<T> data);
}
