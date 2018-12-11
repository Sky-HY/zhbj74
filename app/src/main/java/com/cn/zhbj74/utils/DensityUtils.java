package com.cn.zhbj74.utils;

import android.content.Context;

/**
 * dpi转px 和 px转dpi的工具类
 */
public class DensityUtils {
    // dpi转换px
    public static int dpi2px(float dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    // px转换dpi
    public static float px2dpi(int px, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return px / density;
    }

}
