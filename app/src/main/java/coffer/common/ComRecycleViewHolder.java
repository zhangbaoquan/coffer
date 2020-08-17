package coffer.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


/**
 * @author：张宝全
 * @date：2020/7/16
 * @Description： 万能适配ViewHolder
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

public class ComRecycleViewHolder extends RecyclerView.ViewHolder {

    /**
     * 子View的集合
     */
    private SparseArray<View> mViews;
    private View mContentView;

    public ComRecycleViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
        mContentView = itemView;
    }

    /**
     * 获取CommonViewHolder实体
     *
     * @param parent
     * @param layoutId
     * @return
     */
    public static ComRecycleViewHolder getViewHolder(ViewGroup parent, int layoutId) {
        return new ComRecycleViewHolder(View.inflate(parent.getContext(), layoutId, null));
    }

    /**
     * 提供给外部访问View的方法
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mContentView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 设置ItemContent
     *
     * @param viewId
     * @param text
     * @return
     */
    public ComRecycleViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ComRecycleViewHolder setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 设置背景颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ComRecycleViewHolder setBackgroundColor(int viewId, int color) {
        ImageView iv = getView(viewId);
        iv.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置文本颜色
     *
     * @param viewId
     * @param color
     * @return
     */
    public ComRecycleViewHolder setTextColor(int viewId, int color) {
        TextView tv = getView(viewId);
        tv.setTextColor(color);
        return this;
    }


    /**
     * 设置控件的显示隐藏
     *
     * @param viewId
     * @param visibility
     * @return
     */
    public ComRecycleViewHolder setVisibility(int viewId, int visibility) {
        TextView tv = getView(viewId);
        tv.setVisibility(visibility);
        return this;
    }
}