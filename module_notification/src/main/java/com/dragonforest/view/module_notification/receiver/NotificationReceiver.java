package com.dragonforest.view.module_notification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dragonforest.view.module_notification.util.NotificationWrapper;

/**
 * @author DragonForest
 * @date 2019/11/15 09:38
 */
public class NotificationReceiver extends BroadcastReceiver {

    public static String ACTION_CANCEL="android.dragonforest.receiver.action.cancel";
    public static String ACTION_START="android.dragonforest.receiver.action.start";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(ACTION_CANCEL.equals(action)){
            int nid = intent.getIntExtra("nid", -1);
            if(nid!=-1){
                NotificationWrapper.cancel(context,nid);
                Log.e("NotificationReceiver","接收到通知，准备取消"+nid);
            }
        }
        else if(ACTION_START.equals(action)){
            Log.e("NotificationReceiver","接收到通知，准备进入");
        }
    }
}
