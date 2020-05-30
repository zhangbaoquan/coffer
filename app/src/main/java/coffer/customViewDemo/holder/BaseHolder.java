package coffer.customViewDemo.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import coffer.customViewDemo.bean.MutiTypeData;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class BaseHolder<T extends View> extends RecyclerView.ViewHolder {

    public T view;


    public BaseHolder(@NonNull T itemView) {
        super(itemView);
        view = itemView;
        createHolder(itemView);
    }

    protected void createHolder(T itemView){

    }

    public void bindHolder(MutiTypeData.Data bean,int itemPosition){

    }

    /**
     * 进入缓存池的时候释放viewHolder 中无用的资源
     */
    public void clearData(){

    }


}
