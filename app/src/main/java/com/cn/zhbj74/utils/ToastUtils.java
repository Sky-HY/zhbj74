package com.cn.zhbj74.utils;

import android.widget.Toast;

/**
 * toast工具类
 */
public class ToastUtils {

    private static Toast toast;
    // 显示信息要分开设置，否则不兼容小米手机
    public static void showToast(String content, int duration) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), null, duration);
        } else {
            toast.setDuration(duration);
        }
        toast.setText(content);
        toast.show();
    }
}
