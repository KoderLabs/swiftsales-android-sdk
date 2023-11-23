package com.swift.chat.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import androidx.annotation.RestrictTo;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SwiftChatWebView {
    private WebView webView = null;

    private static SwiftChatWebView INSTANCE = null;
    private static Activity activity = null;

    private static final String JAVASCRIPT_INTERFACE_NAME = "Native";

    @SuppressLint("SetJavaScriptEnabled")
    private SwiftChatWebView(
            Context applicationContext,
            int websiteId,
            String packageName,
            LoadingPageListener loadingPageListener
    ) {
        if (webView == null) {
            webView = new WebView(applicationContext);
            ViewGroup.LayoutParams newLayoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            webView.setLayoutParams(newLayoutParams);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.addJavascriptInterface(this, "Native");
            webView.loadUrl("https://api.swiftsales.io/chat/widget/script?WebsiteId=" + websiteId + "&Domain=" + packageName + "&Integrate=true");
            loadingPageListener.pageLoading(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    loadingPageListener.pageLoading(false);
                }
            });
        }
        webView.addJavascriptInterface(this, JAVASCRIPT_INTERFACE_NAME);

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);
    }

    @JavascriptInterface
    public void onBackPressed() {
        activity.finish();
        activity = null;
        webView.removeJavascriptInterface(JAVASCRIPT_INTERFACE_NAME);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static WebView getWebView(
            Context applicationContext,
            int userId,
            String domain,
            Activity activity,
            LoadingPageListener loadingPageListener
    ) {
        SwiftChatWebView.activity = activity;

        if (INSTANCE == null) {
            INSTANCE = new SwiftChatWebView(
                    applicationContext,
                    userId,
                    domain,
                    loadingPageListener
            );
        }

        return INSTANCE.webView;
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
interface LoadingPageListener {
    void pageLoading(boolean isLoading);
}
