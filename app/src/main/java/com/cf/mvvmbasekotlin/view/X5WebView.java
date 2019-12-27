package com.cf.mvvmbasekotlin.view;

import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebView extends WebView {

    private WebViewClient client = new WebViewClient() {
        /**
         *@作者：陈飞
         *@说明：防止加载网页时调起系统浏览器
         *@创建日期: 2019/12/3 13:53
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String s) {
            webView.loadUrl(s);
            return true;
        }
    };


    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        initWebViewSettings();

        this.getView().setClickable(true);

    }

    private void initWebViewSettings() {
        WebSettings webSettings = this.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
        webSettings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    public X5WebView(Context context) {
        super(context);
        setBackgroundColor(85621);
    }
}
