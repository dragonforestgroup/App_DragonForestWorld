package com.dragonforest.app.dragonforestworld.biz.mine.activity.message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.adapter.MessageFragmentAdapter;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.fragment.AllFragment;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.fragment.NoticeFragment;
import com.dragonforest.app.module_common.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ImageView img_add;
    private TabLayout tl_msg;
    private ViewPager vp_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_message);
        initView();
        StatusBarUtil.getInstance().setColor(this,getResources().getColor(R.color.colorGrayForBackground));
    }

    private void initView() {
        img_add = findViewById(R.id.img_add);
        vp_msg = findViewById(R.id.vp_msg);
        initViewPager(vp_msg);
        tl_msg = findViewById(R.id.tl_msg);
        initTabLayout(tl_msg,vp_msg);
    }

    private void initViewPager(ViewPager viewPager) {
        List<Fragment> list=new ArrayList<>();
        list.add(new AllFragment());
        list.add(new NoticeFragment());
        list.add(new AllFragment());
        list.add(new NoticeFragment());

        List<String> titles=new ArrayList<>();
        titles.add("所有");
        titles.add("通知");
        titles.add("私聊");
        titles.add("群聊");

        MessageFragmentAdapter adapter=new MessageFragmentAdapter(getSupportFragmentManager(),list,titles);
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout(TabLayout tabLayout, ViewPager viewPager) {
        int count=viewPager.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
    }
}
