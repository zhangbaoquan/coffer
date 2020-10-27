package coffer.androidDemo.behaviorDemo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.CofferLog;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020/10/22
 * @Description： 使用BottomSheetBehavior 实现底部弹窗拖拉滑动的显示效果
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class BottomSheetBehaviorDemoActivity extends BaseDefaultActivity {

    private static final String TAG = "ViewDemoActivity_tag";
    SimpleStringRecyclerViewAdapter simpleStringRecyclerViewAdapter = new SimpleStringRecyclerViewAdapter(this);

    @Override
    public void initView() {
//       initDemo1();
//       initDemo2();
       initDemo3();
    }

    /**
     * 使用NestedScrollView 滑动
     */
    private void initDemo1(){
        setContentView(R.layout.activity_view_demo_main1);
        // The View with the BottomSheetBehavior
        View bottomSheet = findViewById(R.id.bottom_sheet);
        final TextView tv = findViewById(R.id.tv);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变，根据slideOffset可以做一些动画
                CofferLog.D(TAG,"newState : "+newState);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
                CofferLog.D(TAG,"slideOffset : "+slideOffset);
                tv.setAlpha(slideOffset);
            }
        });

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    // 在折叠状态时，点击后展开
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    /**
     * 使用RecycleView滑动
     */
    private void initDemo2() {
        setContentView(R.layout.activity_view_demo_main2);
        final View blackView = findViewById(R.id.blackview);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        simpleStringRecyclerViewAdapter.setItemClickListener(new SimpleStringRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                Toast.makeText(BottomSheetBehaviorDemoActivity.this, "pos--->" + pos, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(simpleStringRecyclerViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if(newState == BottomSheetBehavior.STATE_COLLAPSED||newState == BottomSheetBehavior.STATE_HIDDEN){
//                    blackView.setBackgroundColor(Color.TRANSPARENT);
                    blackView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events 设置背景View 的透明度，相当于阴影
                blackView.setVisibility(View.VISIBLE);
                blackView.setAlpha(slideOffset);
            }
        });

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        blackView.setBackgroundColor(Color.parseColor("#60000000"));
        blackView.setVisibility(View.GONE);
        blackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

    }

    /**
     * 使用BottomSheetDialog 实现底部出现的弹窗可拖拽或、全屏、半屏显示
     */
    private void initDemo3() {
        setContentView(R.layout.activity_view_demo_main3);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBSDialog();
            }
        });
    }

    private void showBSDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_view_demo_dialog, null);
        RecyclerView recyclerView = view.findViewById(R.id.bs_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleStringRecyclerViewAdapter adapter = new SimpleStringRecyclerViewAdapter(this);
        adapter.setItemClickListener(new SimpleStringRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                dialog.dismiss();
                Toast.makeText(BottomSheetBehaviorDemoActivity.this, "pos--->" + pos, Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);
        dialog.setContentView(view);
        // 这里有个小点，即设置setPeekHeight 则为首次显示的高度，设置RecycleView 的高度可以控制当前弹窗是否全屏显示
        dialog.getBehavior().setPeekHeight(Util.dipToPixel(this,200));
        dialog.show();
    }

    @Override
    public void initData() {

    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        public ItemClickListener mItemClickListener;

        public void setItemClickListener(ItemClickListener listener) {
            mItemClickListener = listener;
        }

        public interface ItemClickListener {
            public void onItemClick(int pos);
        }

        private Context mContext;

        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(R.id.tv);
            }


        }

        public SimpleStringRecyclerViewAdapter(Context context) {
            super();
            mContext = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_view_demo_item1, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "pos --->" + position, Toast.LENGTH_SHORT).show();
                    mItemClickListener.onItemClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }


}
