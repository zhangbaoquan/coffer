package coffer.customViewDemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import coffer.CofferApplication;
import coffer.androidjatpack.R;
import coffer.model.BaseData;

/**
 * @author：张宝全
 * @date：2020-03-22
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class RcAdapter extends RecyclerView.Adapter<RcAdapter.RcViewHolder> {

    private Context mContext;
    private ArrayList<BaseData> mList;

    public RcAdapter(Context context){
        this.mContext = context;
    }

    public void setData(ArrayList<BaseData> list){
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(CofferApplication.getInstance()).inflate(R.layout.activity_view_main3_item, parent, false);
        return new RcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcViewHolder holder, int position) {
        final String content = mList.get(position).title;
        holder.title.setText(content);
        ImageLoader.getInstance().displayImage(mList.get(position).url,holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class RcViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public RcViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.t1);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
