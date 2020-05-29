package coffer.customViewDemo.holder;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import coffer.customViewDemo.adapter.RcAdapter;
import coffer.customViewDemo.bean.MutiTypeData;
import coffer.model.BaseData;

/**
 * @author：张宝全
 * @date：2020/5/28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class VerticalBookHolder extends BaseHolder {

    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<BaseData> data;
    private RcAdapter adapter;
    public VerticalBookHolder(Context context) {
        super(new RecyclerView(context));
        mContext = context;
    }

    @Override
    protected void createHolder(View itemView) {
        super.createHolder(itemView);
        mRecyclerView = ((RecyclerView)itemView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        adapter = new RcAdapter(mContext);
    }

    @Override
    public void bindHolder(MutiTypeData.Data bean, int itemPosition) {
        super.bindHolder(bean, itemPosition);
        final ArrayList<MutiTypeData.Baby> items = bean.baby;
        final int adCount = items == null ? 0 : items.size();
        if (data == null){
            data = new ArrayList<>();
        }
        data.clear();
        for (int i = 0; i < adCount; i++) {
            BaseData baseData = new BaseData(items.get(i).title);
            baseData.url = items.get(i).url;
            data.add(baseData);
        }
        adapter.setData(data);
        mRecyclerView.setAdapter(adapter);

    }

}
