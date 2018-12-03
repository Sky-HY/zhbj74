package com.cn.zhbj74.utils;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;

/**
 * 获取上下文
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 注入shareSDK
        MobSDK.init(this);
    }

    /**
     * 调用此方法，可以随时随地获取上下文Context
     * @return context
     */
    public static Context getContext() {
        return context;
    }
}
