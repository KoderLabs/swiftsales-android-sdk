package com.swift.chat.library;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SwiftChatWebView {
    private static WebView webView = null;

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public static WebView getWebView(
            Context applicationContext,
            int userId,
            String domain,
            JavaScriptWebInterface javaScriptWebInterface,
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
            webView.addJavascriptInterface(javaScriptWebInterface, "Native");
            webView.loadUrl("https://dev-api.swiftchat.io/widget/script?WebsiteId=" + userId + "&Domain=" + domain + "&Integrate=true");

            loadingPageListener.pageLoading(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    loadingPageListener.pageLoading(false);
                }
            });
        }

        return webView;
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
interface LoadingPageListener {
    void pageLoading(boolean isLoading);
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class JavaScriptWebInterface {
    final Activity activity;

    public JavaScriptWebInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void onBackPressed() {
        activity.finish();
    }
}
