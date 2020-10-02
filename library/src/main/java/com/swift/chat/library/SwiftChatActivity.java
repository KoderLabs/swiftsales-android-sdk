package com.swift.chat.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class SwiftChatActivity extends AppCompatActivity {

    FrameLayout root = null;
    WebView webView = null;
    ProgressBar progressBar = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swift_chat);

        try {
            Bundle bundle = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA).metaData;

            int userId = bundle.getInt("swiftchat.userId");

            if (userId <= 0) {
                throw new IllegalArgumentException("Provide verified userId from swiftChat admin panel");
            }

            String domain = bundle.getString("swiftchat.domain");

            if (domain == null) {
                throw new IllegalArgumentException("Provide verified domain name from swiftChat admin panel");
            }

            root = findViewById(R.id.frameLayout_root);
            progressBar = findViewById(R.id.progressBar);

            webView = SwiftChatWebView.getWebView(
                    getApplicationContext(),
                    userId,
                    domain,
                    new JavaScriptWebInterface(this),
                    isLoading -> {
                        if (isLoading) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
            );

            root.addView(webView, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        root.removeView(webView);
        super.onDestroy();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SwiftChatActivity.class));
    }
}
