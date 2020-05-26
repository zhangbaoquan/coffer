package coffer.customViewDemo;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import coffer.androidjatpack.R;
import coffer.customViewDemo.adapter.RcAdapter;
import coffer.util.Util;
import coffer.widget.StretchRecycleView;

/**
 * @author：张宝全
 * @date：2019-06-08
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ListViewActiviy extends AppCompatActivity {

    private ListView mListView;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_main);
        mListView = findViewById(R.id.rc);
//        mScollerContainer = findViewById(R.id.parent);
        initData();
        initView();
    }

    private void initData() {
        mList.add("亚特兰大老鹰");
        mList.add("夏洛特黄蜂");
        mList.add("迈阿密热火");
        mList.add("迈阿密热火");
        mList.add("迈阿密热火");
    }

    private void initView(){
        ListAdapter adapter = new ListAdapter(mList,this);
        mListView.setAdapter(adapter);
    }

    private class ListAdapter extends BaseAdapter{
        private List<String> data;
        private Context context;

        public ListAdapter(List<String> data,Context context){
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout body = new RelativeLayout(context);
            ViewGroup.LayoutParams bodyLp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, Util.dipToPixel(context,54));
            TextView title = new TextView(context);
            RelativeLayout.LayoutParams tvLp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
            title.setTextColor(Color.BLACK);
            title.setText(mList.get(position));
            tvLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            body.addView(title,tvLp);

            View line = new View(context);
            RelativeLayout.LayoutParams lineLp = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,Util.dipToPixel(context,1));
            line.setBackgroundColor(Color.BLUE);
            lineLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            body.addView(line,lineLp);
            body.setLayoutParams(bodyLp);
            convertView = body;
            return convertView;
        }
    }

}
