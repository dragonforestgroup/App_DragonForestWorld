package com.dragonforest.app.module_message.messageOuter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonforest.app.lib_view.expandlayout.ExpandableLayout;
import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.event.ConnectStatusEvent;
import com.dragonforest.app.module_message.messageOuter.adapter.MessageFragmentAdapter;
import com.dragonforest.app.module_message.messageOuter.fragment.AllFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.GroupChatFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.PrivateChatFragment;
import com.dragonforest.app.module_message.messageOuter.fragment.WorkNoticeFragment;
import com.dragonforest.app.module_message.mqtt.MqttManager;
import com.xubo.scrolltextview.ScrollTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


public class MessageActivity extends AppCompatActivity {

    //    private ImageView img_add;
    private Toolbar toolbar;
    private ScrollTextView scrollTextView;
    private TabLayout tl_msg;
    private ViewPager vp_msg;
    private ExpandableLayout expandableLayout_warn;
    private TextView tv_warn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_message);
        StatusBarUtil.getInstance().setTransparent(this);
        initView();
        initData();
        initServer();
    }

    private void initServer() {
        String[] topics = {"hanlonglin"};
        int[] qoss = {1};
        MqttManager.getInstance().init(
                "tcp://172.16.17.71:1884",
                "hanlonglin",
                topics,
                qoss,
                this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        expandableLayout_warn = findViewById(R.id.expandLayout_warn);
        tv_warn = findViewById(R.id.tv_warn);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        scrollTextView = findViewById(R.id.scrollTextView);
        vp_msg = findViewById(R.id.vp_msg);
        initViewPager(vp_msg);
        tl_msg = findViewById(R.id.tl_msg);
        initTabLayout(tl_msg, vp_msg);

        //初始化公告

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

    private void initData() {
        List<String> contents = new ArrayList<>();
        List<ScrollTextView.OnScrollClickListener> listeners = new ArrayList<>();
        contents.add("今天是国际植树节，请大家积极植树");
        listeners.add(new ScrollTextView.OnScrollClickListener() {

            @Override
            public void onClick() {
                Toast.makeText(MessageActivity.this, "请去植树！", Toast.LENGTH_SHORT).show();
            }
        });
        contents.add("字节跳动收购锤子，互联网又一巨头？");
        listeners.add(new ScrollTextView.OnScrollClickListener() {

            @Override
            public void onClick() {
                Toast.makeText(MessageActivity.this, "滚去字节跳动！", Toast.LENGTH_SHORT).show();
            }
        });
        contents.add("大家的热情真高啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        listeners.add(new ScrollTextView.OnScrollClickListener() {

            @Override
            public void onClick() {
                Toast.makeText(MessageActivity.this, "热情似火焰！", Toast.LENGTH_SHORT).show();
            }
        });
        scrollTextView.setTextContent(contents, listeners);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.msg_action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.msg_action_add) {
            Toast.makeText(this, "功能正在开发中......", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectStatusChanged(ConnectStatusEvent connectStatusEvent) {
        Toast.makeText(this, "消息服务状态变化：" + connectStatusEvent.getMessage(), Toast.LENGTH_SHORT).show();
        if (connectStatusEvent.getStatus() == -1) {
            // 消息服务断开
            if (!expandableLayout_warn.isExpanded()) {
                expandableLayout_warn.setExpanded(true);
            }
            tv_warn.setText(connectStatusEvent.getMessage());
        } else {
            if (expandableLayout_warn.isExpanded()) {
                expandableLayout_warn.setExpanded(false);
            }
        }
    }
}
