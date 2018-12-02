package com.cn.zhbj74;

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

public class NewsDetailActivity extends AppCompatActivity {
    private static final String TAG = "NewsDetailActivity";
    private ImageButton ib_back, ib_textsize, ib_share;
    private WebView wv_news;
    private ProgressBar pb_news_load;

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

        ib_back.setVisibility(View.VISIBLE);
        String newsUrl = getIntent().getStringExtra("newsUrl");
        LogUtil.i(TAG, "url:" + newsUrl);

        // 显示网页
        wv_news.loadUrl(newsUrl);

        WebSettings settings = wv_news.getSettings();
        settings.setBuiltInZoomControls(true);// 设置支持缩放
        settings.setUseWideViewPort(true);// 设置支持双击缩放
        settings.setJavaScriptEnabled(true);// 设置支持js
        
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
}
