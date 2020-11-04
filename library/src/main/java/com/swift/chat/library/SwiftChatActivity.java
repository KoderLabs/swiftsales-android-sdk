package com.swift.chat.library;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;

public class SwiftChatActivity extends AppCompatActivity {

    FrameLayout root = null;
    WebView webView = null;
    ProgressBar progressBar = null;
    public ValueCallback<Uri[]> uploadMessage;
    public static final int REQUEST_SELECT_FILE = 100;

    private static String KEY_MANIFEST_INT_WEBSITE_ID =  "swiftchat.websiteId";
    private static String KEY_MANIFEST_INT_PACKAGE =  "swiftchat.package";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swift_chat);

        try {
            Bundle bundle = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA).metaData;

            int websiteId = bundle.getInt(KEY_MANIFEST_INT_WEBSITE_ID);

            if (websiteId <= 0) {
                throw new IllegalArgumentException("Provide verified websiteId from swiftChat admin panel");
            }

            String packageName = bundle.getString(KEY_MANIFEST_INT_PACKAGE);

            if (packageName == null) {
                throw new IllegalArgumentException("Provide valid application packageName");
            }

            root = findViewById(R.id.frameLayout_root);
            progressBar = findViewById(R.id.progressBar);

            webView = SwiftChatWebView.getWebView(
                    getApplicationContext(),
                    websiteId,
                    packageName,
                    new JavaScriptWebInterface(this),
                    isLoading -> {
                        if (isLoading) {
                            progressBar.setVisibility(View.VISIBLE);
                        } else {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
            );

            webView.setWebChromeClient(new WebChromeClient() {
                // For Lollipop 5.0+ Devices
                public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                    if (uploadMessage != null) {
                        uploadMessage.onReceiveValue(null);
                        uploadMessage = null;
                    }

                    uploadMessage = filePathCallback;

                    Intent intent = fileChooserParams.createIntent();
                    try {
                        startActivityForResult(intent, REQUEST_SELECT_FILE);
                    } catch (ActivityNotFoundException e) {
                        uploadMessage = null;
                        Toast.makeText(SwiftChatActivity.this, "Cannot Open File Chooser", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    return true;
                }

            });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_SELECT_FILE) {
            if (uploadMessage == null)
                return;
            uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
            uploadMessage = null;
        }
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, SwiftChatActivity.class));
    }
}
