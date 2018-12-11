package com.cn.zhbj74.utils;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.umeng.commonsdk.UMConfigure;

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
        UMConfigure.init(this, "5c0e4566f1f5566be50000bf", "hy", UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    /**
     * 调用此方法，可以随时随地获取上下文Context
     *
     * @return context
     */
    public static Context getContext() {
        return context;
    }
}
