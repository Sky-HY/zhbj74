package com.cn.zhbj74.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 从网络加载图片
 */
public class NetCacheUtils {
    private static final String TAG = "NetCacheUtils";
    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils() {
        mLocalCacheUtils = new LocalCacheUtils();
        mMemoryCacheUtils = new MemoryCacheUtils();
    }

    // 从服务器获取图片
    public void getBitmapFromNet(String url, ImageView imageView) {
        // AsyncTast异步连接网络，并设置图片
        new BitmapTask().execute(url, imageView);
    }

    // 异步操作
    private class BitmapTask extends AsyncTask<Object, Void, Bitmap> {

        private String url;
        private ImageView imageview;

        @Override
        protected void onPreExecute() {
        }

        // 连接网络获取图片
        @Override
        protected Bitmap doInBackground(Object... objects) {
            url = (String) objects[0];
            imageview = (ImageView) objects[1];
            return download();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            String tag = (String) imageview.getTag();
            if (bitmap != null && url.equals(tag)) {
                imageview.setImageBitmap(bitmap);
                LogUtil.i(TAG, "从网络获取图片");
                // 设置本地缓存
                mLocalCacheUtils.setLocalCache(url, bitmap);
                // 写入内存
                mMemoryCacheUtils.setMemoryCache(url, bitmap);
            }
        }

        // 下载图片
        @Nullable
        private Bitmap download() {
            HttpURLConnection conn = null;
            Bitmap bitmap = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.connect();
                int code = conn.getResponseCode();
                if (code == 200) {
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return bitmap;
        }
    }
}
