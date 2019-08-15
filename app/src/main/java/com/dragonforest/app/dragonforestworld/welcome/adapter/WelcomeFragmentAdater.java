package com.dragonforest.app.dragonforestworld.welcome.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/15 14:42
 */
public class WelcomeFragmentAdater extends FragmentPagerAdapter {
    List<Fragment> fragmentList;

    public WelcomeFragmentAdater(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
