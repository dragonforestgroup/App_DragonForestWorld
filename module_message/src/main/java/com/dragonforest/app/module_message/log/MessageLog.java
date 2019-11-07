package com.dragonforest.app.module_message.log;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * 消息日志
 *
 * @author DragonForest
 * @date 2019/11/7 17:11
 */
public class MessageLog {

    /**
     * 日志文件
     */
    private static String LOG_FILE="dragonMessageLog.log";

    static boolean isDebug=true;
    public static void D(Context context, String msg){
        if(isDebug){
            // 保存进文件
            saveToFile(context,msg);
        }
    }

    private static void saveToFile(Context context,String msg){
        try {
            FileWriter writer=new FileWriter(context.getFilesDir().getAbsolutePath()+ File.separator+LOG_FILE,true);
            writer.write(msg);
            writer.write("\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
