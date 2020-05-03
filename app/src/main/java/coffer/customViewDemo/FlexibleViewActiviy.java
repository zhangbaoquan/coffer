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
    private List<String> mList = new ArrayList<>();

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
        mList.add("亚特兰大老鹰");
        mList.add("夏洛特黄蜂");
        mList.add("迈阿密热火");
        mList.add("奥兰多魔术");
        mList.add("华盛顿奇才");
        mList.add("波士顿凯尔特人");
        mList.add("布鲁克林篮网");
        mList.add("纽约尼克斯");
        mList.add("费城76人");
        mList.add("多伦多猛龙");
        mList.add("芝加哥公牛");
        mList.add("克里夫兰骑士");
        mList.add("底特律活塞");
        mList.add("印第安纳步行者");
        mList.add("密尔沃基雄鹿");
        mList.add("达拉斯独行侠");
        mList.add("休斯顿火箭");
        mList.add("孟菲斯灰熊");
        mList.add("新奥尔良鹈鹕");
        mList.add("圣安东尼奥马刺");
        mList.add("丹佛掘金");
        mList.add("明尼苏达森林狼");
        mList.add("俄克拉荷马城雷霆");
        mList.add("波特兰开拓者");
        mList.add("犹他爵士");
        mList.add("金州勇士");
        mList.add("洛杉矶快船");
        mList.add("洛杉矶湖人");
        mList.add("菲尼克斯太阳");
        mList.add("萨克拉门托国王");
    }

    private void initView(){
        RcAdapter adapter = new RcAdapter(this, mList);
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
