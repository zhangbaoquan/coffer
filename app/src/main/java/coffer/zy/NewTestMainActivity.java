package coffer.zy;


import coffer.BaseDefaultActivity;
import coffer.androidjatpack.R;
import coffer.util.Util;

/**
 * @author：张宝全
 * @date：2020-01-09
 * @Description： 临时写的小功能、验证要上线的小功能
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class NewTestMainActivity extends BaseDefaultActivity {

    @Override
    public void initView() {
        setContentView(R.layout.activity_text_main);
    }

    @Override
    public void initData() {
        int bg[] = Util.colorsConvertToRgb(-16771550);
        int text[] = Util.colorsConvertToRgb(-14662829);

    }

}