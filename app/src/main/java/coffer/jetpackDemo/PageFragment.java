package coffer.jetpackDemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;

/**
 * @author：张宝全
 * @date：2020/5/5
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class PageFragment extends Fragment {

    private static final String COLORS = "colors";
    private static final String POSITION = "position";

    private final String url = "http://book.img.ireader.com/idc_1/f_webp/7088d8ce/group61/M00/7E/2D/" +
            "CmQUOF-hQziEV4ZOAAAAAGYSrdQ837047424.jpg?v=uX13TMi3&t=CmQUOF-hQzg.";

    public static PageFragment newInstance(List<Integer> colors,int position){

        Bundle bundle = new Bundle();
        bundle.putSerializable(COLORS, ((ArrayList<Integer>) colors));
        bundle.putInt(POSITION,position);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private List<Integer> mColors;
    private int mPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            mColors = (List<Integer>) getArguments().getSerializable(COLORS);
            mPosition = getArguments().getInt(POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_viewpager2_item,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout container = view.findViewById(R.id.container);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        ImageView iv = view.findViewById(R.id.iv);
        container.setBackgroundResource(mColors.get(mPosition));
        tvTitle.setText("Item " + mPosition);
        Glide.with(this).load(url).into(iv);
//        Glide.with()
    }
}
