package coffer.customViewDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrage_main);
        mCofferFlowLayout = findViewById(R.id.flow);

        marginSize = Util.dipToPixel(this,3);
        mViewSize = Util.dipToPixel(this,10);

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
