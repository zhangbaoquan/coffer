package coffer.databinding.util;

import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;
import android.widget.ImageView;

/**
 * @author：张宝全
 * @date：2019-04-28
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */

@BindingMethods({@BindingMethod(
        attribute = "app:srcCompat",
        method = "setImageResource",
        type = ImageView.class
)})
public class MyBindingMethods {
}
