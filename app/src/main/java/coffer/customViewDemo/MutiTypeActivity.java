package coffer.customViewDemo;



import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.customViewDemo.adapter.ChannelAdapter;
import coffer.customViewDemo.recycleView.SuperRecycleView;
import coffer.util.CreateDataUtils;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description： 首页多布局展示
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class MutiTypeActivity extends BaseDefaultActivity implements SuperRecycleView.LoadMoreListener {

    private static final String TAG = "MutiTypeActivity_tag";

    private SuperRecycleView mSuperRecycleView;
    private RecyclerView.Adapter mAdapter;

    /**
     * 底部加载中、加载失败、到底了 组合View
     */
    private FrameLayout mLoadingLayout;

    /**
     * 加载失败、点击重试
     */
    private TextView mErrorTxt;

    /**
     * 已经到底了，去别的频道逛逛吧
     */
    private RelativeLayout mEndViewLayout;

    @Override
    public void initView() {
        setContentView(R.layout.activity_muti_main);
        mSuperRecycleView = findViewById(R.id.recyclerView);
        // 去掉下拉的阴影
        mSuperRecycleView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        // 避免每次绘制Item时，不再重新计算Item高度。(性能优化)
        mSuperRecycleView.setHasFixedSize(true);
        channelType();
    }

    /**
     * 样式一：频道
     */
    private void channelType(){
        // 设置加载更多的监听
        mSuperRecycleView.setLoadMoreListener(this);
        // 设置布局方向
        mSuperRecycleView.setLayoutManager(new LinearLayoutManager(this));
        // 设置尾部View
        mSuperRecycleView.addFooterView(getLoadingLayout());
    }


    @Override
    public void initData() {
        mAdapter = new ChannelAdapter(this);
        ((ChannelAdapter)mAdapter).setData(CreateDataUtils.getMutiData().data);
        mSuperRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 底部加载中、加载失败、到底了 组合View(相当于FooterView)
     * @return 底部ViewGroup
     */
    private FrameLayout getLoadingLayout(){
        if (mLoadingLayout == null){
            mLoadingLayout = new FrameLayout(this);

        }
        return mLoadingLayout;
    }
}