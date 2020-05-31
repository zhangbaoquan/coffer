package coffer.androidDemo.customViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.util.Util;
import coffer.widget.CofferFlowLayout;

/**
 * @author：张宝全
 * @date：2020/5/2
 * @Description： 自定义ViewGroup 布局练习
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ArrangeViewActivity extends AppCompatActivity {

    private CofferFlowLayout mCofferFlowLayout;
    private int marginSize;
    private int mViewSize;
    private ArrayList<String> mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrage_main);
        mCofferFlowLayout = findViewById(R.id.flow);

        marginSize = Util.dipToPixel(this,3);
        mViewSize = Util.dipToPixel(this,10);

//        setView();
        setView2();
    }

    private void setView2(){
        mTitle = new ArrayList<>();
        mTitle.add("凉宫春日的忧郁");
        mTitle.add("叹息");
        mTitle.add("烦闷");
        mTitle.add("消失");
        mTitle.add("动摇");
        mTitle.add("暴走");
        mTitle.add("阴谋");
        mTitle.add("愤慨");
        mTitle.add("分裂");
        mTitle.add("惊愕");
        mCofferFlowLayout.setTag(mTitle, new CofferFlowLayout.ItemClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ArrangeViewActivity.this,mTitle.get(position),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {
                mTitle.remove(position);
                mCofferFlowLayout.removeView(position);
            }
        });
    }

    /**
     * 方式一: 将标签View 在这里创建
     */
    private void setView(){
        mCofferFlowLayout.addView(createTagView("凉宫春日的忧郁"));
        mCofferFlowLayout.addView(createTagView("叹息"));
        mCofferFlowLayout.addView(createTagView("烦闷"));
        mCofferFlowLayout.addView(createTagView("消失"));
        mCofferFlowLayout.addView(createTagView("动摇"));
        mCofferFlowLayout.addView(createTagView("暴走"));
        mCofferFlowLayout.addView(createTagView("阴谋"));
        mCofferFlowLayout.addView(createTagView("愤慨"));
        mCofferFlowLayout.addView(createTagView("分裂"));
        mCofferFlowLayout.addView(createTagView("惊愕"));
    }

    private View createTagView(String content){
        TextView textView = new TextView(this);
        textView.setText(content);
        textView.setTextColor(Color.WHITE);
        textView.setBackground(getResources().getDrawable(R.drawable.bg_gradient));
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.leftMargin = marginSize;
        layoutParams.bottomMargin = marginSize;
        layoutParams.topMargin = marginSize;
        layoutParams.rightMargin = marginSize;
        textView.setLayoutParams(layoutParams);
        return textView;
    }

}
