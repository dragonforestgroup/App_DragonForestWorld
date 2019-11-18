package com.dragonforest.app.module_message.test.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.test.NotificationWrapper;

public class NotificationTestAc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_test);
        initView();
        Log.e("NotificationTestAc", "NotificationTestAc#onCreate() ");

    }

    private void initView() {
//        findViewById(R.id.btn_nomal).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("","点击通知");
//                Intent intent = new Intent(getApplicationContext(), SpacialNotificationAc.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                NotificationWrapper.getInstance().showBasicNotification(
//                        getApplicationContext(),
//                        "普通通知",
//                        "您有一条消息",
//                        1,
//                        intent,
//                        1);
//            }
//        });
    }


    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_nomal) {
            Log.e("", "点击通知");
            int nid = (int) (System.currentTimeMillis() / 1000);
            String title = "新消息";
            String content = "今天的天噶额外的蓝，要不要出去喝一杯哈哈哈哈哈哈哈哈哈哈哈·1哈哈哈哈哈哈哈哈哈安徽哈哈 啊啊啊  啊啊 ";
            new NotificationWrapper().newNotification(getApplicationContext(), R.drawable.msg_icon_client, title, content)
                    .setTarget(SpacialNotificationAc.class, 1, false)
                    .setBigText("大字体啊啊啊啊")
                    .setSound(R.raw.notification1)
//                    .setLargeImage(R.drawable.msg_icon_group)
                    .show(nid);
        }
    }
}
