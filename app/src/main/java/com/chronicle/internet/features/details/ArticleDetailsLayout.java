package com.chronicle.internet.features.details;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chronicle.internet.R;

public class ArticleDetailsLayout extends FrameLayout {

    private final ProgressBar mProgressBar;
    private final WebView mWebView;

    public ArticleDetailsLayout(Context context) {
        super(context);
        inflate(context, R.layout.article_details_view, this);
        mWebView = findViewById(R.id.article_details_web_view);
        mProgressBar = findViewById(R.id.article_details_progress_bar);
    }

    void setUrl(String url) {
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mProgressBar.setVisibility(GONE);
            }
        });
    }
}
