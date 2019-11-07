package com.dragonforest.app.module_message.messageOuter;
import	java.security.PrivateKey;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.event.ConnectStatusEvent;
import com.dragonforest.app.module_message.messageOuter.adapter.MessageFragmentAdapter;
import com.dragonforest.app.module_message.messageOuter.fragment.AllFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.GroupChatFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.PrivateChatFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.WorkNoticeFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ImageView img_add;
    private TabLayout tl_msg;
    private ViewPager vp_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_message);
        initView();
        StatusBarUtil.getInstance().setColor(this, getResources().getColor(R.color.colorGrayForBackground));
    }

    private void initView() {
        img_add = findViewById(R.id.img_add);
        vp_msg = findViewById(R.id.vp_msg);
        initViewPager(vp_msg);
        tl_msg = findViewById(R.id.tl_msg);
        initTabLayout(tl_msg, vp_msg);

        // 初始显示工作通知
        vp_msg.setCurrentItem(1);
    }

    private void initViewPager(ViewPager viewPager) {
        List<Fragment> list = new ArrayList<>();
        list.add(new AllFragment());
        list.add(new WorkNoticeFragment());
        list.add(new PrivateChatFragment());
        list.add(new GroupChatFragment());

        List<String> titles = new ArrayList<>();
        titles.add("所有");
        titles.add("工作通知");
        titles.add("私聊");
        titles.add("群聊");

        MessageFragmentAdapter adapter = new MessageFragmentAdapter(getSupportFragmentManager(), list, titles);
        viewPager.setAdapter(adapter);
    }

    private void initTabLayout(TabLayout tabLayout, ViewPager viewPager) {
        int count = viewPager.getAdapter().getCount();
        for (int i = 0; i < count; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(viewPager.getAdapter().getPageTitle(i)));
        }
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectStatusChanged(ConnectStatusEvent connectStatusEvent) {
        Toast.makeText(this, "消息服务状态变化：" + connectStatusEvent.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
