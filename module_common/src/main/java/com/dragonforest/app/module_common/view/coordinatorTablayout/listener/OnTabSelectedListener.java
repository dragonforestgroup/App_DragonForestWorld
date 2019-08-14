package com.dragonforest.app.module_common.view.coordinatorTablayout.listener;


import android.support.design.widget.TabLayout;

/**
 * @author 韩龙林
 * @date 2019/8/14 12:35
 */

public interface OnTabSelectedListener {

    public void onTabSelected(TabLayout.Tab tab);

    public void onTabUnselected(TabLayout.Tab tab);

    public void onTabReselected(TabLayout.Tab tab);
}
