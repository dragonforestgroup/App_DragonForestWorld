package com.dragonforest.app.module_message.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dragonforest.app.module_common.utils.ToastUtils;
import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.application.MyApplication;
import com.dragonforest.app.module_message.event.ConnectStatusEvent;
import com.dragonforest.app.module_message.messageInter.MessageHistoryActivity;
import com.dragonforest.app.module_message.mqtt.MqttManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class MqttActivity extends AppCompatActivity {

    private Button btn_show;
    private Button btn_history;
    private TextView tv_status;
    private TextView tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_mqtt);
        initView();
        initData();

        String[] topics = {"hanlonglin", "dragon"};
        int[] qoss = {1, 1};
        MqttManager.getInstance().init(
                "tcp://172.16.17.71:1884",
                "com.dragonforest.app.module_message",
                topics,
                qoss,
                this);
        Log.e("MqttActivity", "onCreate()");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MqttActivity", "onDestroy()");
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
        btn_show = findViewById(R.id.btn_show);
        btn_history = findViewById(R.id.btn_history);
        tv_status = findViewById(R.id.tv_status);
        tv_user = findViewById(R.id.tv_user);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MqttActivity.this, MessageHistoryActivity.class));
            }
        });
    }

    private void initData() {
        tv_user.setText(MyApplication.currentClientId + "");
        tv_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeUserDialog();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onConnectChangedEvent(ConnectStatusEvent connectStatusEvent) {
        /* Do something */
        if (connectStatusEvent != null) {
            int status = connectStatusEvent.getStatus();
            if (status == 1) {
                tv_status.setTextColor(Color.GREEN);
            } else {
                tv_status.setTextColor(Color.RED);
            }
            tv_status.setText(connectStatusEvent.getMessage());
        }
    }

    private void showChangeUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择登录用户");
        builder.setCancelable(true);

        // 找出当前clientId所在的下标
        int index = 0;
        for (int i = 0; i < MyApplication.clientIds.length; i++) {
            if (MyApplication.currentClientId.equals(MyApplication.clientIds[i])) {
                index = i;
                break;
            }
        }

        builder.setSingleChoiceItems(MyApplication.clientIds, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String choose = MyApplication.clientIds[which];
                tv_user.setText(choose);
                MyApplication.changeUser(choose);
                ToastUtils.show("切换用户成功", MqttActivity.this);
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
