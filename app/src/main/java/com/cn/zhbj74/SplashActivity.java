package com.cn.zhbj74;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.cn.zhbj74.global.ConstanValues;
import com.cn.zhbj74.utils.MyApplication;
import com.cn.zhbj74.utils.SpUtils;

/**
 * Splash界面
 */
public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rl_root;
    private AnimationSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //初始化控件
        initUI();
        //初始化动画
        initAnimation();
        // 开始动画
        rl_root.startAnimation(set);
        // 给动画设置监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            // 动画执行完毕
            @Override
            public void onAnimationEnd(Animation animation) {
                // 是否第一次进入应用
                boolean isFirstEnter = SpUtils.getBoolean(MyApplication.getContext(), ConstanValues.IS_FIRST_ENTER, true);
                Intent intent;
                if (isFirstEnter) {
                    // 进入新手引导界面
                    intent = new Intent(MyApplication.getContext(), GuideActivity.class);
                } else {
                    // 进入主界面
                    intent = new Intent(MyApplication.getContext(), MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        // 旋转动画
        RotateAnimation rotateAnima = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnima.setDuration(1000);
        rotateAnima.setFillAfter(true);
        // 缩放动画
        ScaleAnimation scaleAnim = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(1000);
        scaleAnim.setFillAfter(true);
        // 透明动画
        AlphaAnimation alphaAnim = new AlphaAnimation(0, 1);
        alphaAnim.setDuration(2000);
        alphaAnim.setFillAfter(true);
        // 动画集合
        set = new AnimationSet(true);
        set.addAnimation(rotateAnima);
        set.addAnimation(scaleAnim);
        set.addAnimation(alphaAnim);

    }

    /**
     * 初始化控件
     */
    private void initUI() {
        rl_root = findViewById(R.id.rl_root);
    }
}
