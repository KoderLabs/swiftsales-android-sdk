package com.swiftchat.chat

import android.content.Context
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

internal object SwiftChatWebView {
    var webView: WebView? = null

    fun getWebView(
        applicationContext: Context,
        userId: Long,
        domain: String,
        loadingPageListener: (isLoading: Boolean) -> Unit
    ): WebView {
        if (webView == null) {
            webView = WebView(applicationContext)
            webView?.apply {
                val newLayoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                layoutParams = newLayoutParams
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl("https://dev-api.swiftchat.io/widget/script?WebsiteId=$userId&Domain=$domain&Integrate=true")

                loadingPageListener.invoke(true)

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)

                        loadingPageListener.invoke(false)
                    }
                }
            }

            // Enable Cookie for WebView
            CookieManager.getInstance().apply {
                setAcceptCookie(true)
                setAcceptThirdPartyCookies(webView, true)
            }

        }
        return requireNotNull(webView)
    }
}