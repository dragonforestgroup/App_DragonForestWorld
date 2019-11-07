package com.dragonforest.app.module_message.messageInter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.database.MessageModel;

import java.text.SimpleDateFormat;

public class MessageDetailActivity extends AppCompatActivity {

    private TextView tv_content;
    private TextView tv_topic;
    private TextView tv_date;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_default_message);
        initView();
        LogUtil.E("DefaultMessageActivity", "OnCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.E("DefaultMessageActivity", "onResume()");
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        tv_content = findViewById(R.id.tv_content);
        tv_topic = findViewById(R.id.tv_topic);
        tv_date = findViewById(R.id.tv_date);
        toolbar = findViewById(R.id.toolbar);
    }

    private void initData() {
        MessageModel messageModel = (MessageModel) getIntent().getSerializableExtra("message");
        LogUtil.E("DefaultMessageActiciry", "收到消息：" + messageModel.getMessage());
        if (messageModel != null) {
            tv_content.setText(messageModel.getMessage() + "");
            tv_topic.setText(messageModel.getTopic() + "");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
            tv_date.setText(sdf.format(messageModel.getDate()) + "");
        }
    }
}
