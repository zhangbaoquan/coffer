package coffer.widget;

import android.content.DialogInterface;

/**
 * @author：张宝全
 * @date：2019-06-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public interface IDismissListener {
    /**
     *
     * @param extInfo 附加信息
     */
    void onDismiss(DialogInterface dialog, Object extInfo);
}
