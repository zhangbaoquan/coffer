package coffer.common;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collection;
import java.util.List;

/**
 * @author：张宝全
 * @date：2020/7/16
 * @Description： RecycleView 万能适配器
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class ComRecycleViewAdapter<T> extends RecyclerView.Adapter<ComRecycleViewHolder> {

    private List<T> mList;

    private OnBindDataListener<T> onBindDataListener;
    private OnMoreBindDataListener<T> onMoreBindDataListener;

    public ComRecycleViewAdapter(List<T> mList, OnBindDataListener<T> onBindDataListener) {
        this.mList = mList;
        this.onBindDataListener = onBindDataListener;
    }

    public ComRecycleViewAdapter(List<T> mList, OnMoreBindDataListener<T> onMoreBindDataListener) {
        this.mList = mList;
        this.onBindDataListener = onMoreBindDataListener;
        this.onMoreBindDataListener = onMoreBindDataListener;
    }

    /**
     * 绑定数据
     * @param <T>
     */
    public interface OnBindDataListener<T> {
        void onBindViewHolder(T model, ComRecycleViewHolder viewHolder, int type, int position);

        int getLayoutId(int type);
    }

    /**
     * 绑定多类型的数据
     * @param <T>
     */
    public interface OnMoreBindDataListener<T> extends OnBindDataListener<T> {
        int getItemType(int position);
    }

    @Override
    public int getItemViewType(int position) {
        if (onMoreBindDataListener != null) {
            return onMoreBindDataListener.getItemType(position);
        }
        return 0;
    }

    @Override
    public ComRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = onBindDataListener.getLayoutId(viewType);
        ComRecycleViewHolder viewHolder = ComRecycleViewHolder.getViewHolder(parent, layoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComRecycleViewHolder holder, int position) {
        onBindDataListener.onBindViewHolder(
                mList.get(position), holder, getItemViewType(position), position);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 替换数据集合
     *
     * @param items
     */
    public void setData(Collection<T> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        if (mList.size() > 0) {
            mList.clear();
        }
        mList.addAll(items);
        notifyDataSetChanged();
    }
}

