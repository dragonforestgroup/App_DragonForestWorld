package com.dragonforest.view.module_notification;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dragonforest.view.module_notification.receiver.NotificationReceiver;
import com.dragonforest.view.module_notification.service.NotificationService;
import com.dragonforest.view.module_notification.util.NotificationWrapper;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn1) {
            show1();

        } else if (i == R.id.btn2) {
            show2();

        } else if (i == R.id.btn3) {
            show3();

        } else if (i == R.id.btn4) {
            show4();

        } else if (i == R.id.btn5) {
            show5();

        } else if (i == R.id.btn6) {
            show6();

        }
    }

    private void show1() {
        String title = "普通";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setTarget(SecondActivity.class, null, 1, true)
                .show(nid);

    }

    private void show2() {
        String title = "大字体";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setBigText("啊啊啊啊啊哈啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈1哈哈哈哈哈哈哈哈哈哈哈")
                .setTarget(SecondActivity.class, null, 1, true)
                .show(nid);
    }

    private void show3() {
        String title = "大图片";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setLargeImage(R.drawable.app_bg_java)
                .setTarget(SecondActivity.class, null, 1, true)
                .show(nid);
    }

    private void show4() {
        String title = "悬挂式";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setFullScreen(SecondActivity.class, null, 1)
                .show(nid);
    }

    private void show5() {
        String title = "消息对话";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setMessage("你好啊啊啊啊啊", "韩龙林")
                .setTarget(SecondActivity.class, null, 1, true)
                .show(nid);
    }

    private void show6() {
        String title = "带按钮的消息";
        String content = "哈鲁";
        int nid = (int) System.currentTimeMillis() / 1000 / 1000;
//        int nid = 10;
        HashMap<String, Object> paramsHash = new HashMap<>();
        paramsHash.put("nid", nid);
        new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.ic_launcher_background, title, content)
                .setTarget(SecondActivity.class, null, 1, true)
                .addAction(R.drawable.ic_launcher_background, "忽略", NotificationReceiver.class, paramsHash, NotificationReceiver.ACTION_CANCEL)
                .addAction(R.drawable.ic_launcher_background, "启动服务", NotificationService.class, paramsHash, NotificationReceiver.ACTION_CANCEL)
                .addAction(R.drawable.ic_launcher_background, "进入", SecondActivity.class, paramsHash, NotificationReceiver.ACTION_START)
                .show(nid);
    }
}
