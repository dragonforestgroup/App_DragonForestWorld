package com.dragonforest.app.module_common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 图片工具类
 *
 * @author 韩龙林
 * @date 2019/8/19 15:15
 */
public class ImageUtil {

    private static ImageUtil instance;

    public static ImageUtil getInstance() {
        if (instance == null) {
            instance = new ImageUtil();
        }
        return instance;
    }

    /**
     * 从文件中加载图片文件 并加载到内存
     *
     * @param path
     * @param dstWidth
     * @param dstHeight
     * @return
     * @paran isSpecific 是否精确压缩  精确压缩可以准确压缩到指定的像素，非精确压缩可以保持原长宽比
     */
    public Bitmap compressLoadFromFile(String path, int dstWidth, int dstHeight, boolean isSpecific) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        // 先进行采样率压缩 避免从文件中读入oom， 但是不能压缩到精确的值
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        int sampleSize = calculateSimpleSize(outWidth, outHeight, dstWidth, dstHeight);
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);

        // 在进行双线性采样率压缩，可以压缩到精确的值
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, dstWidth, dstHeight, true);
        return scaledBitmap;
    }

    public Bitmap compressLoadFromFile(String path, int dstWidth, int dstHeight) {
        return compressLoadFromFile(path, dstWidth, dstHeight, false);
    }

    /**
     * 压缩磁盘图片文件
     *
     * @param srcPath
     * @param dstPath
     * @param dstHeight
     * @param dstWidth
     * @return
     */
    public boolean compressImageFile(String srcPath, String dstPath, int dstHeight, int dstWidth) {
        Bitmap bitmap = compressLoadFromFile(srcPath, dstWidth, dstHeight);
        if (bitmap == null) {
            return false;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(dstPath));
            fos.write(bos.toByteArray());
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 计算压缩比
     *
     * @param outWidth
     * @param outHeight
     * @param dstWidth
     * @param dstHeight
     * @return
     */
    private int calculateSimpleSize(int outWidth, int outHeight, int dstWidth, int dstHeight) {
        if (dstHeight != 0 && dstWidth != 0) {
            return Math.min(outWidth / dstWidth, outHeight / dstHeight);
        }
        return 1;
    }
}
