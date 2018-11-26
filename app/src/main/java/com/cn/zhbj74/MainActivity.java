package com.cn.zhbj74;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;

import com.cn.zhbj74.base.imp.menu.NewsMenuDetailPager;
import com.cn.zhbj74.fragment.ContentFragment;
import com.cn.zhbj74.fragment.LeftMenuFragment;

/**
 * 主页面
 */
public class MainActivity extends FragmentActivity {
    //常量
    private static final String TAG_LEFT_MENU = "TAG_LEFT_MENU";
    private static final String TAG_CONTENT = "TAG_CONTENT";
    public DrawerLayout dl_root;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl_root = findViewById(R.id.dl_root);

        // fragment事务
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        beginTransaction.replace(R.id.fl_menu, new LeftMenuFragment(), TAG_LEFT_MENU);
        beginTransaction.replace(R.id.fl_content, new ContentFragment(), TAG_CONTENT);
        beginTransaction.commit();

    }

    /**
     * 获取LeftMenuFragment
     * @return
     */
    public LeftMenuFragment getLeftMenuFragment(){
        return (LeftMenuFragment) mFragmentManager.findFragmentByTag(TAG_LEFT_MENU);
    }

    /**
     * 获取ContentFragment
     * @return
     */
    public ContentFragment getContentFragment(){
        return (ContentFragment) mFragmentManager.findFragmentByTag(TAG_CONTENT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 让新闻专题的标签页索引置零
        NewsMenuDetailPager.mCurrentPagerItemId = 0;
    }
}
