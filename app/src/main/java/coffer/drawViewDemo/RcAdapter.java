package coffer.drawViewDemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;

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
    private List<String> mList = new ArrayList<>();

    RcAdapter(Context context,List<String> list){
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public RcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_view_main3_item, parent, false);
        return new RcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcViewHolder holder, int position) {
        final String content = mList.get(position);
        holder.title.setText(content);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class RcViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public RcViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.t1);
        }
    }
}
