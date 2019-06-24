package coffer.widget;

/**
 * @author：张宝全
 * @date：2019-06-10
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public interface IDefaultFooterListener {

    int EVENT_CANCEL = 1;        // 对应 Dialog 的 setOnCancelListener 事件（一般不会用到，在 AlertDialogController 使用）
    int BUTTON_YES = 11;         // 确认按钮
    int BUTTON_CANCEL = 12;      // 取消按钮
    int BUTTON_Neutral = 13;     // 中间按钮

    /**
     * 点击回调
     *
     * @param event   点击源：{@link #BUTTON_YES}/{@link #BUTTON_Neutral}/{@link #BUTTON_CANCEL}/{@link #EVENT_CANCEL}
     * @param extInfo 附加信息
     */
    void onEvent(int event, Object extInfo);
}
