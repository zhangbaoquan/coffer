package coffer.androidDemo.customViewDemo.holder;

import android.text.TextUtils;

import coffer.androidDemo.customViewDemo.bean.MutiTypeData;

/**
 * @author：张宝全
 * @date：2020/5/27
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public class ViewType {

    /**
     * 兼容服务端异常
     */
    public final static int ITEM_TYPE_DEFAULT = -1;

    /**
     * Banner
     */
    public final static int ITEM_TYPE_BANNER = 10;

    /**
     * 文字轮播样式
     */
    public final static int ITEM_TYPE_BANNER_TEXT = 20;

    /**
     * 书库一级分类
     */
    public final static int ITEM_TYPE_CARTOON_CATEGORY = 29;

    /**
     * 横排book
     */
    public final static int ITEM_TYPE_HORIZONTAL_BOOK = 11;


    /**
     * 竖排Book
     */
    public final static int ITEM_TYPE_VERTICAL_BOOK = 12;

    /**
     * head
     */
    public static final String STYLE_HEAD = "head";

    /**
     * banner
     */
    public static final String STYLE_BANNER = "banner";

    /**
     * horizon
     */
    public static final String STYLE_HORIZON = "horizon";

    /**
     * grid
     */
    public static final String STYLE_GRID = "grid";

    /**
     * portrait
     */
    public static final String STYLE_PORTRAIT = "portrait";


    public static int getViewType(MutiTypeData.Data data){
        if(data != null){
            String type = data.type;
            if (!TextUtils.isEmpty(type)){
                switch (type){
                    case STYLE_HEAD:
                        return ITEM_TYPE_BANNER_TEXT;
                    case STYLE_BANNER:
                        return ITEM_TYPE_BANNER;
                    case STYLE_HORIZON:
                        return ITEM_TYPE_HORIZONTAL_BOOK;
                    case STYLE_GRID:
                        return ITEM_TYPE_CARTOON_CATEGORY;
                    case STYLE_PORTRAIT:
                        return ITEM_TYPE_VERTICAL_BOOK;
                    default:
                        return ITEM_TYPE_DEFAULT;
                }
            }else {
                return ITEM_TYPE_DEFAULT;
            }
        }else {
            return ITEM_TYPE_DEFAULT;
        }
    }

}
