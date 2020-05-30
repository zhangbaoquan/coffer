package coffer.customViewDemo.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import coffer.customViewDemo.bean.MutiTypeData;
import coffer.customViewDemo.holder.BaseHolder;
import coffer.customViewDemo.holder.ViewHolderFactory;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description： 频道适配器
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MutiTypeAdapter extends AbsAdapter<MutiTypeData.Data>{

    private ViewHolderFactory mFactory;

    public MutiTypeAdapter(Context context){
        mFactory = new ViewHolderFactory(context,null);
    }

    @Override
    public void setData(List<MutiTypeData.Data> data) {
        this.mFactory.setData(data);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mFactory.createViewHolder(viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        mFactory.bindViewHolder(holder,position);
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        if (holder instanceof BaseHolder){
            ((BaseHolder)holder).clearData();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mFactory.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mFactory.getItemCount();
    }
}
