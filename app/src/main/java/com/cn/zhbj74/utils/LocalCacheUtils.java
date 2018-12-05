package com.cn.zhbj74.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 本地缓存
 */
public class LocalCacheUtils {
    private static final String TAG = "LocalCacheUtils";
    private static final String LOCAL_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj74";
    private MemoryCacheUtils mMemoryCacheUtils;

    public LocalCacheUtils() {
        mMemoryCacheUtils = new MemoryCacheUtils();
    }

    // 写缓存
    public void setLocalCache(String url, Bitmap bitmap) {
        File file = new File(LOCAL_CACHE_PATH);
        // 文件夹不存在或者不是文件夹，就创建文件夹
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        File cacheFile = new File(file, Md5Util.encoder(url));
        try {
            // 将图片缓存在本地
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(cacheFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    // 读缓存
    public Bitmap getLoaclCache(String url) {
        File cacheFile = new File(LOCAL_CACHE_PATH + "/" + Md5Util.encoder(url));
        Bitmap bitmap = null;
        if (cacheFile.exists()) {
            bitmap = BitmapFactory.decodeFile(cacheFile.getAbsolutePath());
            // 写入内存
            if (bitmap != null) {
                mMemoryCacheUtils.setMemoryCache(url, bitmap);
            }
        }

        return bitmap;
    }
}
