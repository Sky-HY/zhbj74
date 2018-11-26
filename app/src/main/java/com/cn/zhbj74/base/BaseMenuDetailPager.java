package com.cn.zhbj74.base;

import android.app.Activity;
import android.view.View;

/**
 * 菜单标签页基类
 */
public abstract class BaseMenuDetailPager {

    public Activity mActivity;
    public View rootView;
    public BaseMenuDetailPager(Activity mActivity) {
        this.mActivity = mActivity;
        rootView = initUI();
    }


    public abstract View initUI();

    public void initData(){

    }
}
