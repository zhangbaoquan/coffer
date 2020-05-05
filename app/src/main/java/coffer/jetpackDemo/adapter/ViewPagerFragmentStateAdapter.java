package coffer.jetpackDemo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

import coffer.jetpackDemo.PageFragment;

/**
 * @author：张宝全
 * @date：2020/5/5
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {

    private List<Integer> colors = new ArrayList<>();

    {
        colors.add(android.R.color.black);
        colors.add(android.R.color.holo_purple);
        colors.add(android.R.color.holo_blue_dark);
        colors.add(android.R.color.holo_green_light);
    }

    public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return PageFragment.newInstance(colors,position);
    }

    @Override
    public int getItemCount() {
        return colors.size();
    }
}
