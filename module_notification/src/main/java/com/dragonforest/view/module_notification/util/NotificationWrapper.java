package com.dragonforest.view.module_notification.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.dragonforest.view.module_notification.receiver.NotificationReceiver;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author DragonForest
 * @date 2019/11/13 11:37
 */
public class NotificationWrapper {
    /*
        样式分类：
        1.基本类型 （标题+文字）
        2.

        功能分类：
        1.点击进入现有的一个activity(加入回退栈)
        2.点击进入特殊的一个activity(不加入回退栈)
     */

    private final static String CHANNEL_ID = "15621485910";
    private final static String CHANNEL_NAME = "DragonForest消息通知专用渠道";
    private final static String CHANNEL_DESCRIPTION = "用来接收消息的通知";
    private final static String GROUP_KEY = "DragonForestGroup";


    private NotificationCompat.Builder builder;
    private Context context;
    private String title;
    private String content;


    public void init(Context context) {
        createNotificationChannel(context);
    }


    public NotificationWrapper newNotification(Context context, int icon, String title, String content) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setGroup(GROUP_KEY)
                .setGroupSummary(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
            builder.setChannelId(CHANNEL_ID);
        }
        return this;
    }

    public NotificationWrapper setTarget(Class cls, HashMap<String, Object> paramsHashMap, int requestCode, boolean isSpacial) {
        Intent intent = new Intent(context, cls);
        if (paramsHashMap != null) {
            for (String key : paramsHashMap.keySet()) {
                Object value = paramsHashMap.get(key);
                if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) paramsHashMap.get(key));
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (Float) paramsHashMap.get(key));
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (Double) paramsHashMap.get(key));
                }
                if (value instanceof String) {
                    intent.putExtra(key, (String) paramsHashMap.get(key));
                }
                if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) paramsHashMap.get(key));
                }
            }
        }
        if (isSpacial) {
            /*
                特殊的目标
                回退即关闭
             */
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            this.builder.setContentIntent(pendingIntent);
        } else {
            /*
                带有回退栈的目标
                添加目标，会将目标连同其父节点（需要在AndroidManiest中定义parentActivityName属性指定层级关系）加入回退栈。
                这样按返回键的时候会回退activity到上一个，而不是直接退出
             */
            // Create an Intent for the activity you want to start
//            Intent resultIntent = new Intent(context, cls);
            // Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(intent);
            Log.e("NotificationWrapper", "setTarget intent counts" + stackBuilder.getIntentCount());
            // Get the PendingIntent containing the entire back stack
            PendingIntent pendingIntent =
                    stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_UPDATE_CURRENT);
            this.builder.setContentIntent(pendingIntent);
        }
        return this;
    }

    public NotificationWrapper setBigText(String text) {
        this.builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(text));
        return this;
    }

    public NotificationWrapper setLargeImage(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        this.builder
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null));

        return this;
    }

    public NotificationWrapper setSound(int soundId) {
        this.builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + soundId));
        return this;
    }

    public NotificationWrapper setVibrate(long[] pattern) {
        /*
            pattern数组第一个参数表示延迟震动时间
            第二个参数表示震动持续时间
            第三个参数表示震动后的休眠时间
            第四个参数又表示震动持续时间
            第五个参数也表示正到休眠时间
            以此类推
         */
        this.builder.setVibrate(pattern);
        return this;
    }

    public NotificationWrapper setFullScreen(Class cls, HashMap<String, Object> paramsHashMap, int requestCode) {
        Intent intent = new Intent(context, cls);
        if (paramsHashMap != null) {
            for (String key : paramsHashMap.keySet()) {
                Object value = paramsHashMap.get(key);
                if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) paramsHashMap.get(key));
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (Float) paramsHashMap.get(key));
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (Double) paramsHashMap.get(key));
                }
                if (value instanceof String) {
                    intent.putExtra(key, (String) paramsHashMap.get(key));
                }
                if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) paramsHashMap.get(key));
                }
            }
        }
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        this.builder.setFullScreenIntent(fullScreenPendingIntent, true);
        return this;
    }


    public NotificationWrapper setMessage(String text, String sender) {
        NotificationCompat.MessagingStyle.Message message1 =
                new NotificationCompat.MessagingStyle.Message(text, 1000, sender);
        this.builder.setStyle(new NotificationCompat.MessagingStyle("回复")
                .addMessage(message1));
        return this;
    }

    public NotificationWrapper addAction(int icon, String title, Class target, HashMap<String, Object> paramsHashMap, String action) {
        Intent intent = new Intent(context, target);
        if (paramsHashMap != null) {
            for (String key : paramsHashMap.keySet()) {
                Object value = paramsHashMap.get(key);
                if (value instanceof Integer) {
                    intent.putExtra(key, (Integer) paramsHashMap.get(key));
                }
                if (value instanceof Float) {
                    intent.putExtra(key, (Float) paramsHashMap.get(key));
                }
                if (value instanceof Double) {
                    intent.putExtra(key, (Double) paramsHashMap.get(key));
                }
                if (value instanceof String) {
                    intent.putExtra(key, (String) paramsHashMap.get(key));
                }
                if (value instanceof Serializable) {
                    intent.putExtra(key, (Serializable) paramsHashMap.get(key));
                }
            }
        }
        intent.setAction(action);
        PendingIntent snoozePendingIntent = null;
        if (BroadcastReceiver.class.isAssignableFrom(target)) {
            snoozePendingIntent =
                    PendingIntent.getBroadcast(context, 0, intent, 0);
        } else if (Service.class.isAssignableFrom(target)) {
            snoozePendingIntent =
                    PendingIntent.getService(context, 0, intent, 0);
        } else if (Activity.class.isAssignableFrom(target)) {
            snoozePendingIntent =
                    PendingIntent.getActivity(context, 0, intent, 0);
        }
        if (snoozePendingIntent == null) {
            Log.e("NotificationWrapper-->", "target 只支持基本组件，不能是其他类型");
            return this;
        }
        this.builder.addAction(icon, title, snoozePendingIntent);
        return this;
    }

    public void show(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, this.builder.build());
    }

    public static void cancel(Context context, int id) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(id);
    }


    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            // 8.0之后的版本设置了此处之后可实现悬挂式
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
