package com.cn.zhbj74;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.cn.zhbj74.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class NewsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NewsDetailActivity";
    private ImageButton ib_back, ib_textsize, ib_share;
    private WebView wv_news;
    private ProgressBar pb_news_load;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        findViewById(R.id.ib_menu).setVisibility(View.GONE);
        findViewById(R.id.ll_ctrl).setVisibility(View.VISIBLE);

        ib_back = findViewById(R.id.ib_back);
        ib_textsize = findViewById(R.id.ib_textsize);
        ib_share = findViewById(R.id.ib_share);
        wv_news = findViewById(R.id.wv_news);
        pb_news_load = findViewById(R.id.pb_news_load);
        // 后退按钮可见
        ib_back.setVisibility(View.VISIBLE);
        String newsUrl = getIntent().getStringExtra("newsUrl");
        LogUtil.i(TAG, "url:" + newsUrl);

        // 设置点击事件
        ib_back.setOnClickListener(this);
        ib_textsize.setOnClickListener(this);
        ib_share.setOnClickListener(this);

        // 显示网页
        wv_news.loadUrl(newsUrl);

        mSettings = wv_news.getSettings();
        mSettings.setBuiltInZoomControls(true);// 设置支持缩放(wap网页不支持)
        mSettings.setUseWideViewPort(true);// 设置支持双击缩放(wap网页不支持)
        mSettings.setJavaScriptEnabled(true);// 设置支持js

        wv_news.setWebViewClient(new WebViewClient() {
            // 网页开始加载
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            // 网页加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                pb_news_load.setVisibility(View.GONE);
            }

            // 跳转连接
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                // 后退按钮
                finish();
                break;
            case R.id.ib_textsize:
                // 弹出对话框设置字体
                showDialog();
                break;
            case R.id.ib_share:
                // 分享
                showShare();
                break;
        }
    }

    // 当前设置的字号索引
    private int mCurrentWhich = 2;

    /**
     * 显示对话框
     */
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置字体大小");
        String[] items = {"超大号字体", "大号字体", "标准字体", "小号字体", "超小号字体"};
        builder.setSingleChoiceItems(items, mCurrentWhich, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCurrentWhich = which;
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (mCurrentWhich) {
                    case 0:
                        // 超大号字体
                        mSettings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        // 大号字体
                        mSettings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        // 正常字体
                        mSettings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        // 小号字体
                        mSettings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        // 超小号字体
                        mSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;
                }
            }
        });
        builder.setNegativeButton("取消", null);

        builder.show();
    }

    /**
     * 第三方shareSDK
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("来自zhbj74的分享");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        //oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    // 统计sdk
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
