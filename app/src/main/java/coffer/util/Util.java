package coffer.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author：张宝全
 * @date：2019-11-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class Util {

    public static int dipToPixel(Context context, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1);
    }

    public static float dipToPixel(Context context, float dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                context.getResources().getDisplayMetrics()) + 1;
    }

    public static int dipToPixel(Resources r, int dip) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics()) + 1);
    }
}
