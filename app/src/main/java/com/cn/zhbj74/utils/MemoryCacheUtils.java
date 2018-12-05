package com.cn.zhbj74.utils;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 内存缓存
 * <p>
 * softReference 软引用
 * weakReference 弱引用
 * phantomReference 虚引用
 */
public class MemoryCacheUtils {
    // 软引用，方便垃圾回收器回收
    private static Map<String, SoftReference<Bitmap>> cacheMap = new HashMap<>();
    private static final String TAG = "MemoryCacheUtils";

    // 写入内存
    public void setMemoryCache(String url, Bitmap bitmap) {

        cacheMap.put(url, new SoftReference<Bitmap>(bitmap));
    }

    // 取出图片
    public Bitmap getMemoryCache(String url) {
        // 软引用
        SoftReference<Bitmap> bitmapSoftReference = cacheMap.get(url);
        Bitmap bitmap = null;
        if (bitmapSoftReference != null) {
            bitmap = bitmapSoftReference.get();
        }
        return bitmap;
    }
}
