package coffer.customViewDemo.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;

import coffer.customViewDemo.bean.MutiTypeData;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description： View Holder 统一入口
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ViewHolderFactory {

    private Context mContext;
    private List<MutiTypeData.Data> mData;

    private HashMap<Integer,Class> mHolderMap;

    public ViewHolderFactory(Context context,List<MutiTypeData.Data> data){
        this.mContext = context;
        this.mData = data;
        initHolderMap();
    }

    private void initHolderMap(){
        // 文字轮播
        registerHolder(ViewType.ITEM_TYPE_BANNER_TEXT,TextBannerHolder.class);
        registerHolder(ViewType.ITEM_TYPE_BANNER,BannerHolder.class);
        registerHolder(ViewType.ITEM_TYPE_HORIZONTAL_BOOK,HorizontalSlideBookHolder.class);
        registerHolder(ViewType.ITEM_TYPE_VERTICAL_BOOK,VerticalBookHolder.class);
        registerHolder(ViewType.ITEM_TYPE_CARTOON_CATEGORY,MagazineCategoryHolder.class);
    }

    private HashMap registerHolder(int type,Class holderClass){
        if (mHolderMap == null){
            mHolderMap = new HashMap<>();
        }
        mHolderMap.put(type,holderClass);
        return mHolderMap;
    }

    public BaseHolder createViewHolder(int type){
        Class holderClass = null;
        if (mHolderMap != null){
            holderClass = mHolderMap.get(type);
        }
        if (holderClass == null){
            holderClass = BaseHolder.class;
        }

        BaseHolder holder = null;
        try {
            holder = (BaseHolder) holderClass.getConstructor(Context.class).newInstance(mContext);
        }catch (Exception e){
            Log.e("createViewHolder", e.getMessage());
        }
        if (holder == null){
            holder = new BaseHolder(new View(mContext));
        }
        return holder;
    }

    public void setData(List<MutiTypeData.Data> data){
        this.mData = data;
    }

    /**
     * bindViewHolder
     *
     * @param holder
     * @param position
     */
    public void bindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MutiTypeData.Data bean = mData.get(position);
        ((BaseHolder) holder).bindHolder(bean, position);
    }

    /**
     * 根据 position 获取 ViewType
     *
     * @param position
     * @return
     */
    public int getItemViewType(int position){
        if (mData == null){
            return 0;
        }
        MutiTypeData.Data data = mData.get(position);
        return ViewType.getViewType(data);
    }

    /**
     * 获取item数量
     *
     * TODO: 计算一次
     *
     * @return
     */
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }
}
