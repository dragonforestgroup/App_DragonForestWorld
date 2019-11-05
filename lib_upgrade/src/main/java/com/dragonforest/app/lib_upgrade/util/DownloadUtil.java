package com.dragonforest.app.lib_upgrade.util;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.dragonforest.app.module_common.utils.LogUtil;

import java.io.File;

/**
 * @author 韩龙林
 * @date 2019/10/9 11:15
 */
public class DownloadUtil {
    private Context context;
    private DownloadManager downloadManager = null;
    private static DownloadUtil instance = null;

    public DownloadUtil(Context context) {
        this.context = context;
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static DownloadUtil getInstance(Context context) {
        if (instance == null) {
            instance = new DownloadUtil(context);
        }
        return instance;
    }

    public long startDownload(String url, String title, String description, String fileName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 设置允许下载时的网络类型
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        // 设置下载通知显示
        //VISIBILTY_HIDDEN: Notification:将不会显示，如果设置该属性的话，必须要添加权限 。Android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
        //VISIBILITY_VISIBLE： Notification显示，但是只是在下载任务执行的过程中显示，下载完成自动消失。（默认值）
        //VISIBILITY_VISIBLE_NOTIFY_COMPLETED : Notification显示，下载进行时，和完成之后都会显示。
        //VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION ：只有当任务完成时，Notification才会显示。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        // 设置下载后保存的路径
        // 这个文件是应用专属，软件卸载后，下载的文件也将全部删除
        // file:///storage/emulated/0/Android/data/your-package/files/Download/update.apk
//        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName);
        File savedFile = new File(context.getExternalCacheDir().getAbsolutePath() + File.separator + fileName);
        LogUtil.D("DownloadUtil", "开始下载文件-->" + savedFile.getAbsolutePath());
        Uri uri = Uri.fromFile(savedFile);
        request.setDestinationUri(uri);
        request.setTitle(title);
        request.setDescription(description);
        return downloadManager.enqueue(request);
    }

    /**
     * 获取下载路径
     *
     * @param downloadId
     * @return
     */
    public String getDownloadPath(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    String downloadPath = c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                    if (downloadPath.startsWith("file://")) {
                        downloadPath = downloadPath.split("file://")[1];
                    }
                    return downloadPath;
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /**
     * 获取下载地址
     *
     * @param downloadId
     * @return
     */
    public Uri getDownloadUri(long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }

    /**
     * 获取下载状态
     *
     * @param downloadId
     * @return
     */
    public int getDownloadStatus(long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                c.close();
            }
        }
        return -1;
    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public void removeDownloadId(long downloadId) {
        downloadManager.remove(downloadId);
    }
}
