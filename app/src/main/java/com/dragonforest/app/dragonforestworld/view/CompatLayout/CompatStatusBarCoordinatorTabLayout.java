package com.dragonforest.app.dragonforestworld.view.CompatLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_common.view.coordinatorTablayout.CoordinatorTabLayout;

/**
 * @author 韩龙林
 * @date 2019/9/23 17:30
 */
public class CompatStatusBarCoordinatorTabLayout extends CoordinatorTabLayout {
    public CompatStatusBarCoordinatorTabLayout(Context context) {
        super(context);
        init(context);
    }

    public CompatStatusBarCoordinatorTabLayout(Context context, AttributeSet attrs) {
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
