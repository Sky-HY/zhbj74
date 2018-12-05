package com.cn.zhbj74;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.Toast;

import com.cn.zhbj74.base.imp.menu.NewsMenuDetailPager;
import com.cn.zhbj74.fragment.ContentFragment;
import com.cn.zhbj74.fragment.LeftMenuFragment;
import com.cn.zhbj74.utils.ToastUtils;

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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100&&grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

        }else{
            ToastUtils.showToast("请在设置中心去授权，否则会消耗更多的流量",Toast.LENGTH_SHORT);
        }

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
