package com.dragonforest.view.module_notification.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author DragonForest
 * @date 2019/11/15 13:55
 */
public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int nid = intent.getIntExtra("nid", -1);
        String action = intent.getAction();
        Log.e("NotificationService-->", "onStartCommand()启动通知服务, nid=" + nid+",action="+action);
        return super.onStartCommand(intent, flags, startId);
    }
}
