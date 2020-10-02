package com.swiftchat.chat

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ProgressBar

class SwiftChatActivity : AppCompatActivity() {

    lateinit var root: FrameLayout
    lateinit var webView: WebView
    lateinit var progressBar: ProgressBar

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AppCompat_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swift_chat)

        val bundle =
            packageManager.getActivityInfo(this.componentName, PackageManager.GET_META_DATA).metaData


        /*val userId = intent.getLongExtra(KEY_LONG_USER_ID, -1)

        if (userId <= 0L) {
            throw IllegalArgumentException("Provide verified userId from swiftChat admin panel")
        }

        val domain = intent.getStringExtra(KEY_STRING_DOMAIN)
            ?: throw IllegalArgumentException("Provide verified domain name from swiftChat admin panel")
*/
        val userId = bundle.getInt("swift.chat.userId")

        if (userId <= 0) {
            throw IllegalArgumentException("Provide verified userId from swiftChat admin panel")
        }

        val domain = bundle.getString("swift.chat.domain")
            ?: throw IllegalArgumentException("Provide verified domain name from swiftChat admin panel")

        root = findViewById(R.id.frameLayout_root)
        progressBar = findViewById(R.id.progressBar)

        webView = SwiftChatWebView.getWebView(
            applicationContext = applicationContext,
            userId = userId.toLong(),
            domain = domain,
            loadingPageListener = {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        )

        root.addView(webView, 0)
    }

    override fun onDestroy() {
        root.removeView(webView)
        super.onDestroy()
    }

    companion object {
        private const val KEY_LONG_USER_ID = "userId"
        private const val KEY_STRING_DOMAIN = "domain"

        fun launch(context: Context, userId: Long, domain: String) {
            Intent(context, SwiftChatActivity::class.java).apply {
                putExtra(KEY_LONG_USER_ID, userId)
                putExtra(KEY_STRING_DOMAIN, domain)
            }.also {
                context.startActivity(it)
            }
        }
    }
}