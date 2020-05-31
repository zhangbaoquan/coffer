/**
 * File Name:PauseOnScrollListener.java <br/>
 * Package Name:com.zhangyue.iReader.cache.extend <br/>
 * Date:2015年6月1日<br/>
 * Copyright (c) 2015, zy All Rights Reserved.
 */

package coffer.androidDemo.customViewDemo.recycleView;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * ClassName:PauseOnScrollListener <br/>
 * 滚动暂停监听
 * 
 * <pre>
 * 从而保证滚动顺滑度
 * 通过列表views的{@link AbsListView#setOnScrollListener(OnScrollListener) }设置该类
 * 同时该类可以包装你自己的
 * {@link OnScrollListener}.
 * 
 * <pre>
 * Date:     2015年6月1日  <br/>
 * @author   huangyahong
 */
public class PauseOnScrollListener extends RecyclerView.OnScrollListener {


    /** 滚动时是否暂停 **/
    private boolean isPauseOnScroll;

    /** 快速滚动时是否暂停 **/
    private boolean isPauseOnFling;

    /** 包装的滚动监听器 **/
    private RecyclerView.OnScrollListener mExternalListener;

    /**
     * Creates a new instance of PauseOnScrollListener.
     * @param pauseOnScroll 滚动时是否暂停图片加载
     * @param pauseOnFling 快速滚动时是否暂停图片加载
     */
    public PauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling) {
        this(pauseOnScroll, pauseOnFling, null);
    }

    /**
     * Creates a new instance of PauseOnScrollListener.
     * @param pauseOnScroll 滚动时是否暂停图片加载
     * @param pauseOnFling 快速滚动时是否暂停图片加载
     * @param customListener  需要包装的滚动监听器 
     */
    public PauseOnScrollListener(boolean pauseOnScroll, boolean pauseOnFling,
                                 RecyclerView.OnScrollListener customListener) {
        this.isPauseOnScroll = pauseOnScroll;
        this.isPauseOnFling = pauseOnFling;
        mExternalListener = customListener;
    }

    /**
     * 使用ImageLoader 替换VolleyLoader
     * @param recyclerView
     * @param newState
     */
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        switch(newState) {
            case RecyclerView.SCROLL_STATE_IDLE:
//                VolleyLoader.getInstance().resumeLoadBitmap();
                ImageLoader.getInstance().resume();
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:
                if(isPauseOnScroll) {
//                    VolleyLoader.getInstance().pauseLoadBitmap();
                    ImageLoader.getInstance().pause();
                }
                break;
            case RecyclerView.SCROLL_STATE_SETTLING:
                if(isPauseOnFling) {
//                    VolleyLoader.getInstance().pauseLoadBitmap();
                    ImageLoader.getInstance().pause();
                }
                break;
            default:
                break;
        }
        if(mExternalListener != null) {
            mExternalListener.onScrollStateChanged(recyclerView, newState);
        }
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(mExternalListener != null) {
            mExternalListener.onScrolled(recyclerView, dx, dy);
        }
    }

}
