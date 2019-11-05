package com.dragonforest.app.dragonforestworld.service;

import android.app.IntentService;
import android.content.Intent;

import com.dragonforest.app.dragonforestworld.activity.MainActivity;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.NotificationUtil;

/**
 * @author 韩龙林
 * @date 2019/10/8 17:38
 */
public class MainService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MainService() {
        super("MainService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Intent intent0 = new Intent(this, MainActivity.class);
//        intent0.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        NotificationUtil.getInstance().showHangNotification(3, NotificationUtil.CHANNEL_ID_NOTICE, this, "悬挂式通知", android.R.drawable.ic_notification_overlay, android.R.drawable.ic_notification_overlay, intent0);

        LogUtil.E("onHandleIntent", "当前线程：" + Thread.currentThread().getName());
    }


}
