package coffer.customViewDemo;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;
import coffer.customViewDemo.adapter.RcAdapter;
import coffer.model.BaseData;
import coffer.widget.StretchRecycleView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class FlexibleViewActiviy extends AppCompatActivity {

    private StretchRecycleView mRecycleVie;
    private ArrayList<BaseData> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexible_view_main);
        mRecycleVie = findViewById(R.id.rc);
//        mScollerContainer = findViewById(R.id.parent);
        initData();
        initView();
    }

    private void initData() {
        mList.add(new BaseData("亚特兰大老鹰"));
        mList.add(new BaseData("夏洛特黄蜂"));
        mList.add(new BaseData("迈阿密热火"));
        mList.add(new BaseData("奥兰多魔术"));
        mList.add(new BaseData("华盛顿奇才"));
        mList.add(new BaseData("波士顿凯尔特人"));
        mList.add(new BaseData("布鲁克林篮网"));
        mList.add(new BaseData("纽约尼克斯"));
        mList.add(new BaseData("费城76人"));
        mList.add(new BaseData("多伦多猛龙"));
        mList.add(new BaseData("芝加哥公牛"));
        mList.add(new BaseData("克里夫兰骑士"));
        mList.add(new BaseData("底特律活塞"));
        mList.add(new BaseData("印第安纳步行者"));
        mList.add(new BaseData("密尔沃基雄鹿"));
        mList.add(new BaseData("达拉斯独行侠"));
        mList.add(new BaseData("休斯顿火箭"));
        mList.add(new BaseData("孟菲斯灰熊"));
        mList.add(new BaseData("新奥尔良鹈鹕"));
        mList.add(new BaseData("圣安东尼奥马刺"));
        mList.add(new BaseData("丹佛掘金"));
        mList.add(new BaseData("明尼苏达森林狼"));
        mList.add(new BaseData("俄克拉荷马城雷霆"));
        mList.add(new BaseData("波特兰开拓者"));
        mList.add(new BaseData("犹他爵士"));
        mList.add(new BaseData("金州勇士"));
        mList.add(new BaseData("洛杉矶快船"));
        mList.add(new BaseData("洛杉矶湖人"));
        mList.add(new BaseData("菲尼克斯太阳"));
        mList.add(new BaseData("萨克拉门托国王"));
    }

    private void initView(){
        RcAdapter adapter = new RcAdapter(this);
        adapter.setData(mList);
        mRecycleVie.setLayoutManager(new LinearLayoutManager(this));
        mRecycleVie.setHasFixedSize(true);
        mRecycleVie.setAdapter(adapter);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && mRecycleVie != null){
            int h1 = mRecycleVie.getHeight();
            int h2 = ((ViewGroup)mRecycleVie.getParent()).getHeight();
            if (h1 != h2){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRecycleVie.getLayoutParams();
                layoutParams.height = h2;
                mRecycleVie.setLayoutParams(layoutParams);
            }
        }
    }
}
