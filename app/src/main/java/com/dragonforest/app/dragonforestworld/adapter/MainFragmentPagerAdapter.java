package com.dragonforest.app.dragonforestworld.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dragonforest.app.dragonforestworld.biz.home.FragmentHome;
import com.dragonforest.app.dragonforestworld.biz.mine.FragmentMine;
import com.dragonforest.app.dragonforestworld.biz.recommend.FragmentRecommend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/2 16:35
 */
public class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentHome());
        fragmentList.add(new FragmentRecommend());
        fragmentList.add(new FragmentMine());
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
