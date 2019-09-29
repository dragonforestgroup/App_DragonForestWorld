package com.dragonforest.app.dragonforestworld.view.mylayoutmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义layoutManger(弧形RecycleView)
 * 可以分为以下几步：
 * 1.指定默认的LayoutParams
 * 2.测量并记录每个item的信息
 * 3.回收以及放置各个item
 * 4.处理滚动
 *
 * @author 韩龙林
 * @date 2019/9/9 09:21
 */
public class ArcLayoutManger extends RecyclerView.LayoutManager {

    /**
     * 第1步：指定默认的LayoutParams
     * 指定了每一个 view默认的LayoutParams，并且这个layoutParams会在你调用getViewForPosition()返回的view前应用到这个子view
     *
     * @return
     */
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 第2步：测量并记录每个item的信息
     * 初始化布局以及adapter数据发生改变时调用。所以我们在这个方法中对我们的item进行测量以及初始化
     */
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        super.onLayoutChildren(recycler, state);
        // getItemCount获取到的是数据的总数
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
//            offsetRotate=0;
            return;
        }
        // getChildCount获取到的是当前已添加的子View的数量
        if (getChildCount() == 0) {
            View scrap = recycler.getViewForPosition(0);
            addView(scrap);
            measureChildWithMargins(scrap,0,0);
            int mDecoratedMeasuredWidth = getDecoratedMeasuredWidth(scrap);
            int mDecoratedMeasuredHeight = getDecoratedMeasuredHeight(scrap);

        }
    }
}
