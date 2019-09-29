package com.dragonforest.app.dragonforestworld.view.CompatLayout;

import android.content.Context;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.dragonforest.app.module_common.utils.StatusBarUtil;

/**
 * @author 韩龙林
 * @date 2019/9/23 17:30
 */
public class CompatStatusBarSearchView extends SearchView {
    public CompatStatusBarSearchView(Context context) {
        super(context);
        init(context);
    }

    public CompatStatusBarSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        int statusBarHeight = StatusBarUtil.getInstance().getStatusBarHeight(context);
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = statusBarHeight+getPaddingTop();
        setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
    }
}
