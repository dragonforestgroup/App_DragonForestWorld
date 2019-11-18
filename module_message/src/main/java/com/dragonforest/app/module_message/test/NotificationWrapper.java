package com.dragonforest.app.module_message.test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
                .setGroup(GROUP_KEY)
                .setGroupSummary(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(context);
            builder.setChannelId(CHANNEL_ID);
        }
        return this;
    }

    public NotificationWrapper setTarget(Class cls, int requestCode, boolean isSpacial) {

        if (isSpacial) {
            /*
                特殊的目标
                回退即关闭
             */
            Intent intent = new Intent(context, cls);
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
            Intent resultIntent = new Intent(context, cls);
            // Create the TaskStackBuilder and add the intent, which inflates the back stack
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
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

    public void show(int notificationId) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, this.builder.build());
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = CHANNEL_NAME;
            String description = CHANNEL_DESCRIPTION;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
