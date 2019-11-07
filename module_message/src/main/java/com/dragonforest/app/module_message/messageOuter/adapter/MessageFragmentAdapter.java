package com.dragonforest.app.module_message.messageOuter.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/8 20:10
 */
public class MessageFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> list;
    List<String> titles;

    public MessageFragmentAdapter(FragmentManager fm, List<Fragment> list,List<String> titles) {
        super(fm);
        this.list = list;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int i) {
        return list.get(i);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
