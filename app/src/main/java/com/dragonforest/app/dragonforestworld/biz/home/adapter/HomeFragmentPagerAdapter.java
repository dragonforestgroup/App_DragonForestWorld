package com.dragonforest.app.dragonforestworld.biz.home.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dragonforest.app.dragonforestworld.biz.home.FragmentHome;
import com.dragonforest.app.dragonforestworld.biz.home.fragment.TempleteFragment;
import com.dragonforest.app.dragonforestworld.biz.mine.FragmentMine;
import com.dragonforest.app.dragonforestworld.biz.recommend.FragmentRecommend;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/2 16:35
 */
public class HomeFragmentPagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList;
    String[] titles;

    public HomeFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList,String[] titles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
