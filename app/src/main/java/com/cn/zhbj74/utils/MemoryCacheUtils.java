package com.cn.zhbj74.utils;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 内存缓存
 * <p>
 * softReference 软引用
 * weakReference 弱引用
 * phantomReference 虚引用
 * <p>
 * api level 9后Google推荐使用LruCache来代替软引用或者弱引用
 * <p>
 * 最近最少未使用
 * LruCache 缓存机制:底层lineHashMap
 */
public class MemoryCacheUtils {
    // 软引用，方便垃圾回收器回收
    //private static Map<String, SoftReference<Bitmap>> cacheMap = new HashMap<>();
    private static final String TAG = "MemoryCacheUtils";

    // 先计算分配给应用的内存
    // 将内存的1/8用来缓存图片
    private static LruCache<String, Bitmap> mLruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory() / 8)) {
        @Override
        protected int sizeOf(String key, Bitmap value) {
            // 计算图片的字节数
            return value.getByteCount();
        }
    };


    // 写入内存
    public void setMemoryCache(String url, Bitmap bitmap) {
        mLruCache.put(url, bitmap);
        LogUtil.i(TAG, "写入内存");
    }

    // 取出图片
    public Bitmap getMemoryCache(String url) {
        // 软引用
//        SoftReference<Bitmap> bitmapSoftReference = cacheMap.get(url);
//        Bitmap bitmap = null;
//        if (bitmapSoftReference != null) {
//            bitmap = bitmapSoftReference.get();
//        }
//        return bitmap;
        return mLruCache.get(url);

    }
}
