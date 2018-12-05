package com.cn.zhbj74.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.cn.zhbj74.R;

/**
 * 加载图片工具类
 * 三级缓存
 */
public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    private NetCacheUtils mNetCacheUtils;
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public BitmapUtils() {
        mNetCacheUtils = new NetCacheUtils();
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
    }


    // 获取图片
    public void display(String url, ImageView imageView) {
        // 设置默认图片
        imageView.setImageResource(R.drawable.pic_item_list_default);

        // 从内存读取图片
        Bitmap bitmap = mMemoryCacheUtils.getMemoryCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            LogUtil.i(TAG, "从内存获取图片");
            return;
        }

        // 从本地读取图片
        bitmap = mLocalCacheUtils.getLoaclCache(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            LogUtil.i(TAG, "从本地获取图片");
            return;
        }

        // 从网络获取图片
        mNetCacheUtils.getBitmapFromNet(url, imageView);
    }
}
