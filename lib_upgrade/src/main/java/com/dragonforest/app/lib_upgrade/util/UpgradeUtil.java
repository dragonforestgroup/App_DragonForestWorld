package com.dragonforest.app.lib_upgrade.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.dragonforest.app.module_common.utils.LogUtil;

import java.io.File;

/**
 * 升级类
 *
 * @author 韩龙林
 * @date 2019/10/9 10:32
 */
public class UpgradeUtil {
    private static UpgradeUtil instance;
    private Context context;

    private UpgradeUtil(Context context) {
        this.context = context;
    }

    public static UpgradeUtil getInstance(Context context) {
        if (instance == null) {
            instance = new UpgradeUtil(context);
        }
        return instance;
    }

    /**
     * 检查版本
     * @param serverVersionCode
     * @return  true需要更新  false不需要更新
     */
    public boolean checkUpgrade(int serverVersionCode){
        long localVersionCode = getLocalVersionCode();
        LogUtil.D("UpgradeUtil", "获取本机版本号-->" + localVersionCode);

        if (localVersionCode == -1) {
            LogUtil.E("UpgradeUtil", "获取本机版本号失败！");
            return false;
        }
        if (serverVersionCode <= localVersionCode) {
            LogUtil.E("UpgradeUtil", "已经是最新版本！！");
            return false;
        }
        return true;
    }

    /**
     * 开始更新
     * </br>
     * // 先检查本地是否已经下载
     * // 若已经下载，则直接安装，否则先下载
     * @param downloadUrl
     */
    public void startUpgrade(String downloadUrl) {
        long savedDownloadId = getDownloadID();
        if (savedDownloadId != -1) {
            // 已经有下载记录
            int downloadStatus = DownloadUtil.getInstance(context).getDownloadStatus(savedDownloadId);
            if (downloadStatus == DownloadManager.STATUS_SUCCESSFUL) {
                String downloadFilePath = DownloadUtil.getInstance(context).getDownloadPath(savedDownloadId);
                if (downloadFilePath != null) {
                    File apkFile = new File(downloadFilePath);
                    if (apkFile.exists()) {
                        LogUtil.D("UpgradeUtil", "文件下载过->" + downloadFilePath);
                        startInstall(downloadFilePath);
                        return;
                    } else {
                        LogUtil.E("UpgradeUtil", "下载过的文件已经删除！" + downloadFilePath);
                    }
                }
            }
        }
        startDownload(downloadUrl, "下载中", "马上就好");
    }

    /**
     * 开始安装
     *
     * @param downloadFilePath
     */
    public void startInstall(String downloadFilePath) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        Uri uri;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,  "com.dragonforest.app.dragonforestworld.fileprovider", new File(downloadFilePath));
            // 给目标应用一个临时授权
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(downloadFilePath));
        }
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

    /**
     * 开始下载 并保存下载任务id到本地
     *
     * @param downloadUrl
     * @param title
     * @param descrip
     */
    private void startDownload(String downloadUrl, String title, String descrip) {
        if (!downloadUrl.startsWith("http")) {
            LogUtil.E("UpgradeUtil", "下载路径不合法，无法进行下载！！");
            return;
        }
        String appName = downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1);
        if (!appName.endsWith(".apk")) {
            LogUtil.E("UpgradeUtil", appName + "-> 该文件不是apk文件！！");
            return;
        }
        LogUtil.E("UpgradeUtil", "要保存的apk文件->" + appName);

        // 进行更新
        long downloadId = DownloadUtil.getInstance(context).startDownload(downloadUrl,
                "下载中",
                "马上就好",
                appName);
        saveDownloadID(downloadId);
    }

    /**
     * 保存下载任务id到本地
     *
     * @param downloadId
     */
    private void saveDownloadID(long downloadId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong("downloadId", downloadId);
        edit.commit();
    }

    /**
     * 获取本地下载任务id
     *
     * @return
     */
    private long getDownloadID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getLong("downloadId", -1);
    }

    /**
     * 获取本地版本号
     *
     * @return
     */
    private long getLocalVersionCode() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取本地版本名
     *
     * @return
     */
    private String getLocalVersionName() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取包名
     *
     * @return
     */
    private String getPackageName() {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

