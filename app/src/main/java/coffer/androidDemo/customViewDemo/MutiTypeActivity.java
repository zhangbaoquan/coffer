package coffer.androidDemo.customViewDemo;



import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.androidDemo.customViewDemo.adapter.MutiTypeAdapter;
import coffer.androidDemo.customViewDemo.recycleView.SuperRecycleView;
import coffer.util.CreateDataUtils;
import coffer.util.Util;
import coffer.widget.DampLayout;

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
     * 样式一：首页
     */
    private void channelType(){
        // 设置加载更多的监听
        mSuperRecycleView.setLoadMoreListener(this);
        // 设置布局方向
        mSuperRecycleView.setLayoutManager(new CofferLayoutManager(this));
        // 设置尾部View
//        mSuperRecycleView.addFooterView(getLoadingLayout());

//        addToTopView();
//        addFloatHeadView(savedInstanceState);
        mSuperRecycleView.addHeaderView(getHeaderPicView());
    }

    @Override
    public void initData() {
        mAdapter = new MutiTypeAdapter(this);
        ((MutiTypeAdapter)mAdapter).setData(CreateDataUtils.getMutiData().data);
        mSuperRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 给RecycleView 添加head
     */
    private RelativeLayout getHeaderPicView(){
        RelativeLayout parent = new RelativeLayout(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parent.setLayoutParams(layoutParams);

        DampLayout dampLayout = new DampLayout(this);
        RelativeLayout.LayoutParams dllp = new RelativeLayout.LayoutParams(
                Util.dipToPixel(this,300),Util.dipToPixel(this,100));
        dllp.topMargin = Util.dipToPixel(this,10);
        dllp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        dampLayout.setBackground(getResources().getDrawable(R.drawable.bg_gradient));
        parent.addView(dampLayout,dllp);

        ImageView imageView = new ImageView(this);
        FrameLayout.LayoutParams ivfl = new FrameLayout.LayoutParams(
                Util.dipToPixel(this,100),Util.dipToPixel(this,80));
        ivfl.gravity = Gravity.CENTER;
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.coffer));
        dampLayout.addView(imageView,ivfl);
        dampLayout.setSuperRecycleView(mSuperRecycleView);

        return parent;
    }

    /**
     * 底部加载中、加载失败、到底了 组合View(相当于FooterView)
     * @return 底部ViewGroup
     */
    private FrameLayout getLoadingLayout(){
        if (mLoadingLayout == null){
            mLoadingLayout = new FrameLayout(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,Util.dipToPixel(this,80));
            mLoadingLayout.setLayoutParams(layoutParams);
            mErrorTxt = new TextView(this);
            mErrorTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            mErrorTxt.setTextColor(0xff222222);
            mErrorTxt.setGravity(Gravity.CENTER);
            mErrorTxt.setText("加载失败，点击重试");
            mErrorTxt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onLoadMore();
                }
            });
            FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    Gravity.CENTER);
            mLoadingLayout.addView(mErrorTxt, fl);
            // 再加一个等待球动画

        }
        return mLoadingLayout;
    }

    private RelativeLayout getEndViewLayout(){
        if (mEndViewLayout == null){
            mEndViewLayout = new RelativeLayout(this);
            TextView mTipTxt = new TextView(this);
            mTipTxt.setId(R.id.id_recyleview_end_text);
            mTipTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            mTipTxt.setTextColor(0xff999999);
            mTipTxt.setGravity(Gravity.CENTER);
            String text = "已经滑到底部啦~";
            mTipTxt.setText(text);
            mTipTxt.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(
                            Util.dipToPixel(this, 72),
                            View.MeasureSpec.EXACTLY));
            RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(mTipTxt.getMeasuredWidth(), RelativeLayout.LayoutParams.WRAP_CONTENT);
            rl.addRule(RelativeLayout.CENTER_IN_PARENT);
            int margin = Util.dipToPixel(this, 5);
            rl.leftMargin = margin;
            rl.rightMargin = margin;
            mEndViewLayout.addView(mTipTxt, rl);

            View view = new View(this);
            view.setBackgroundColor(0xffE7E7E7);
            rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            rl.addRule(RelativeLayout.CENTER_VERTICAL);
            rl.addRule(RelativeLayout.LEFT_OF, R.id.id_recyleview_end_text);
            mEndViewLayout.addView(view, rl);

            view = new View(this);
            view.setBackgroundColor(0xffE7E7E7);
            rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 1);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            rl.addRule(RelativeLayout.CENTER_VERTICAL);
            rl.addRule(RelativeLayout.RIGHT_OF, R.id.id_recyleview_end_text);
            mEndViewLayout.addView(view, rl);
        }
        return mEndViewLayout;
    }
}