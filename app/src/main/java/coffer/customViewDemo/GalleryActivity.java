package coffer.customViewDemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import coffer.androidjatpack.R;
import coffer.customViewDemo.adapter.GalleryAdapter;
import coffer.customViewDemo.adapter.GalleryScrollListener;

/**
 * @author：张宝全
 * @date：2020/5/5
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class GalleryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_main);
        recyclerView = findViewById(R.id.recyclerView);
        initData();
    }

    private void initData(){
        ArrayList data = new ArrayList();
        for (int i = 0; i < 5; i++) {
            data.add("哈哈"+i);
        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new GalleryScrollListener());
        recyclerView.setAdapter(new GalleryAdapter(data));
        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }
}
