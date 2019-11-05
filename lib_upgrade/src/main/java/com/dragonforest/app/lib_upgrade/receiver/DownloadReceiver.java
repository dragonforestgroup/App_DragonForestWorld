package com.dragonforest.app.lib_upgrade.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dragonforest.app.lib_upgrade.util.DownloadUtil;
import com.dragonforest.app.lib_upgrade.util.UpgradeUtil;
import com.dragonforest.app.module_common.utils.LogUtil;

/**
 * @author 韩龙林
 * @date 2019/10/9 11:12
 */
public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtil.E("DownloadReceiver","接收到广播-->"+action);
        if(action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            LogUtil.E("DownloadReceiver","下载完成，开始安装--->");
            long downloadApkId =intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            String downloadPath = DownloadUtil.getInstance(context).getDownloadPath(downloadApkId);
            UpgradeUtil.getInstance(context).startInstall(downloadPath);
        }
    }
}
