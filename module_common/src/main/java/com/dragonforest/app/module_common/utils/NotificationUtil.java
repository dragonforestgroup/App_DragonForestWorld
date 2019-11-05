package com.dragonforest.app.module_common.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.dragonforest.app.module_common.R;

/**
 * 通知工具类
 * 3种通知：
 * 普通通知，  折叠式通知，   悬挂式通知
 *
 * @author 韩龙林
 * @date 2019/9/22 13:45
 */
public class NotificationUtil {

    public final static String CHANNEL_ID_MESSAGE = "message";
    public final static String CHANNEL_ID_NOTICE = "notice";

    private static NotificationUtil instance = null;

    private NotificationUtil() {
    }

    public static NotificationUtil getInstance() {
        if (instance == null) {
            instance = new NotificationUtil();
        }
        return instance;
    }

    /**
     * 普通通知
     *
     * @param id
     * @param context
     * @param title
     * @param smallIcon
     * @param largeIcon
     * @param intent
     */
    public void showNormalNotification(int id, String channelId, Context context, String title, int smallIcon, int largeIcon, Intent intent) {
        Notification.Builder notificationBuilder = createNotificationBuilder(channelId, context, title, smallIcon, largeIcon);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int)(System.currentTimeMillis()/1000), intent, 0);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        getManager(context).notify(id, notificationBuilder.build());
    }


    /**
     * 折叠式通知 展开的view在另一个进程中 remoteView
     *
     * @param id
     * @param context
     * @param title
     * @param smallIcon
     * @param largeIcon
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.N)
    public void showFoldNotification(int id, String channelId, Context context, String title, int smallIcon, int largeIcon, Intent intent) {
        Notification.Builder notificationBuilder = createNotificationBuilder(channelId, context, title, smallIcon, largeIcon);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, 0);
        notificationBuilder.setContentIntent(pendingIntent);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.remoteview_notify1);
        Notification notification = notificationBuilder.build();
        notification.bigContentView = remoteViews;
        getManager(context).notify(id, notification);
    }


    /**
     * 悬挂式通知 setFullScreen()
     *
     * @param id
     * @param context
     * @param title
     * @param smallIcon
     * @param largeIcon
     * @param intent
     */
    public void showHangNotification(int id, String channelId, Context context, String title, int smallIcon, int largeIcon, Intent intent) {
        Notification.Builder notificationBuilder = createNotificationBuilder(channelId, context, title, smallIcon, largeIcon);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, intent, 0);
        notificationBuilder.setContentIntent(pendingIntent);

        Intent hangIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jd.com"));
        PendingIntent hangPendingIntent = PendingIntent.getActivity(context, 0, hangIntent, 0);
        notificationBuilder.setFullScreenIntent(hangPendingIntent, true);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.remoteview_notify1);
        Notification notification = notificationBuilder.build();
        notification.bigContentView = remoteViews;

        getManager(context).notify(id, notification);
    }

    private Notification.Builder createNotificationBuilder(String channelId, Context context, String title, int smallIcon, int largeIcon) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentText(title);
        builder.setSmallIcon(smallIcon);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), largeIcon));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId);
        }
        return builder;
    }

    private NotificationManager getManager(Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 兼容android 7.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(DragonChannelList.messageChannel);
            manager.createNotificationChannel(DragonChannelList.noticeChannel);
        }
        return manager;
    }

    @TargetApi(Build.VERSION_CODES.O)
    public static class DragonChannelList {
        public static NotificationChannel messageChannel = new NotificationChannel(CHANNEL_ID_MESSAGE, "消息通知", NotificationManager.IMPORTANCE_LOW);
        public static NotificationChannel noticeChannel = new NotificationChannel(CHANNEL_ID_NOTICE, "公告", NotificationManager.IMPORTANCE_LOW);
    }

}
