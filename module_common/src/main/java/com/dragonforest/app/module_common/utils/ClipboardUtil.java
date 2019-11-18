package com.dragonforest.app.module_common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 复制粘贴工具类
 *
 * @author DragonForest
 * @date 2019/11/18 12:29
 */
public class ClipboardUtil {

    private static final String LABEL_TEXT = "dragonText";
    private static final String LABEL_URL = "dragonUrl";
    private static final String LABEL_INTENT = "dragonIntent";

    /**
     * 复制文字到剪切板
     *
     * @param context
     * @param content
     */
    public static void copyTextToPlate(Context context, String content) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(LABEL_TEXT, content);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 复制url到剪切板
     *
     * @param context
     * @param url
     */
    public static void copyUrlToPlate(Context context, String url) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newRawUri(LABEL_URL, Uri.parse(url));
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 复制intent到剪切板
     *
     * @param context
     * @param intent
     */
    public static void copyIntentToPlate(Context context, Intent intent) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newIntent(LABEL_INTENT, intent);
        clipboardManager.setPrimaryClip(clipData);
    }

    /**
     * 从剪切板获取文字
     *
     * @param context
     * @return
     */
    public static String getTextFromPlate(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip.getItemCount() > 0) {
            ClipData.Item itemAt = primaryClip.getItemAt(primaryClip.getItemCount() - 1);
            return itemAt.getText().toString();
        }else{
            return null;
        }
    }

    /**
     * 从剪切板获取uri
     *
     * @param context
     * @return
     */
    public static Uri getUriFromPlate(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip.getItemCount() > 0) {
            ClipData.Item itemAt = primaryClip.getItemAt(primaryClip.getItemCount() - 1);
            return itemAt.getUri();
        }else{
            return null;
        }
    }

    /**
     * 从剪切板获取intent
     *
     * @param context
     * @return
     */
    public static Intent getIntentFromPlate(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData primaryClip = clipboardManager.getPrimaryClip();
        if (primaryClip.getItemCount() > 0) {
            ClipData.Item itemAt = primaryClip.getItemAt(primaryClip.getItemCount() - 1);
            return itemAt.getIntent();
        }else{
            return null;
        }
    }

}
