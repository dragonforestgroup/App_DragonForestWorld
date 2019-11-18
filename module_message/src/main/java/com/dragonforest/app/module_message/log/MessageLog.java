package com.dragonforest.app.module_message.log;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 消息日志
 *
 * @author DragonForest
 * @date 2019/11/7 17:11
 */
public class MessageLog {

    /**
     * 消息记录日志文件
     */
    private static final String LOG_MESSAGE_RECORD_FILE = "dragonMessageLog.log";

    /**
     * 消息异常日志文件
     */
    private static final String LOG_MESSAGE_EXCEPTION_FILE = "dragonMessageExceptionLog.log";

    static boolean isDebug = true;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    /**
     * 保存消息记录
     *
     * @param context
     * @param msg
     */
    public static void D(Context context, String msg) {
        if (isDebug) {
            // 保存进文件
            saveToFile(context.getFilesDir().getAbsolutePath() + File.separator + LOG_MESSAGE_RECORD_FILE, msg);
        }
    }

    /**
     * 保存消息异常记录
     *
     * @param context
     * @param msg
     */
    public static void E(Context context, String msg) {
        // 保存进文件
        saveToFile(context.getFilesDir().getAbsolutePath() + File.separator + LOG_MESSAGE_EXCEPTION_FILE, msg);
    }

    private static void saveToFile(String filePath, String msg) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            writer.write("[" + sdf.format(new Date()) + "] : -->> " + msg);
            writer.write("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
